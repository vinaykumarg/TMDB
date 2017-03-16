package com.example.vinayg.tmdb.database;
/**
 * Created by manasa.a on 15-03-2017.
 */
public class MovieDBSchema {
    public static final class MoviesTable{
        public static final  String NAME ="movies";

        public static final class Cols{
            public static final String ID = "id";
            public static final String MOVIE_ID="movieid";
            public static final String MOVIE_NAME = "movie_name";
            public static final String MOVIE_IMAGE_URL = "movie_image_url";
            public static final String IS_FAVORITE = "is_favorite";
            public static final String RELEASE_DATE="releasedate";
            public static final String BACKGROUND_IMG="bckgrndimg";
            public static final String AVG_RATING = "avgrating";
            public static final String OVERVIEW = "overview";
            public static final String CATEGORY ="category";
        }
    }
}
