package com.example.salvadorelizarraras.movies.utilities;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import com.example.salvadorelizarraras.movies.InterfaceFetshImage;
import com.example.salvadorelizarraras.movies.MainActivity;
import com.example.salvadorelizarraras.movies.Movie;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Objects;

/**
 * Created by Salvador Elizarraras on 20/01/2017.
 */

public class ImageLoaderAsync extends AsyncTask<Object,Void,Movie> {

    InterfaceFetshImage listener;
    int position;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Movie doInBackground(Object... params) {
        Bitmap bitmap = null;
        Movie movie = (Movie)params[0];
        listener = (InterfaceFetshImage) params[1];
        position = (int) params[2];
        ContentValues values = (ContentValues) params[3];
        URL url = NetworkUtils.buildUrl(values);
        bitmap = NetworkUtils.downloadImage(url);
        movie.setImageBitmap(bitmap);
        url = null;
        return movie;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        listener.setData(movie,position);

    }
}
