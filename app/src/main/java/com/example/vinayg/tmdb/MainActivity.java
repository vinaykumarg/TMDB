package com.example.vinayg.tmdb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vinayg.tmdb.adapters.pagerAdapter;
import com.example.vinayg.tmdb.database.MoviesDatabase;
import com.example.vinayg.tmdb.reicievers.MyReceiver;

public class MainActivity extends AppCompatActivity {
    private String[] movie_filter;
    private ImageView networkStatus;
    private ViewPager viewPager;
    private final BroadcastReceiver mMyReceiver = new MyReceiver();
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            // This method will be invoked when a new page becomes selected.
//            @Override
//            public void onPageSelected(int position) {
//                Toast.makeText(MainActivity.this,
//                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();
//                Log.d("called main",position+" position ");
//            }
//
//            // This method will be invoked when the current page is scrolled
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                // Code goes here
//                Toast.makeText(MainActivity.this,
//                        "onPageScrolled: " + position, Toast.LENGTH_SHORT).show();
//                Log.d("called main1",position+" position ");
//            }
//
//            // Called when the scroll state changes:
//            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                // Code goes here
//                Toast.makeText(MainActivity.this,
//                        "onPageScrollStateChanged: " + state, Toast.LENGTH_SHORT).show();
//            }
//        });
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
                Log.d(" calle MAIN CTIVITY"," tab3 called");

            }
        });




        }

    public void registerReciever() {

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mMyReceiver, filter);
    }
    public void unregisterReciever() {
        try {
            unregisterReceiver(mMyReceiver);
        } catch (Exception e) {

        }

    }



    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReciever();
        registerReceiver(broadcastReceiver, new IntentFilter("INTERNET_LOST"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReciever();
        unregisterReceiver(broadcastReceiver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
