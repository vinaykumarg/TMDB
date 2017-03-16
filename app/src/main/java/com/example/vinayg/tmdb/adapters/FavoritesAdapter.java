package com.example.vinayg.tmdb.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.models.Movie;

import java.util.ArrayList;

import static com.example.vinayg.tmdb.adapters.PopularVIewAdapter.context;


/**
 * Created by manasa.a on 14-03-2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Movie> mData;

<<<<<<< HEAD
    public FavoritesAdapter(){

    }

    public FavoritesAdapter(Context context, ArrayList<Movie> DataSet){
        mData = DataSet;
        mContext = context;
    }

    public FavoritesAdapter(Context context){
=======
    public FavoritesAdapter(Context context,  ArrayList<Movie>  DataSet){
        mData = DataSet;
>>>>>>> ab24c7e4cc1a8c1e6c178befea3adffbd2b09b70
        mContext = context;
    }

    public void updateData(ArrayList<Movie> DataSet){
        mData = DataSet;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextView;
        public ImageView mImageView;
        public LinearLayout mLinearLayout;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv);
            mImageView =(ImageView) v.findViewById(R.id.imageViewFav);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
        }
    }


    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.favorite_view,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
<<<<<<< HEAD
        holder.mTextView.setText(mData.get(position).getTitle());
        Glide.with(context).load(mData.get(position).getImageUrl()).crossFade().fitCenter()
                .into(holder.mImageView);
=======
//        holder.mTextView.setText(mDataSet[position]);
>>>>>>> ab24c7e4cc1a8c1e6c178befea3adffbd2b09b70
    }

    @Override
    public int getItemCount() {
        return  mData.size();
    }
}
