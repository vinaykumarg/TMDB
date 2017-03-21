package com.example.vinayg.tmdb.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.vinayg.tmdb.activities.MovieDetailsActivity;
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.adapters.FavoritesAdapter;
import com.example.vinayg.tmdb.database.MoviesDatabase;
import com.example.vinayg.tmdb.listeners.ClickListener;
import com.example.vinayg.tmdb.listeners.RecyclerTouchListener;
import com.example.vinayg.tmdb.models.Movie;

import java.util.ArrayList;

/**
 * Created by manasa.a on 15-03-2017.
 */
public class FavoriteFragment extends Fragment {
    private  static String TAG = FavoriteFragment.class.getSimpleName();
    FavoritesAdapter mAdapter;
    Context mContext;
    View mV;
    ArrayList<Movie> favMoviesList;
    private MoviesDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mV = inflater.inflate(R.layout.fragment_favorite, container, false); // Inflate the layout for this fragment
        mContext =  getActivity().getApplicationContext();
        db = MoviesDatabase.getInstance(getContext());
        favMoviesList =db.getUserFavoriteMovies();

        setRecyclerView();
        return mV;
    }

    private void setRecyclerView() {
        RecyclerView mRecyclerView = (RecyclerView) mV.findViewById(R.id.recyclerViewFavorites);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext,2);// StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);//
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FavoritesAdapter(mContext, favMoviesList);
        mRecyclerView.setAdapter(mAdapter);        // Set the adapter for RecyclerView

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("movie", favMoviesList.get(position));
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }

            @Override
            public void onDoubleClick(View view, int position) {

            }
        }));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d("called ", "  visible + "+isVisibleToUser+"");
        if (isVisibleToUser) {
            if(favMoviesList!=null) {
                favMoviesList.clear();
                favMoviesList.addAll(db.getUserFavoriteMovies());
                if(favMoviesList.size()==0){
                    Toast.makeText(getContext(),"No favourites added", Toast.LENGTH_SHORT).show();
                }
                mAdapter.updateData(favMoviesList);
            }
        }

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
//        if (menuVisible) {
//            if(favMoviesList!=null) {
//                favMoviesList.clear();
//                favMoviesList.addAll(db.getUserFavoriteMovies());
//                if(favMoviesList.size()==0){
//                    Toast.makeText(getContext(),"No favourites added", Toast.LENGTH_SHORT).show();
//                }
//                mAdapter.updateData(favMoviesList);
//            }
//        }
    }
}
