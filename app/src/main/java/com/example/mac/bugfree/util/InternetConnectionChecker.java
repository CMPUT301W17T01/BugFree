package com.example.mac.bugfree.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *  This is a method class, the use is to check internet connectivity and return a boolean.
 *  How to use:
 *  1. InternetConnectionChecker checker = new InternetConnectionChecker();
 *  2. Context context = getApplicationContext();
 *  3. final boolean isOnline = checker.isOnline(context);
 * @author Zhi Li
 */

public class InternetConnectionChecker {
    public InternetConnectionChecker(){}

    public boolean isOnline(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

    }

