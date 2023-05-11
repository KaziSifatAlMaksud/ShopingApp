package com.example.aexpress.activities;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class InternetConnectionChangeReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean isInternetConnected = isNetworkAvailable(context);
            if (isInternetConnected) {
                // Internet connection is available
             //   Toast.makeText(context, "Internet connection is ON", Toast.LENGTH_SHORT).show();
            } else {
                // Internet connection is not available
                Toast.makeText(context, "No Internet connection. \n Please Check Your Internet Connection.", Toast.LENGTH_SHORT).show();



            }
        }

    }



    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }
}

