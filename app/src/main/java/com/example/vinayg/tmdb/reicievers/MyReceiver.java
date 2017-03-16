package com.example.vinayg.tmdb.reicievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
            // Check internet connection and accrding to state change the
            // text of activity by calling method
            if (isNetworkAvailable(context)) {
                Intent i = new Intent("INTERNET_LOST");
                i.putExtra("status", true);
                context.sendBroadcast(i);
            } else {
                Intent i = new Intent("INTERNET_LOST");
                i.putExtra("status", false);
                context.sendBroadcast(i);
            }
        }




    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
