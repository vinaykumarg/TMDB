package com.example.vinayg.tmdb.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.vinayg.tmdb.models.Movie;

import static com.example.vinayg.tmdb.database.MovieDBSchema.*;
/**
 * Created by manasa.a on 15-03-2017.
 */
public class MoviesDatabase extends SQLiteOpenHelper {
    private static MoviesDatabase instance;
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "moviesdatabase.db";

    private MoviesDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    public static MoviesDatabase getInstance(Context context){
        if(instance == null){
            instance = new MoviesDatabase(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ MoviesTable.NAME +"( "+
                MoviesTable.Cols.ID +" integer primary key autoincrement, "+
                MoviesTable.Cols.MOVIE_NAME+ " varchar(50) , " +
                MoviesTable.Cols.MOVIE_IMAGE_URL + "varchar(500) , " +
                MoviesTable.Cols.IS_FAVORITE + "integer default 0 )"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesTable.NAME);
    }
    public void insertMovie(Movie movie){

    }
    private static ContentValues getContentValues(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(MoviesTable.Cols.ID, movie.getId());
        values.put(MoviesTable.Cols.MOVIE_NAME, movie.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }
}
