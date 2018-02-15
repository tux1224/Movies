package com.example.salvadorelizarraras.movies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.salvadorelizarraras.movies.Movie;
import com.example.salvadorelizarraras.movies.MoviesRecyclerViewAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Salvador Elizarraras on 07/02/2018.
 */

public class FetchTrailersTask extends AsyncTask<Object,Void,JSONObject> {
    private OnResponseJson listener;
    private String request;
    @Override
    protected JSONObject doInBackground(Object... params) {
        JSONObject jsonObject = null;
        if (params.length == 0) {
            return null;
        }

        ContentValues values = (ContentValues) params[0];
        request = values.getAsString(NetworkUtils.REQUEST);
        URL url = NetworkUtils.buildUrl((ContentValues) params[0]);
        listener = (OnResponseJson) params[1];

        try {

            String jsonResponseTrailer = NetworkUtils.getResponseFromHttpUrl(url);
            jsonObject = new JSONObject(jsonResponseTrailer);
            Log.v("doInBackground   ", jsonResponseTrailer);
            //jsonObject.put(NetworkUtils.REQUEST, values.getAsString(NetworkUtils.REQUEST));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        listener.onResponse(jsonObject, request);

    }
    public interface OnResponseJson{
       void onResponse(JSONObject jsonObject, String request);
    }
}
