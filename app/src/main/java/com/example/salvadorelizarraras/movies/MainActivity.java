package com.example.salvadorelizarraras.movies;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.salvadorelizarraras.movies.utilities.FetchMoviesTask;
import com.example.salvadorelizarraras.movies.utilities.NetworkUtils;
import java.io.Serializable;



public class MainActivity extends AppCompatActivity implements MoviesOnClickHandler, Serializable {

    private static RecyclerView recyclerView;
    private static TextView mTextViewMessageError;
    public  static FrameLayout frameLayout;
    private static MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    public  static ProgressBar mLoadingIndicator;
    public  static String request = NetworkUtils.STATIC_POPULAR;
    private static final int TASK_LOADER_ID = 0;
    private int CODE_RESULT = 101;
    private static int mScrollPosition = 0;
    private String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() returned: " +savedInstanceState);


            setContentView(R.layout.activity_main);
            mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
            mTextViewMessageError = (TextView) findViewById(R.id.show_message_error);

            frameLayout = (FrameLayout) findViewById(R.id.opacity);
            recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
            GridLayoutManager layoutManager;

            layoutManager = (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ?
                    new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false) :
                    new GridLayoutManager(this, 5, GridLayoutManager.VERTICAL, false);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
            moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(this);
            recyclerView.setAdapter(moviesRecyclerViewAdapter);
            loadData();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() returned: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();


        Log.d(TAG, "onRestart() returned: ");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "onStart() returned: ");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume() returned: " );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.popular:
                request = NetworkUtils.STATIC_POPULAR;
                loadData();
                break;
            case R.id.rate:
                request = NetworkUtils.STATIC_TOP_RATE;
                loadData();
                break;
            case R.id.fav:
                request = NetworkUtils.STATIC_FAVORITE;
                loadData();
                break;
        }
        return true;
    }

    private void loadData() {
        if (NetworkUtils.isNetworkAvailable(this)) {
            onLoading();
            new FetchMoviesTask().execute(request, null, getApplicationContext(), moviesRecyclerViewAdapter);
        } else {
            Toast.makeText(this, "Internet Connection unavailable", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(int position) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            Context context = this;
            Class destinationClass = DetailActivity.class;
            Intent intentToStartDetailActivity = new Intent(context, destinationClass);
            Bundle bundle = new Bundle();
            Movie requestMovie = moviesRecyclerViewAdapter.getMovie(position);
            Movie toSendMovie = new Movie();

            toSendMovie.setId(requestMovie.getId());
            toSendMovie.setTitle(requestMovie.getTitle());
            toSendMovie.setReleaseDate(requestMovie.getReleaseDate());
            toSendMovie.setOverView(requestMovie.getOverView());
            toSendMovie.setVoteAverage(requestMovie.getVoteAverage());
            toSendMovie.setPosterPath(requestMovie.getPosterPath());
            bundle.putSerializable("movie", toSendMovie);

            Log.d("MainActivyt:onClick()", toSendMovie.toString());
            intentToStartDetailActivity.putExtras(bundle);

            startActivityForResult(intentToStartDetailActivity,CODE_RESULT,null);
        } else {
            Toast.makeText(this, "Internet Connection unavailable", Toast.LENGTH_LONG).show();
        }

    }

    public static void onLoading() {
        frameLayout.setVisibility(View.VISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setClickable(false);
    }

    public static void showMessageError() {
        mTextViewMessageError.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);

    }

    public static void hideLoading() {
        mTextViewMessageError.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        recyclerView.setClickable(true);
        recyclerView.getLayoutManager().scrollToPosition(mScrollPosition);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (data!=null ) {
                Log.d(TAG, data.getExtras().getInt("update", 0) + " onActivityResult() returned: " + requestCode);
                if (data.getExtras().getInt("update", 0) > 0) {
                    loadData();
                }
            }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        GridLayoutManager layoutManager = null;
        layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        layoutManager.setSpanCount((newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? 5 : 3);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
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
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if(layoutManager != null){
                int count = layoutManager.getChildCount();
                if(mScrollPosition != RecyclerView.NO_POSITION && mScrollPosition < count){
                    layoutManager.scrollToPosition(mScrollPosition);
                    Log.d(TAG, "onRestoreInstanceState() returned: " + mScrollPosition);

                }
            }

        }

    }

}

