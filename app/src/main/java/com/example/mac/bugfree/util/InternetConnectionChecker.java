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

    //Taken form http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
    // 2017-03-23 23:20
    public boolean isOnline(Context context) {
            ConnectivityManager cm =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
//            return netInfo != null && netInfo.isConnectedOrConnecting();
        return netInfo != null && netInfo.isConnected();
        }

}

