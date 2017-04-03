package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.util.CurrentLocation;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.ScaleBarOverlay;

/**
 * This class is aim to choose a location on map and return the location to Edit UI and Create UI.
 * <br> In this class, user interaction and file manipulation is performed.
 *
 * @author Heyue Huang 2017-03-29
 * @version 1.4.2
 * @since 1.0
 */
public class ChooseLocationOnMapActivity extends MapActivity implements MapEventsReceiver {

    private MapView mapView;
    private GeoPoint currentPoint;
    private GeoPoint chosenLocation;
    private GeoPoint startPoint;
    private double lon;
    private double lat;
    private MapEventsOverlay mapEventsOverlay;
    public GeoPoint getChosenLocation(){
        return chosenLocation;
    }
    public GeoPoint getCurrentPoint(){
        return currentPoint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        // set the agent to prevent getting banned from the osm servers
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_choose_location_on_map);

        // add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add map
        mapView = (MapView) findViewById(R.id.map_choose);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        // add zoom in and out button
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        // set center
        IMapController mapController = mapView.getController();
        mapController.setZoom(18);
        startPoint = getCurrentPosition();
        mapController.setCenter(startPoint);
        //add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

        //add the circle overlay to map
        mapEventsOverlay = new MapEventsOverlay(this, this);
        mapView.getOverlays().add(0, mapEventsOverlay);

    }

    public void onResume() {
        super.onResume();
        org.osmdroid.config.Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    // Taken from http://stackoverflow.com/questions/35648913/how-to-set-menu-to-toolbar-in-android
    // combine the menu and the layout
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_location, menu);
        return true;
    }

    // Taken from http://stackoverflow.com/questions/7479992/handling-a-menu-item-click-event-android
    // Determines if Action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle presses on the action bar items
        switch (item.getItemId()) {
            // if the item in tool bar is selected
            case R.id.action_clear_choose:
                // clear the map
                clearAllLocation();
                return true;
            case R.id.activity_choose_location_on_map:
                // if a circle is placed
                if (chosenLocation != null){
                    // pass its lat and lon to the destined intent
                    Intent parent = new Intent();
                    parent.putExtra("chosenLocationLat", lat);
                    parent.putExtra("chosenLocationLon", lon);
                    setResult(RESULT_OK,parent);
                    finish();
                    // else, return nothing but a flag
                } else {
                    Intent parent = new Intent();
                    String mes = "isnull";
                    parent.putExtra("flag", mes);
                    setResult(RESULT_OK,parent);
                    finish();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *  When the user tap on the map, do the following
     */
    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        Toast.makeText(this, "Tap on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();
        return true;
    }

    /**
     * When the user long presses on the map, add a circle on the map
     */
    @Override
    public boolean longPressHelper(GeoPoint p) {
        // clear the previous choice
        clearAllLocation();
        // draw a circle when
        Polygon circle = new Polygon(this);
        circle.setPoints(Polygon.pointsAsCircle(p, 50.0));
        circle.setFillColor(0x12121212);
        circle.setStrokeColor(Color.RED);
        circle.setStrokeWidth(2);
        mapView.getOverlays().add(circle);
        mapView.invalidate();
        // add the location to chosenLocation
        lat = p.getLatitudeE6()/1E6;
        lon = p.getLongitudeE6()/1E6;
        chosenLocation =  new GeoPoint(lat, lon);

        Toast.makeText(this, "Successfully choose ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();

        return false;
    }

    /**
     * Get the user's current location by gps.
     *
     * @return currentPoint: The current position which get from GPS
     */
    private GeoPoint getCurrentPosition() {
        try {
            // get gps
            CurrentLocation locationListener = new CurrentLocation();
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            // if not null then set current point
            if (location != null) {
                int latitude = (int) (location.getLatitude() * 1E6);
                int longitude = (int) (location.getLongitude() * 1E6);
                currentPoint = new GeoPoint(latitude, longitude);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return currentPoint;
    }

    /**
     * Clear all overlay, and empty previous stored current location
     */
    private void clearAllLocation(){
        // clear all overlay
        mapView.getOverlays().clear();
        // add back scale and circle base
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);
        mapEventsOverlay = new MapEventsOverlay(this, this);
        mapView.getOverlays().add(0, mapEventsOverlay);
        // empty the chosenLocation
        chosenLocation = null;
        mapView.invalidate();
    }
}
