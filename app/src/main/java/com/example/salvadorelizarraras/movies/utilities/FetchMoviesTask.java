package com.example.salvadorelizarraras.movies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.salvadorelizarraras.movies.InterfaceFetshImage;
import com.example.salvadorelizarraras.movies.MainActivity;
import com.example.salvadorelizarraras.movies.Movie;
import com.example.salvadorelizarraras.movies.MoviesRecyclerViewAdapter;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Salvador Elizarraras on 12/01/2017.
 */

public class FetchMoviesTask extends AsyncTask<Object, Void, Movie[]> implements InterfaceFetshImage {

    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    private Movie[] movies;
    private ContentValues values;
    public static String request;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Movie[] doInBackground(Object... params) {
        if (params.length == 0) {
            return null;
        }
        request = (String) params[0];
        String imageRequest = (String) params[1];
        Context context = (Context) params[2];
        values = new ContentValues();
        values.put("image",imageRequest);
        values.put("request",request);
        moviesRecyclerViewAdapter = (MoviesRecyclerViewAdapter) params[3];
        Movie[] movies = null;
        URL moviesRequestUrl = NetworkUtils.buildUrl(values);
        try {
            if(request.equals(NetworkUtils.STATIC_FAVORITE)){
                values = new ContentValues();
                values.put("image",imageRequest);
                values.put("request",NetworkUtils.STATIC_TOP_RATE);
                 moviesRequestUrl = NetworkUtils.buildUrl(values);
                String jsonResponseMovies = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                movies = JsonUtils.getMoviesFromJson(context, jsonResponseMovies);

                values = new ContentValues();
                values.put("image",imageRequest);
                values.put("request",NetworkUtils.STATIC_POPULAR);
                moviesRequestUrl = NetworkUtils.buildUrl(values);
                jsonResponseMovies = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                Movie[] movies2 = JsonUtils.getMoviesFromJson(context, jsonResponseMovies);
                int moviesSize = movies != null ? movies.length : 0;
                int moviesSize2 = movies2 != null ? movies2.length : 0;


                    Movie[] movies3 = (moviesSize+moviesSize2) > 0 ? new Movie[moviesSize + moviesSize2] : null;

                for (int i = 0; i < moviesSize; i++) {
                    movies3[i] = movies[i];
                }

                for (int i =0; i < moviesSize2; i++) {
                    movies3[i+moviesSize] = movies2[i];
                }

                movies = movies3;
            }else {
                String jsonResponseMovies = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                movies = JsonUtils.getMoviesFromJson(context, jsonResponseMovies);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }


    @Override
    protected void onPostExecute(Movie[] movies) {
        super.onPostExecute(movies);
        if (movies == null) {
            MainActivity.showMessageError();
        } else {
            this.movies = movies;

            for (int i = 0; i < movies.length; i++) {

                Movie movie = movies[i];
                values = new ContentValues();
                values.put("request",NetworkUtils.IMAGE_SIZE_W342);
                values.put("image",movie.getPosterPath());
                Log.d("FetchImages Values--->", values.toString());
                new ImageLoaderAsync().execute(movie, this, i, values);
            }
        }
    }

    @Override
    public void setData(Movie movie, int position) {
        movies[position] = movie;
        if (position == movies.length - 1) {

            moviesRecyclerViewAdapter.setMovieData(this.movies);
            MainActivity.hideLoading();
        }
    }
}
