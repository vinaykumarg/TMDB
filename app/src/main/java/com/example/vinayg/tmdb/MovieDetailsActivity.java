package com.example.vinayg.tmdb;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vinayg.tmdb.handler.HttpHandler;
import com.example.vinayg.tmdb.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import static com.example.vinayg.tmdb.adapters.PopularVIewAdapter.context;

public class MovieDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imageButton;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        imageButton = (ImageButton) findViewById(R.id.play);
        imageButton.setOnClickListener(this);
        movie = (Movie) getIntent().getSerializableExtra("movie");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                new fetchvideos().execute();
//                HttpHandler httpHandler = new HttpHandler();
//                String videos = httpHandler.makeServiceCall(buildurl().toString());
//                Log.d("movie",videos);
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
//                startActivity(browserIntent);

                break;
        }
    }
    private class fetchvideos extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            URL url = buildurl();
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url != null ? url.toString() : null);
            if (jsonStr!=null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(jsonArray.length()-1);
                    String key = jsonObject1.getString("key");
                    String link = "https://www.youtube.com/watch?v="+key;
                    return link;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(s));
            startActivity(intent);
        }
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
