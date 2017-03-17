package com.example.vinayg.tmdb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.vinayg.tmdb.R;

import com.example.vinayg.tmdb.database.MoviesDatabase;
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
    private Button likeBtn;
    Movie movie;

    public TopRatedAdapter(Context context, int layoutResourceId, ArrayList<Movie> data) {
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    @Override
    public TopRatedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_demo_01,parent,false);


        return new ViewHolder(v,data);
    }

    @Override
    public void onBindViewHolder(TopRatedAdapter.ViewHolder holder, int position) {
        Button likeBtn = holder.likeBtn ;
        ImageView image = holder.image;
        TextView imageTitle = holder.imageTitle;
        TextView release_date = holder.release_date;
        movie = data.get(position);
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        Boolean isSaved = database.checkIfsaved(movie);
        if (isSaved) {
            likeBtn.setBackgroundResource(R.drawable.like);
        } else {
            likeBtn.setBackgroundResource(R.drawable.likegrey);
        }
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



    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView imageTitle;
        public final ImageView image;
        public final TextView release_date;
        public final Button likeBtn;
        ArrayList<Movie> data;
        Movie movie;
        MoviesDatabase database;


        public ViewHolder(View itemView,ArrayList<Movie> data) {
            super(itemView);
            imageTitle = (TextView) itemView.findViewById(R.id.tvMovieTitle);
            image = (ImageView) itemView.findViewById(R.id.imgBanner);
            release_date = (TextView) itemView.findViewById(R.id.tvMovieReleaseDate);
            likeBtn = (Button) itemView.findViewById(R.id.btnLike);

            likeBtn.setOnClickListener(this);
            this.data = data;
            database = MoviesDatabase.getInstance(context);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnLike:
                    String name = imageTitle.getText()+"";
                    int i=0;
                    for(i=0;i<data.size();i++){
                        if(data.get(i).getTitle().equals(name)){
                            break;
                        }
                    }

                    Movie movie = data.get(i);

                    MoviesDatabase database = MoviesDatabase.getInstance(context);

                    if(!database.checkIfsaved(movie)) {
                        likeBtn.setBackgroundResource(R.drawable.like);
                        movie.setIsFavorite(1);
                        database.insertMovie(movie);
                        //ArrayList<Movie> list = database.getUserFavoriteMovies();

                    } else {
                        likeBtn.setBackgroundResource(R.drawable.likegrey);
                        movie.setIsFavorite(0);
                        database.deleteMovie(movie);
                    }
            }
        }
    }
}
