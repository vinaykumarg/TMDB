package com.example.vinayg.tmdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.vinayg.tmdb.R;

import com.example.vinayg.tmdb.models.Movie;

import java.util.ArrayList;

/**
 * Created by saili.k on 16-03-2017.
 */

//public class TopRatedAdapter {
//}
//public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ListItemViewHolder> {



//    private ArrayList<Model> items;
//    private SparseBooleanArray selectedItems;
//
//    public TopRatedAdapter(ArrayList<Model> modelData) {
//        if (modelData == null) {
//            throw new IllegalArgumentException("modelData must not be null");
//        }
//        items = modelData;
//        selectedItems = new SparseBooleanArray();
//    }
//
//    @Override
//    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View itemView = LayoutInflater.
//                from(viewGroup.getContext()).
//                inflate(R.layout.item_demo_01, viewGroup, false);
//        return new ListItemViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
//        Model model = items.get(position);
//        viewHolder.name.setText(String.valueOf(model.getName()));
//        viewHolder.genre.setText(String.valueOf(model.getAge()));
//        viewHolder.itemView.setActivated(selectedItems.get(position, false));
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
//        TextView name;
//        TextView genre;
//
//        public ListItemViewHolder(View itemView) {
//            super(itemView);
////            name = (TextView) itemView.findViewById(R.id.moviename_tv);
////            genre = (TextView) itemView.findViewById(R.id.moviegenre_tv);
//            name = (TextView) itemView.findViewById(R.id.tvMovieTitle);
//            genre = (TextView) itemView.findViewById(R.id.tvMovieReleaseDate);
//        }
//    }
public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.ViewHolder>{
    public static Context context;
    private int layoutResourceId;
    private ArrayList<Movie> data;

    public TopRatedAdapter(Context context, int layoutResourceId, ArrayList<Movie> data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public TopRatedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_demo_01,parent,false);
        return new TopRatedAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TopRatedAdapter.ViewHolder holder, int position) {
        ImageView image = holder.image;
        TextView imageTitle = holder.imageTitle;
        TextView release_date = holder.release_date;
        Movie movie = data.get(position);
        Glide
                .with(context)
                .load(movie.getImageUrl())
                .crossFade()
                .fitCenter()
                .into(image);
        imageTitle.setText(movie.getTitle());
        release_date.setText(movie.getRelease_date());
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        public final TextView imageTitle;
        public final ImageView image;
        public final TextView release_date;

        public ViewHolder(View itemView) {
            super(itemView);
            imageTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            image = (ImageView) itemView.findViewById(R.id.imgBanner);
            release_date = (TextView) itemView.findViewById(R.id.tvMovieReleaseDate);
        }
    }
}
