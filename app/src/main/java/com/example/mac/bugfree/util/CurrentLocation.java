package com.example.mac.bugfree.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.example.mac.bugfree.activity.CreateEditMoodActivity;

import org.osmdroid.util.GeoPoint;

/**
 * Created by mac on 2017-03-20.
 */

public class CurrentLocation {
    private Context context;

    public CurrentLocation(Context context) {
        this.context = context;
    }

    public void getCurrentLocation() {
        LocationManager locationManager;
        LocationListener locationListener;
        GeoPoint geoPoint;

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        try {
            locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

//        return geoPoint;
    }


}
