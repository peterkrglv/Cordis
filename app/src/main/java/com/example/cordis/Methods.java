package com.example.cordis;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TransportInfo;
import android.os.Build;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.textfield.TextInputLayout;

public class Methods {
    public static void setPasswordIcon(TextInputLayout layout, View view) {
        layout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layout.getEditText().getInputType() == 129) {
                    layout.setEndIconDrawable(ResourcesCompat.getDrawable(view.getResources(), R.drawable.guitar_pick, null));
                    layout.getEditText().setInputType(145);
                } else {
                    layout.setEndIconDrawable(ResourcesCompat.getDrawable(view.getResources(), R.drawable.guitar_pick_filled, null));
                    layout.getEditText().setInputType(129);
                }
            }
        });
    }

    public static Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false;
            }
            NetworkCapabilities activeNetwork = connectivityManager.getNetworkCapabilities(network);
            if (activeNetwork == null) {
                return false;
            }
            Boolean hasTransport =
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            return hasTransport;
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null) {
                return false;
            }
            return networkInfo.isConnected();
        }
    }
}
