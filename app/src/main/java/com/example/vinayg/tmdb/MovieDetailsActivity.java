package com.example.vinayg.tmdb;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.vinayg.tmdb.database.MoviesDatabase;
import com.example.vinayg.tmdb.handler.HttpHandler;
import com.example.vinayg.tmdb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.vinayg.tmdb.adapters.PopularVIewAdapter.context;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton;
    Movie movie;
    private String movielink;
    private Button likeBtn;
    private VideoView videoview;
    private ImageView videoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        imageButton = (ImageButton) findViewById(R.id.play);
        imageButton.setOnClickListener(this);
        likeBtn = (Button) findViewById(R.id.likebtn);
        likeBtn.setOnClickListener(this);
        movie = (Movie) getIntent().getSerializableExtra("movie");
        MoviesDatabase database = MoviesDatabase.getInstance(context);
        Boolean isSaved = database.checkIfsaved(movie);
        if (isSaved) {
            likeBtn.setBackgroundResource(R.drawable.like);
        } else {
            likeBtn.setBackgroundResource(R.drawable.likegrey);
        }
        movielink = "https://www.themoviedb.org/movie/"+movie.getMovieId()+"-"+movie.getTitle();
        videoview = (VideoView) findViewById(R.id.VideoView);
        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoview);
        mc.setMediaPlayer(videoview);
        videoview.setMediaController(mc);
        videoImage = (ImageView) findViewById(R.id.imageView1);
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
            case R.id.share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. \n"+movielink;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"movie details");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                new fetchvideos().execute();
                break;
            case R.id.likebtn:
                MoviesDatabase database = MoviesDatabase.getInstance(getApplicationContext());
                if(!database.checkIfsaved(movie)) {
                    likeBtn.setBackgroundResource(R.drawable.like);
                    movie.setIsFavorite(1);
                    database.insertMovie(movie);
                } else {
                    likeBtn.setBackgroundResource(R.drawable.likegrey);
                    movie.setIsFavorite(0);
                    database.deleteMovie(movie);
                }
                break;
        }
    }
    private class fetchvideos extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            URL url = buildurl();
            HttpHandler sh = new HttpHandler();
            ArrayList<String> key = new ArrayList<>();
            String jsonStr = sh.makeServiceCall(url != null ? url.toString() : null);
            if (jsonStr!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        key.add(jsonObject1.getString("key"));
                    }
//                    String link = "https://www.youtube.com/watch?v="+key;
//                    Log.d("link",jsonObject1.toString());
                    return key;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> keys) {
            super.onPostExecute(keys);
//            Uri uri = Uri.parse(s);
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(uri);
//            startActivity(intent);
            Intent intent = new Intent(getApplicationContext(),YouTubeActivity.class);
            intent.putStringArrayListExtra("key",keys);
            startActivity(intent);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    private URL buildurl() {
        try {
            final String Movie_Base_URL = "https://api.themoviedb.org/3/movie/"+movie.getMovieId()+"/";
            final String APPID_PARAM = "api_key";
            final String VIDEO_PARAM = "videos";
            String query = "language";
            String language = "en-US";
            String page = "page";
            Uri builtUri = Uri.parse(Movie_Base_URL).buildUpon()
                    .appendPath(VIDEO_PARAM)
                    .appendQueryParameter(APPID_PARAM, getString(R.string.apikey))
                    .appendQueryParameter(query,language)
                    .build();
            return new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
