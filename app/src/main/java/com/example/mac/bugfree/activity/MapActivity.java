package com.example.mac.bugfree.activity;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.util.CurrentLocation;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import static android.widget.Toast.LENGTH_LONG;

public class MapActivity extends AppCompatActivity {
    private MapView mOpenMapView;
    GeoPoint currentPoint;
    GeoPoint startPoint;
    MyLocationNewOverlay myLocationOverlay = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        // important! set your agent to prevent getting banned from the osm servers
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView add_tab = (ImageView) findViewById(R.id.add_tab_map);
        add_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        ImageView home_tab = (ImageView) findViewById(R.id.home_tab_map);
        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        mOpenMapView = (MapView) findViewById(R.id.map);
        mOpenMapView.setTileSource(TileSourceFactory.MAPNIK);

        mOpenMapView.setBuiltInZoomControls(true);
        mOpenMapView.setMultiTouchControls(true);

        IMapController mapController = mOpenMapView.getController();
        mapController.setZoom(14);
        startPoint = new GeoPoint(53.56, -113.50);
        mapController.setCenter(startPoint);



        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mOpenMapView);
        mOpenMapView.getOverlays().add(myScaleBarOverlay);

        //Add MyLocationOverlay
        this.myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), mOpenMapView);
        this.myLocationOverlay.enableMyLocation();
        mOpenMapView.getOverlays().add(this.myLocationOverlay);


        //your items
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("Canada", "Country", new GeoPoint(45.4, -75.666667))); // Lat/Lon decimal degrees
        items.add(new OverlayItem("Calgary", "City", new GeoPoint(51.03, -114.05))); // Lat/Lon decimal degrees
        items.add(new OverlayItem("Edmonton", "City", new GeoPoint(53.56, -113.50))); // Lat/Lon decimal degrees


        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this, items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
//                        double a = distanceBetweenPoints();

                        Toast.makeText(MapActivity.this, item.getTitle() + "\n"
                                        + item.getPoint().getLatitudeE6() + " : " + item.getPoint().getLongitudeE6(),
                                LENGTH_LONG).show();
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {

                        return false;
                    }
                });
        mOverlay.setFocusItemsOnTap(true);

        mOpenMapView.getOverlays().add(mOverlay);

    }


    public void onResume() {
        super.onResume();
        org.osmdroid.config.Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

    }

    public double distanceBetweenPoints(GeoPoint moodPoint) {
        try {
            CurrentLocation locationListener = new CurrentLocation();
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                int latitude = (int) (location.getLatitude() * 1E6);
                int longitude = (int) (location.getLongitude() * 1E6);
                currentPoint = new GeoPoint(latitude, longitude);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Location currentLocation = new Location("Current Location");
        currentLocation.setLatitude(currentPoint.getLatitudeE6() / 1E6);
        currentLocation.setLongitude(currentPoint.getLongitudeE6() / 1E6);

        Location moodLocation = new Location("Mood's location");

        moodLocation.setLatitude(moodPoint.getLatitudeE6() / 1E6);
        moodLocation.setLongitude(moodPoint.getLongitudeE6() / 1E6);
        double distance = currentLocation.distanceTo(moodLocation);
        return distance;
    }



    public boolean showPin(){
        return true;
    }

    public boolean showDetail(){
        return true;
    }



}
