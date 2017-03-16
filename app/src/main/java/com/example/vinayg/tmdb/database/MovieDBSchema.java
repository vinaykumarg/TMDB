package com.example.vinayg.tmdb.database;

/**
 * Created by manasa.a on 15-03-2017.
 */

public class MovieDBSchema {
    public static final class MoviesTable{
        public static final  String NAME ="movies";

        public static final class Cols{
            public static final String ID = "id";
            public static final String MOVIE_NAME = "movie_name";
            public static final String MOVIE_IMAGE_URL = "movie_image_url";
            public static final String IS_FAVORITE = "is_favorite";
        }
    }

}
