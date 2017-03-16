package com.example.vinayg.tmdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vinayg.tmdb.MovieDetailsActivity;
import com.example.vinayg.tmdb.R;


/**
 * Created by manasa.a on 14-03-2017.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private String[] mDataSet;
    private Context mContext;

    public FavoritesAdapter(Context context, String[] DataSet){
        mDataSet = DataSet;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        public TextView mTextView;
        public LinearLayout mLinearLayout;
        public ViewHolder(View v){
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tv);
            mLinearLayout = (LinearLayout) v.findViewById(R.id.ll);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent =  new Intent(v.getContext(), MovieDetailsActivity.class);
            intent.putExtra("position",getAdapterPosition());
            v.getContext().startActivity(intent);

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
        holder.mTextView.setText(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return  mDataSet.length;
    }
}
