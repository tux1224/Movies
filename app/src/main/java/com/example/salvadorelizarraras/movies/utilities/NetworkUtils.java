package com.example.salvadorelizarraras.movies.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;

import com.example.salvadorelizarraras.movies.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Salvador Elizarraras on 11/01/2017.
 */

public final class NetworkUtils  {

    public static final String TAG = NetworkUtils.class.getSimpleName();

    //region static values

    public static final String STATIC_API_KEY  = "f91acd1c7d6f95c995f9ce29dbef1304";
    public static final String STATIC_SCHEME ="http";
    public static final String STATIC_SECURE_SCHEME ="https";
    public static final String STATIC_MOVIES_P_R = "api.themoviedb.org";
    public static final String STATIC_MOVIES_EPPEND = "movie";
    public static final String STATIC_MOVIES_3 = "3";
    public static final String STATIC_API_KEY_PARAM = "api_key";
    public static final String STATIC_AUTHORITY= "image.tmdb.org";
    public static final String STATIC_APPEND_T = "t";
    public static final String STATIC_APPEND_P = "p";
    public static final String STATIC_POPULAR  = "popular";
    public static final String STATIC_FAVORITE = "favorite";
    public static final String STATIC_TOP_RATE = "top_rated";
    public static final String IMAGE_SIZE_W92  = "w92";
    public static final String IMAGE_SIZE_W154 = "w154";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W342 = "w342";
    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_SIZE_W780 = "w780";
    public static final String STATIC_VIDEOS   = "videos";
    public static final String STATIC_VIDEO_KEY= "video_key";
    public static final String YOUTUBE_VIDEOS  = "youtube";
    public static final String STATIC_WATCH    = "watch";
    public static final String STATIC_VIDEO    = "v";
    public static final String STATIC_AUTORITY_YOUTUBE = "www.youtube.com";
    public static final String STATIC_REVIEWS  = "reviews";
    public static final String STATIC_ID       = "id";
    public static final String REQUEST       = "request";
    public static final String INSERT_DB    = "insert_db";
    public static final String DELTE_DB    = "delte_db";
    public static final String SELECT_ALL_DB    = "select_all";
    public static final String SELECT_ONE_DB    = "select_one_db";

    //endregion

    public static  Uri buildUri(@NonNull ContentValues values){
        Uri uri= null;
        String uriString = "";
                String request = values.getAsString(REQUEST);

            switch (request) {

                case INSERT_DB:
                    uriString = MovieDb.MovieEntry.CONTENT_URI.toString();
                    break;
                case DELTE_DB:
                    uriString = MovieDb.MovieEntry.CONTENT_URI.buildUpon().appendPath(values.getAsString(DELTE_DB)).toString();
                    break;

                case SELECT_ONE_DB:
                    uriString = MovieDb.MovieEntry.CONTENT_URI.buildUpon().appendPath(values.getAsString(SELECT_ONE_DB)).toString();
                    break;
                    case SELECT_ALL_DB:
                     uriString = MovieDb.MovieEntry.CONTENT_URI.toString();
                    break;
            }
            try {
                uri = Uri.parse(uriString);
            }catch (Exception e){
                e.printStackTrace();
            }
        Log.v(TAG, "Uri Builded ---> " +uri.toString());

        return uri;
    }

    public static URL buildUrl(@NonNull ContentValues values){
        URL url = null;
        String request = values.getAsString(REQUEST);

        Uri.Builder uriBuilder = new Uri.Builder();


        switch (request){

                case STATIC_POPULAR:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_MOVIES_P_R);
                uriBuilder.appendPath(STATIC_MOVIES_3);
                uriBuilder.appendPath(STATIC_MOVIES_EPPEND);
                uriBuilder.appendPath(STATIC_POPULAR);
                uriBuilder.appendQueryParameter(STATIC_API_KEY_PARAM,STATIC_API_KEY);
                break;

            case STATIC_TOP_RATE:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_MOVIES_P_R);
                uriBuilder.appendPath(STATIC_MOVIES_3);
                uriBuilder.appendPath(STATIC_MOVIES_EPPEND);
                uriBuilder.appendPath(STATIC_TOP_RATE);
                uriBuilder.appendQueryParameter(STATIC_API_KEY_PARAM,STATIC_API_KEY);
                break;

            case STATIC_FAVORITE:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_MOVIES_P_R);
                uriBuilder.appendPath(STATIC_MOVIES_3);
                uriBuilder.appendPath(STATIC_MOVIES_EPPEND);
                uriBuilder.appendPath(STATIC_TOP_RATE);
                uriBuilder.appendQueryParameter(STATIC_API_KEY_PARAM,STATIC_API_KEY);
                break;

            case YOUTUBE_VIDEOS:
                uriBuilder.scheme(STATIC_SECURE_SCHEME);
                uriBuilder.authority(STATIC_AUTORITY_YOUTUBE);
                uriBuilder.appendEncodedPath(STATIC_WATCH);
                uriBuilder.appendQueryParameter(STATIC_VIDEO,values.getAsString(STATIC_VIDEO_KEY));
                break;

                case STATIC_VIDEOS:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_MOVIES_P_R);
                uriBuilder.appendPath(STATIC_MOVIES_3);
                uriBuilder.appendPath(STATIC_MOVIES_EPPEND);
                uriBuilder.appendPath(values.getAsString(STATIC_ID));
                uriBuilder.appendPath(STATIC_VIDEOS);
                uriBuilder.appendQueryParameter(STATIC_API_KEY_PARAM,STATIC_API_KEY);
                break;
                

                case STATIC_REVIEWS:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_MOVIES_P_R);
                uriBuilder.appendPath(STATIC_MOVIES_3);
                uriBuilder.appendPath(STATIC_MOVIES_EPPEND);
                uriBuilder.appendPath(values.getAsString(STATIC_ID));
                uriBuilder.appendPath(STATIC_REVIEWS);
                uriBuilder.appendQueryParameter(STATIC_API_KEY_PARAM,STATIC_API_KEY);
                break;


            case IMAGE_SIZE_W92:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_AUTHORITY);
                uriBuilder.appendPath(STATIC_APPEND_T);
                uriBuilder.appendPath(STATIC_APPEND_P);
                uriBuilder.appendPath(IMAGE_SIZE_W92);
                uriBuilder.appendPath(values.getAsString("image"));
                break;
            case IMAGE_SIZE_W154:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_AUTHORITY);
                uriBuilder.appendPath(STATIC_APPEND_T);
                uriBuilder.appendPath(STATIC_APPEND_P);
                uriBuilder.appendPath(IMAGE_SIZE_W154);
                uriBuilder.appendPath(values.getAsString("image"));
                break;
            case IMAGE_SIZE_W185:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_AUTHORITY);
                uriBuilder.appendPath(STATIC_APPEND_T);
                uriBuilder.appendPath(STATIC_APPEND_P);
                uriBuilder.appendPath(IMAGE_SIZE_W185);
                uriBuilder.appendPath(values.getAsString("image"));

                break;
            case IMAGE_SIZE_W342:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_AUTHORITY);
                uriBuilder.appendPath(STATIC_APPEND_T);
                uriBuilder.appendPath(STATIC_APPEND_P);
                uriBuilder.appendPath(IMAGE_SIZE_W342);
                uriBuilder.appendPath(values.getAsString("image"));

                break;
            case IMAGE_SIZE_W500:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_AUTHORITY);
                uriBuilder.appendPath(STATIC_APPEND_T);
                uriBuilder.appendPath(STATIC_APPEND_P);
                uriBuilder.appendPath(IMAGE_SIZE_W500);
                uriBuilder.appendPath(values.getAsString("image"));

                break;
            case IMAGE_SIZE_W780:
                uriBuilder.scheme(STATIC_SCHEME);
                uriBuilder.authority(STATIC_AUTHORITY);
                uriBuilder.appendPath(STATIC_APPEND_T);
                uriBuilder.appendPath(STATIC_APPEND_P);
                uriBuilder.appendPath(IMAGE_SIZE_W780);
                uriBuilder.appendPath(values.getAsString("image"));
                break;




        }

        try {
            url = new URL(uriBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();


        }
        Log.v(TAG, "URI Builded ---> " +url.toString());

        return url;
    }

    public static Bitmap downloadImage(URL url) {

        try {

            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

            InputStream is = httpCon.getInputStream();
            int fileLength = httpCon.getContentLength();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead, totalBytesRead = 0;
            byte[] data = new byte[2048];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
                totalBytesRead += nRead;

            }

            byte[] image = buffer.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }finally{

            connection.disconnect();
        }

     }
}
