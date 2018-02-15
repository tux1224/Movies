package com.example.salvadorelizarraras.movies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.salvadorelizarraras.movies.Review;
import com.example.salvadorelizarraras.movies.Movie;
import com.example.salvadorelizarraras.movies.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.StringTokenizer;

/**
 * Created by Sal
 * vador Elizarraras on 16/01/2017.
 */

public  class JsonUtils {

    private static final String TAG = "Utils";

    /**
     * Get a Movies array with all data parsing from JSON;
     * @param context
     * @param object
     * @return
     */

    public static Movie[] getMoviesFromJson(Context context, String object){
            Movie movie;

            Movie[] movies = null;
            ArrayList<Movie> arrayListMovies = new ArrayList<>();

        int[] genreIds;
        try {
            JSONObject jsonObject = new JSONObject(object);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            movies = new Movie[jsonArray.length()];


            for(int i = 0; i<jsonArray.length() ; i++)
            {
            jsonObject = (JSONObject) jsonArray.get(i);
                genreIds = new int[jsonObject.getJSONArray("genre_ids").length()];
                for (int j = 0; j <genreIds.length; j++) {
                    genreIds[j] = jsonObject.getJSONArray("genre_ids").getInt(j);
                }
                StringTokenizer posterPath = new StringTokenizer(jsonObject.getString("poster_path"),"/");
                StringTokenizer backdropPath = new StringTokenizer(jsonObject.getString("backdrop_path"),"/");


            movie = new Movie();
            movie.setAdult(jsonObject.getBoolean("adult"));
            movie.setPosterPath(posterPath.nextToken());
            movie.setOverView(jsonObject.getString("overview"));
            movie.setReleaseDate(jsonObject.getString("release_date"));
            movie.setGenre_ids(genreIds);
            movie.setId(jsonObject.getInt("id"));
            movie.setOriginalTitle(jsonObject.getString("original_title"));
            movie.setOriginalLanguge(jsonObject.getString("original_language"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setBakcDropPath(backdropPath.nextToken());
            movie.setPopularity(Float.parseFloat(jsonObject.getDouble("popularity")+""));
            movie.setVoteCount(jsonObject.getInt("vote_count"));
            movie.setVideo(jsonObject.getBoolean("video"));
            movie.setVoteAverage(Float.parseFloat(jsonObject.getDouble("vote_average")+""));

                if(FetchMoviesTask.request.equals(NetworkUtils.STATIC_FAVORITE) && isThisFavorite(String.valueOf(jsonObject.getInt("id")),context)){
                arrayListMovies.add(movie);
                }

            movies[i] = movie;
            }
            if(FetchMoviesTask.request.equals(NetworkUtils.STATIC_FAVORITE)){
            if(arrayListMovies.size() > 0) {
                movies = new Movie[arrayListMovies.size()];
                for (int i = 0; i < movies.length; i++) {
                    movies[i] = arrayListMovies.get(i);
                }
            }else{
                movies = null;
                 }
            }


            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static Movie getFavMoviesFromJson(String object){
        object = "{\"adult\":false,\"backdrop_path\":\"/nDS8rddEK74HfAwCC5CoT6Cwzlt.jpg\",\"belongs_to_collection\":{\"id\":403374,\"name\":\"Jack Reacher Collection\",\"poster_path\":\"/7baSUtFKi8PQ9SLo6ECYBfAW2K8.jpg\",\"backdrop_path\":\"/3FHrAeYMogXd6K1e5tUzQAiS7GE.jpg\"},\"budget\":60000000,\"genres\":[{\"id\":28,\"name\":\"Action\"}],\"homepage\":\"http://www.jackreachermovie.com/\",\"id\":343611,\"imdb_id\":\"tt3393786\",\"original_language\":\"en\",\"original_title\":\"Jack Reacher: Never Go Back\",\"overview\":\"Jack Reacher must uncover the truth behind a major government conspiracy in order to clear his name. On the run as a fugitive from the law, Reacher uncovers a potential secret from his past that could change his life forever.\",\"popularity\":12.588313,\"poster_path\":\"/IfB9hy4JH1eH6HEfIgIGORXi5h.jpg\",\"production_companies\":[{\"name\":\"Paramount Pictures\",\"id\":4},{\"name\":\"Shanghai Film Group\",\"id\":3407},{\"name\":\"TC Productions\",\"id\":21777},{\"name\":\"Skydance Media\",\"id\":82819},{\"name\":\"Huahua Media\",\"id\":83645}],\"production_countries\":[{\"iso_3166_1\":\"CN\",\"name\":\"China\"},{\"iso_3166_1\":\"US\",\"name\":\"United States of America\"}],\"release_date\":\"2016-10-19\",\"revenue\":162146076,\"runtime\":118,\"spoken_languages\":[{\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Released\",\"tagline\":\"Justice is Coming.\",\"title\":\"Jack Reacher: Never Go Back\",\"video\":false,\"vote_average\":5.4,\"vote_count\":2071}";

        Movie movie = null;

        try {
            JSONObject jsonObject = new JSONObject(object);

                StringTokenizer posterPath = new StringTokenizer(jsonObject.getString("poster_path"),"/");
                StringTokenizer backdropPath = new StringTokenizer(jsonObject.getString("backdrop_path"),"/");


                movie = new Movie();
                movie.setAdult(jsonObject.getBoolean("adult"));
                movie.setPosterPath(posterPath.nextToken());
                movie.setOverView(jsonObject.getString("overview"));
                movie.setReleaseDate(jsonObject.getString("release_date"));
                movie.setId(jsonObject.getInt("id"));
                movie.setOriginalTitle(jsonObject.getString("original_title"));
                movie.setOriginalLanguge(jsonObject.getString("original_language"));
                movie.setTitle(jsonObject.getString("title"));
                movie.setBakcDropPath(backdropPath.nextToken());
                movie.setPopularity(Float.parseFloat(jsonObject.getDouble("popularity")+""));
                movie.setVoteCount(jsonObject.getInt("vote_count"));
                movie.setVideo(jsonObject.getBoolean("video"));
                movie.setVoteAverage(Float.parseFloat(jsonObject.getDouble("vote_average")+""));

                return movie;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }
    public static ArrayList<Review> getReviewsFromJson(Context context, JSONObject object){

        Review review;
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            JSONObject jsonObject = object;
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {

                review = new Review();

                jsonObject = jsonArray.getJSONObject(i);
                review.setId(jsonObject.getString("id"));
                review.setAuthor(jsonObject.getString("author"));
                review.setContent(jsonObject.getString("content"));
                review.setUrl(jsonObject.getString("url"));

                reviews.add(review);
            }



        } catch (JSONException e) {
            e.printStackTrace();

        }
        return reviews;


    }
    public static Trailer[] getTrailerFromJson(Context context, JSONObject jsonObject){
        Trailer[] trailers = null ;
        Trailer trailer = null;

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            trailers = new Trailer[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonOb = (JSONObject) jsonArray.get(i);
                trailer = new Trailer();
                trailer.setId(jsonOb.getString("id"));
                trailer.setIso_639_1(jsonOb.getString("iso_639_1"));
                trailer.setIso_3166_1(jsonOb.getString("iso_3166_1"));
                trailer.setKey(jsonOb.getString("key"));
                trailer.setName(jsonOb.getString("name"));
                trailer.setSite(jsonOb.getString("site"));
                trailer.setSite(jsonOb.getString("size"));
                trailer.setType(jsonOb.getString("type"));
                trailers[i]= trailer;

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailers;
    }

    public static boolean isThisFavorite(String id, Context context){
        boolean isFavorite = false;
        ContentValues values = new ContentValues();
        values.put(NetworkUtils.REQUEST,NetworkUtils.SELECT_ONE_DB);
        values.put(NetworkUtils.SELECT_ONE_DB,id);
        Uri uri = NetworkUtils.buildUri(values);
        Cursor cursor = context.getContentResolver().query(uri,null,
                MovieDb.MovieEntry.COLUMN_ID,
                new String[]{id},
                MovieDb.MovieEntry.COLUMN_ID);
        Log.d(TAG, "isThisFavorite() returned: " + cursor.getCount());
        isFavorite = (cursor.getCount() > 0) ? true : false;


        return isFavorite;

    }
}
