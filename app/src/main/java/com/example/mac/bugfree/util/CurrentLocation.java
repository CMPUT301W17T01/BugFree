package com.example.mac.bugfree.util;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import org.osmdroid.util.GeoPoint;


/**
 * Created by mac on 2017-03-20.
 */

public class CurrentLocation implements LocationListener {

    public GeoPoint mcurrentLocation;

    @Override
    public void onLocationChanged(Location location) {
        int latitude = (int) (location.getLatitude() * 1E6);
        int longitude = (int) (location.getLongitude() * 1E6);
        mcurrentLocation = new GeoPoint(latitude, longitude);
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
}
