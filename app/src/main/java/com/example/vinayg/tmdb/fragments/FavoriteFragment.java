package com.example.vinayg.tmdb.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.vinayg.tmdb.MovieDetailsActivity;
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.adapters.FavoritesAdapter;
import com.example.vinayg.tmdb.database.MoviesDatabase;
import com.example.vinayg.tmdb.listeners.ClickListener;
import com.example.vinayg.tmdb.listeners.RecyclerTouchListener;
import com.example.vinayg.tmdb.models.Movie;

import java.util.ArrayList;

/**
 * Created by manasa.a on 15-03-2017.
 *Reference:
 * https://android--code.blogspot.in/2015/12/android-recyclerview-grid-layout-example.html
 * https://guides.codepath.com/android/using-the-recyclerview
 */
public class FavoriteFragment extends Fragment {
    Context mContext;
    View mV;
    ArrayList<Movie> favMoviesList;
    // Initialize a new String array
    String[] animals = {
            "Aardvark", "Albatross", "Alligator", "Alpaca", "Ant", "Anteater", "Antelope", "Ape", "Armadillo", "Donkey", "Baboon", "Badger", "Barracuda", "Bear", "Beaver", "Bee", "Armadillo", "Donkey", "Baboon", "Badger", "Barracuda", "Bear", "Beaver", "Bee"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mV = inflater.inflate(R.layout.fragment_favorite, container, false); // Inflate the layout for this fragment
        mContext =  getActivity().getApplicationContext();
        MoviesDatabase db = MoviesDatabase.getInstance(getContext());
        favMoviesList =db.getUserFavoriteMovies();
//        if(favMoviesList.size()==0){
//            Toast.makeText(getContext(),"No favourites added", Toast.LENGTH_SHORT).show();
//        }else {
            setRecyclerView();
        //}
        return mV;
    }

    private void setRecyclerView() {
        RelativeLayout mRelativeLayout = (RelativeLayout) mV.findViewById(R.id.relativeLayout); // Get the widgets reference from XML layout
        RecyclerView mRecyclerView = (RecyclerView) mV.findViewById(R.id.recyclerViewFavorites);
        RecyclerView.LayoutManager mLayoutManager =new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        // GridLayoutManager(mContext,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new FavoritesAdapter(mContext,animals);  // Initialize a new instance of RecyclerView Adapter instance
        //RecyclerView.Adapter mAdapter = new FavoritesAdapter(mContext,favMoviesList);
        mRecyclerView.setAdapter(mAdapter);        // Set the adapter for RecyclerView
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent =  new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
            @Override
            public void onLongClick(View view, int position) {

            }

        }));

    }
}
