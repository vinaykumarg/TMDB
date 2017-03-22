package com.example.vinayg.tmdb.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vinayg.tmdb.activities.MovieDetailsActivity;
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.adapters.TopRatedAdapter;
import com.example.vinayg.tmdb.handler.HttpHandler;
import com.example.vinayg.tmdb.listeners.ClickListener;
import com.example.vinayg.tmdb.listeners.RecyclerTouchListener;
import com.example.vinayg.tmdb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class TopRatedFragment extends Fragment{
    private  static String TAG = PopularScreenFragment.class.getSimpleName();
    private String[] movie_filter;
    ArrayList<Movie> data;
    private TopRatedAdapter gridAdapter;
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.myList);
        movie_filter = new String[]{"popular","top_rated","favourite"};
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        new FetchMovieData().execute();


        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                Intent intent =  new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("movie",data.get(position));
                startActivity(intent);
            }

            @Override
            public void onDoubleClick(View view, int position) {

            }

        }));
        return view;
    }



    private class FetchMovieData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
//            URL url = buildurl();
//            HttpHandler sh = new HttpHandler();
//            data = new ArrayList<>();
//            // Making a request to url and getting response
//            String jsonStr = sh.makeServiceCall(url.toString());
//            Log.d(TAG,jsonStr + " called ");
//            if (jsonStr != null) {
//
//
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//                    JSONArray movies = jsonObj.getJSONArray("results");
//                    for (int i=0;i<movies.length();i++){
//                        JSONObject movieDetails = movies.getJSONObject(i);
//                        Movie movie = new Movie();
////                        Log.d("movie",movieDetails.toString());
//                        movie.setMovieId(movieDetails.getLong("id"));
//                        movie.setTitle(movieDetails.getString("original_title"));
//                        movie.setImageUrl("https://image.tmdb.org/t/p/w500"+movieDetails.getString("poster_path"));
//                        movie.setOverview(movieDetails.getString("overview"));
//                        movie.setAverageRating(movieDetails.getString("vote_average"));
//                        movie.setBackgroundImage("https://image.tmdb.org/t/p/w500"+movieDetails.getString("backdrop_path"));
//                        movie.setRelease_date(movieDetails.getString("release_date"));
//                        data.add(movie);
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//
//            return "";
            URL url = buildurl();
            HttpHandler sh = new HttpHandler();
            data = new ArrayList<>();
            HashMap hm = new HashMap();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url != null ? url.toString() : null);

            String genreJson = sh.makeServiceCall(buildGenresUrl().toString());
            if (jsonStr != null && genreJson != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject genreobject = new JSONObject(genreJson);
                    JSONArray genres = genreobject.getJSONArray("genres");
                    JSONArray movies = jsonObj.getJSONArray("results");
                    for (int j=0; j<genres.length();j++) {
                        JSONObject object = genres.getJSONObject(j);
                        hm.put(object.getInt("id"),object.get("name"));
                    }
                    for (int i=0;i<movies.length();i++){
                        JSONObject movieDetails = movies.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setMovieId(movieDetails.getLong("id"));
                        movie.setTitle(movieDetails.getString("title"));
                        movie.setImageUrl("https://image.tmdb.org/t/p/w500"+movieDetails.getString("poster_path"));
                        movie.setOverview(movieDetails.getString("overview"));
                        movie.setAverageRating("Average rating : "+movieDetails.getString("vote_average"));
                        movie.setBackgroundImage("https://image.tmdb.org/t/p/w500"+movieDetails.getString("backdrop_path"));
                        movie.setRelease_date(movieDetails.getString("release_date"));
                        JSONArray genre_ids = movieDetails.getJSONArray("genre_ids");
                        String genre = "";
                        for (int j=0;j<genre_ids.length();j++) {
                            genre = genre+hm.get(genre_ids.get(j))+",";
                        }
                        movie.setGenre(genre);
                        data.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return "";
        }
        private URL buildGenresUrl() {
            try {
                final String Movie_Base_URL = "https://api.themoviedb.org/3/genre/movie/list?";
                final String APPID_PARAM = "api_key";
                String query = "language";
                String language = "en-US";
                String page = "page";
                Uri builtUri = Uri.parse(Movie_Base_URL).buildUpon()
                        .appendQueryParameter(APPID_PARAM, getString(R.string.apikey))
                        .appendQueryParameter(query,language)
                        .build();
                return new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        private URL buildurl() {
            try {
                final String Movie_Base_URL = "https://api.themoviedb.org/3/movie/";
                final String APPID_PARAM = "api_key";
                String query = "language";
                String language = "en-US";
                String page = "page";
                Uri builtUri = Uri.parse(Movie_Base_URL).buildUpon()
//                        .appendPath(movie_filter[0])
                        .appendPath(movie_filter[1])
                        .appendQueryParameter(APPID_PARAM, getString(R.string.apikey))
                        .appendQueryParameter(query,language)
                        .appendQueryParameter(page,"1")
                        .build();
                URL url = new URL(builtUri.toString());
//                Log.e("url", builtUri.toString());
                return url;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            gridAdapter = new TopRatedAdapter(getContext(), R.layout.item_demo_01, data);
            mRecyclerView.setAdapter(gridAdapter);
        }
    }
}

