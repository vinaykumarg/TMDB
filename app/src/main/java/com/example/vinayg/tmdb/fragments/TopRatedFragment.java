package com.example.vinayg.tmdb.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vinayg.tmdb.R;

import java.util.ArrayList;


//public class TopRatedFragment extends Fragment {
//    TopRatedAdapter adapter;
//    RecyclerView recyclerView;
//    ArrayList<Model> demoData;
//
//
//
//
//    public TopRatedFragment() {
//        // Required empty public constructor
//    }
//
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//
//        return inflater.inflate(R.layout.fragment_top_rated, container, false);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        recyclerView = (RecyclerView) getView().findViewById(R.id.myList);
//        recyclerView.setHasFixedSize(true);
//        Context c = getActivity().getApplicationContext();
//        RecyclerView.LayoutManager llm = new GridLayoutManager(c,2);
//        //llm.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(llm);
//
//        demoData = new ArrayList<Model>();
//        //char c = 'A';
//        for(int i=0;i<20;i++){
//            Model model = new Model();
//            model.setName("name: "+i);
//            model.setAge(i);
//            demoData.add(model);
//        }
////        for (byte i = 0; i < 20; i++) {
////            Model model = new Model();
////            model.setName("c++");
////            model.setAge(i);
////            //model.age = (byte) (20 + i);
////            demoData.add(model);
////        }
//        adapter = new TopRatedAdapter(demoData);
//        recyclerView.setAdapter(adapter);
//    }
//}



import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Button;

import com.example.vinayg.tmdb.MovieDetailsActivity;
import com.example.vinayg.tmdb.database.MoviesDatabase;
import com.example.vinayg.tmdb.listeners.ClickListener;
import com.example.vinayg.tmdb.listeners.RecyclerTouchListener;
import com.example.vinayg.tmdb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vinay.g.
 */
public class TopRatedFragment extends Fragment {
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
                Button likeBtn = (Button) view.findViewById(R.id.btnLike) ;
                Movie movie = data.get(position);
                if(movie.getIsFavorite()==0) {
                    likeBtn.setBackgroundResource(R.drawable.like);
                    MoviesDatabase database = MoviesDatabase.getInstance(getContext());
                    movie.setIsFavorite(1);
                    database.insertMovie(movie);

                } else {
                    likeBtn.setBackgroundResource(R.drawable.likegrey);
                    movie.setIsFavorite(0);
                }


                //likeBtn.setBackgroundResource(R.drawable.like);

            }

            @Override
            public void onLongClick(View view, int position) {
                Intent intent =  new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("movie",data.get(position));
                startActivity(intent);
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
            String jsonStr = sh.makeServiceCall(url.toString());
            Log.d(TAG,jsonStr + " called ");
            if (jsonStr != null) {


                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray movies = jsonObj.getJSONArray("results");
                    for (int i=0;i<movies.length();i++){
                        JSONObject movieDetails = movies.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setTitle(movieDetails.getString("original_title"));
                        movie.setImageUrl("https://image.tmdb.org/t/p/w500"+movieDetails.getString("poster_path"));
                        Log.d("movie",movieDetails.toString());
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
