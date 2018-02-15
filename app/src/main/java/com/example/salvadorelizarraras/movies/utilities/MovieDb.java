package com.example.salvadorelizarraras.movies.utilities;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Salvador Elizarraras on 29/01/2018.
 */



public class MovieDb {

    //The authority, which is how your code  knows  which Content Provider to access
    public static final String AUTHORITY = "com.example.salvadorelizarraras.movies";
    //The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    // Define the possible paths for accessing data in this movie
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES_FAV = "movies_fav";

    public static final  class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES_FAV).build();

        // Movie table and column names
        public static final String TABLE_NAME = "movies_fav";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_ID = "column_id";
    }
}
