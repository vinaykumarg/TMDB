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
import android.widget.Button;
import android.widget.Toast;

import com.example.vinayg.tmdb.MovieDetailsActivity;
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.adapters.PopularVIewAdapter;
import com.example.vinayg.tmdb.database.MoviesDatabase;
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

/**
 * Created by vinay.g.
 */
public class PopularScreenFragment extends Fragment {
    private  static String TAG = PopularScreenFragment.class.getSimpleName();
    private String[] movie_filter;
    ArrayList<Movie> data;
    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewPopular);
        movie_filter = new String[]{"popular","top_rated","favourite"};
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        new FetchMovieData().execute();
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Button likeBtn = (Button) view.findViewById(R.id.btnLike) ;
                Movie movie = data.get(position);
                MoviesDatabase database = MoviesDatabase.getInstance(getContext());
                if(!database.checkIfsaved(movie)) {
                    likeBtn.setBackgroundResource(R.drawable.like);
                    movie.setIsFavorite(1);
                    database.insertMovie(movie);

                } else {
                    likeBtn.setBackgroundResource(R.drawable.likegrey);
                    movie.setIsFavorite(0);
                    database.deleteMovie(movie);
                }
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
                Toast.makeText(getContext(),"DOBLE TAP",Toast.LENGTH_SHORT).show();
            }

        }));
        return view;
    }
    private class FetchMovieData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            URL url = buildurl();
            HttpHandler sh = new HttpHandler();
            data = new ArrayList<>();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url != null ? url.toString() : null);
            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray movies = jsonObj.getJSONArray("results");
                    for (int i=0;i<movies.length();i++){
                        JSONObject movieDetails = movies.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setMovieId(movieDetails.getLong("id"));
                        movie.setTitle(movieDetails.getString("original_title"));
                        movie.setImageUrl("https://image.tmdb.org/t/p/w500"+movieDetails.getString("poster_path"));
                        movie.setOverview(movieDetails.getString("overview"));
                        movie.setAverageRating("Average rating : "+movieDetails.getString("vote_average"));
                        movie.setBackgroundImage("https://image.tmdb.org/t/p/w500"+movieDetails.getString("backdrop_path"));
                        movie.setRelease_date(movieDetails.getString("release_date"));
                        data.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return "";
        }

        private URL buildurl() {
            try {
                final String Movie_Base_URL = "https://api.themoviedb.org/3/movie/";
                final String APPID_PARAM = "api_key";
                String query = "language";
                String language = "en-US";
                String page = "page";
                Uri builtUri = Uri.parse(Movie_Base_URL).buildUpon()
                        .appendPath(movie_filter[0])
                        .appendQueryParameter(APPID_PARAM, getString(R.string.apikey))
                        .appendQueryParameter(query,language)
                        .appendQueryParameter(page,"1")
                        .build();
                return new URL(builtUri.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            PopularVIewAdapter gridAdapter = new PopularVIewAdapter(getContext(), R.layout.movie_card_layout, data);
            mRecyclerView.setAdapter(gridAdapter);
        }
    }
}
