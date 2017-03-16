package com.example.vinayg.tmdb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinayg.tmdb.models.Movie;

import static com.example.vinayg.tmdb.adapters.PopularVIewAdapter.context;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        ImageView videoImage = (ImageView) findViewById(R.id.imageView1);
        ImageView movieBanner = (ImageView)findViewById(R.id.imageView2);
        TextView title = (TextView) findViewById(R.id.title);
        TextView releaseDate = (TextView) findViewById(R.id.releasedate);
        TextView rating = (TextView) findViewById(R.id.rating);
        TextView overview = (TextView) findViewById(R.id.summary);
        overview.setText(movie.getOverview());
        title.setText(movie.getTitle());
        releaseDate.setText(movie.getRelease_date());
        rating.setText(movie.getAverageRating());

        Glide
                .with(context)
                .load(movie.getImageUrl())
                .into(movieBanner);

        Glide
                .with(context)
                .load(movie.getBackgroundImage())
                .into(videoImage);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
