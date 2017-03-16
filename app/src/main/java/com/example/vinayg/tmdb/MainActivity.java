package com.example.vinayg.tmdb;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.vinayg.tmdb.adapters.pagerAdapter;

public class MainActivity extends AppCompatActivity {
    private String[] movie_filter;
    private ImageView networkStatus;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        movie_filter = new String[]{"popular","top_rated","favourite"};
        tabLayout.addTab(tabLayout.newTab().setText(movie_filter[0].toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(movie_filter[1].toUpperCase()));
        tabLayout.addTab(tabLayout.newTab().setText(movie_filter[2].toUpperCase()));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        // Set up the ViewPager with the sections adapter.
        networkStatus  = (ImageView) findViewById(R.id.network);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final pagerAdapter adapter = new pagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        }
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isNetworkAvailable(getApplicationContext())) {
            networkStatus.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.INVISIBLE);

        } else {
            networkStatus.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.VISIBLE);
        }
    }
}
