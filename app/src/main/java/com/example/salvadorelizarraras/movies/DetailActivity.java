package com.example.salvadorelizarraras.movies;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salvadorelizarraras.movies.databinding.ActivityDetailBinding;
import com.example.salvadorelizarraras.movies.utilities.FetchTrailersTask;
import com.example.salvadorelizarraras.movies.utilities.ImageLoaderAsync;
import com.example.salvadorelizarraras.movies.utilities.JsonUtils;
import com.example.salvadorelizarraras.movies.utilities.MovieDb;
import com.example.salvadorelizarraras.movies.utilities.NetworkUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import static com.example.salvadorelizarraras.movies.utilities.JsonUtils.isThisFavorite;

/**
 * Created by Salvador Elizarraras on 18/01/2017.
 */

public class DetailActivity extends AppCompatActivity implements Serializable,
                                    InterfaceFetshImage, MoviesOnClickHandler,
                                    FetchTrailersTask.OnResponseJson, View.OnClickListener{



    //region fields

    private static final String TAG = "DetailActivity" ;


    private Movie movie;
    private TrailerAdapter trailerAdapter;
    private ContentValues values;
    private static ArrayList<Review> reviews;
    private Menu menu;
    private int returned = 0;
    private static int mScrollPosition = 0;

    //endregion

    private static ActivityDetailBinding mBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //toolbar setup
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.movie = (Movie) getIntent().getSerializableExtra("movie");

        Log.d("Movie in DetailActivity", movie.getPosterPath());
        //marksFavorite = (TextView) findViewById(R.id.marks_fav);
        //floatingActionButton = (FloatingActionButton) findViewById(R.id.f_button);
        mBinding.fButton.setOnClickListener(this);
        //mDetailContent = (ScrollView) findViewById(R.id.m_detail_container);
        //mTextViewMessageError = (TextView) findViewById(R.id.show_message_error);
        //frameLayout = (FrameLayout) findViewById(R.id.opacity);
        //mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        //imageView = (ImageView) findViewById(R.id.img_poster);
        //tv_title = (TextView) findViewById(R.id.tv_title);
        //tv_release_date = (TextView) findViewById(R.id.tv_release_date);
        //tv_synopsis = (TextView) findViewById(R.id.tv_synopsis);
        //ratingBar = (RatingBar) findViewById(R.id.rate);

        mBinding.toolbar.setTitle(movie.getTitle());
        mBinding.toolbar.setSubtitle(movie.getTitle());



        mBinding.posterLayout2.tvReleaseDate.setText(movie.getReleaseDate());
        mBinding.posterLayout.tvSynopsis.setText(movie.getOverView());
        mBinding.posterLayout2.rate.setNumStars(5);

        mBinding.posterLayout2.rate.setRating((float) (((double) 5 / (double) 10)) * (movie.getVoteAverage()));

        //RecyclerView and adapter setup
        //recyclerView = (RecyclerView) findViewById(R.id.m_recycler_trailers);
        RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(this);
        mBinding.posterLayout.mRecyclerTrailers.setLayoutManager(linearLayout);
        trailerAdapter = new TrailerAdapter();
        trailerAdapter.setListener(this);
        mBinding.posterLayout.mRecyclerTrailers.setAdapter(trailerAdapter);

        loadData();
        mBinding.posterLayout2.marksFav.setVisibility(isThisFavorite(String.valueOf(movie.getId()),this) ? View.VISIBLE : View.INVISIBLE);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        RecyclerView.LayoutManager layoutManager = mBinding.posterLayout.mRecyclerTrailers.getLayoutManager();
        if(layoutManager != null && layoutManager instanceof LinearLayoutManager){
            mScrollPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }
        outState.putInt("position",mScrollPosition);
        Log.d(TAG, "onSaveInstanceState() returned: "+mScrollPosition);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState() returned: " + savedInstanceState);
        if(savedInstanceState != null ){
            mScrollPosition = savedInstanceState.getInt("position");
            RecyclerView.LayoutManager layoutManager = mBinding.posterLayout.mRecyclerTrailers.getLayoutManager();
            if(layoutManager != null){
                int count = layoutManager.getChildCount();
                if(mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count){
                    layoutManager.scrollToPosition(mScrollPosition);
                    Log.d(TAG, "onRestoreInstanceState() returned: " + mScrollPosition);

                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.review:
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("reviews", reviews);
                DialogFragment dialog = new DialogReview();
                dialog.setArguments(bundle);
                dialog.show(getSupportFragmentManager(), "DialogReview");
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadData() {

        onLoading();
        if (NetworkUtils.isNetworkAvailable(this)) {

            loadPoster();
            loadTrailesrs();
            loadReviews();

        } else {
            Toast.makeText(this, "Internet Connection unavailable", Toast.LENGTH_LONG).show();
        }


    }

    private void loadPoster(){

        values = new ContentValues();
        values.put(NetworkUtils.REQUEST, NetworkUtils.IMAGE_SIZE_W342);
        values.put("image", movie.getPosterPath());
        new ImageLoaderAsync().execute(movie, this, 0, values);
    }

    private void loadReviews(){

        values = new ContentValues();
        values.put(NetworkUtils.REQUEST, NetworkUtils.STATIC_REVIEWS);
        values.put(NetworkUtils.STATIC_ID, movie.getId());
        new FetchTrailersTask().execute(values, this);

    }

    public void loadTrailesrs(){

        values = new ContentValues();
        values.put(NetworkUtils.REQUEST, NetworkUtils.STATIC_VIDEOS);
        values.put(NetworkUtils.STATIC_ID, movie.getId());
        new FetchTrailersTask().execute(values, this);

    }

    public static void onLoading() {
        mBinding.pbLoadingIndicator.setVisibility(View.VISIBLE);
        mBinding.opacity.setVisibility(View.VISIBLE);
    }

    public static void showMessageError() {
        mBinding.showMessageError.setVisibility(View.VISIBLE);
        mBinding.posterLayout.mRecyclerTrailers.setVisibility(View.INVISIBLE);
    }

    public static void hideLoading() {
        mBinding.showMessageError.setVisibility(View.INVISIBLE);
        mBinding.posterLayout.mRecyclerTrailers.setVisibility(View.VISIBLE);
        mBinding.opacity.setVisibility(View.INVISIBLE);
        mBinding.pbLoadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.posterLayout.mRecyclerTrailers.getLayoutManager().scrollToPosition(mScrollPosition);

    }

    @Override
    public void setData(Movie movie, int position) {

        mBinding.posterLayout2.imgPoster.setImageBitmap(movie.getImageBitmap());
        hideLoading();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(int position) {
        Log.d("onClick",trailerAdapter.getTrailer(position).getId().toString());
        String key = trailerAdapter.getTrailer(position).getKey();


        watchYoutubeVideo(this,key);
    }

    public static void watchYoutubeVideo(Context context, String id){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NetworkUtils.REQUEST,NetworkUtils.YOUTUBE_VIDEOS);
        contentValues.put(NetworkUtils.STATIC_VIDEO_KEY, id);
        String url = NetworkUtils.buildUrl(contentValues).toString();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject, String request) {
        hideLoading();
        if(jsonObject == null){
            showMessageError();
        }else {
            Log.v("onResponse ", jsonObject.toString());
            Log.d(TAG, "onResponse() returned: " + request);
            Log.i("Trailer", jsonObject.toString());
            switch (request) {
                case NetworkUtils.STATIC_VIDEOS:
                Trailer[] trailes = JsonUtils.getTrailerFromJson(this, jsonObject);
                trailerAdapter.setData(trailes);
                break;
                case NetworkUtils.STATIC_REVIEWS:
                reviews = JsonUtils.getReviewsFromJson(this, jsonObject);

                MenuItem menuItem = menu.findItem(R.id.review);
                    menuItem.setVisible( (reviews.size() > 0)? true : false);
                break;

                    default:
                     throw new UnsupportedOperationException("Unsupported operation "+ request);

            }
        }
    }

    private void saveFavorite(){

        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieDb.MovieEntry.COLUMN_ID, movie.getId());
        contentValues.put(MovieDb.MovieEntry.COLUMN_TITLE, movie.getTitle());
        contentValues.put(NetworkUtils.REQUEST,NetworkUtils.INSERT_DB);

        try {

        Uri uri= NetworkUtils.buildUri(contentValues);

        Log.d(TAG, "saveFavorite() returned: " + uri);

            // removing unices value
            contentValues.remove(NetworkUtils.REQUEST);

            // Insert into database
            uri = getContentResolver().insert(uri,contentValues);
            Log.d(TAG, "Insert provider returned: " + uri);


            contentValues.put(NetworkUtils.REQUEST,NetworkUtils.SELECT_ALL_DB);
            uri= NetworkUtils.buildUri(contentValues);
            Cursor cursor = getContentResolver().query(uri,null,null, null, BaseColumns._ID);

            while(cursor.moveToNext()) {
                Log.d(TAG, "Cursor DB returned: " + cursor.getString(cursor.getColumnIndex(MovieDb.MovieEntry.COLUMN_ID)));
            }


            if(uri != null) {
                Log.d(TAG, "Uri ---> "+uri.toString());
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toggleFavorite(){

        if(isThisFavorite(String.valueOf(movie.getId()),this))
            deleteFavorite();
        else
            saveFavorite();

        mBinding.posterLayout2.marksFav.setVisibility(isThisFavorite(String.valueOf(movie.getId()),this) ? View.VISIBLE : View.INVISIBLE);


    }

    private void deleteFavorite(){
        ContentValues values = new ContentValues();
        values.put(NetworkUtils.REQUEST,NetworkUtils.DELTE_DB);
        values.put(NetworkUtils.DELTE_DB, movie.getId());

        Uri uri = NetworkUtils.buildUri(values);

        Log.d(TAG, "Uri to delete: "+ uri);
        returned = getContentResolver().delete(uri,null,null);
        Log.d(TAG, "deleted  returned: " + returned );
        setResult(Activity.RESULT_OK, new Intent().putExtra("update",returned));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.f_button:
                toggleFavorite();

                break;
        }
    }


}
