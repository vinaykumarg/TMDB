package com.example.vinayg.tmdb.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.vinayg.tmdb.R;
import com.example.vinayg.tmdb.adapters.pagerAdapter;
import com.example.vinayg.tmdb.receivers.MyReceiver;

public class MainActivity extends AppCompatActivity {
    private ImageView networkStatus;
    private ViewPager viewPager;
    private final BroadcastReceiver mMyReceiver = new MyReceiver();
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // internet lost alert dialog method call from here...
            boolean status = intent.getBooleanExtra("status", false);
            if (!status) {
                networkStatus.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.INVISIBLE);

            } else {
                networkStatus.setVisibility(View.INVISIBLE);
                viewPager.setVisibility(View.VISIBLE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        String[] movie_filter = new String[]{"popular", "top_rated", "favourite"};
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

    private void registerReceiver() {

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mMyReceiver, filter);
    }
    private void unregisterReceiver() {
        unregisterReceiver(mMyReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver();
        registerReceiver(broadcastReceiver, new IntentFilter("INTERNET_LOST"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
        unregisterReceiver(broadcastReceiver);

    }

}
