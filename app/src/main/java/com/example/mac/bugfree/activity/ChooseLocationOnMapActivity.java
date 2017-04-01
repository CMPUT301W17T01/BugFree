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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.util.CurrentLocation;
import com.example.mac.bugfree.util.SaveFile;

//import org.osmdroid.DefaultResourceProxyImpl;
//import org.osmdroid.ResourceProxy;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow;
import org.osmdroid.views.overlay.infowindow.InfoWindow;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;


import java.io.Serializable;
import java.util.List;

import static com.example.mac.bugfree.R.id.current_location;
import static com.example.mac.bugfree.R.id.map;

/**
 * Created by heyuehuang on 2017-03-29.
 */

public class ChooseLocationOnMapActivity extends AppCompatActivity implements MapEventsReceiver,Serializable {

    private MapView mapView;
    private GeoPoint currentPoint;
    private GeoPoint chosenLocation;
    private GeoPoint startPoint;
    private double lon;
    private double lat;
    private MapEventsOverlay mapEventsOverlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        // important! set your agent to prevent getting banned from the osm servers
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_choose_location_on_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = (MapView) findViewById(map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(18);
        startPoint = getCurrentPosition();
        mapController.setCenter(startPoint);

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

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
                clearAllLocation();
                return true;
            case R.id.activity_choose_location_on_map:
                if (chosenLocation != null){
                    Intent parent = new Intent();
                    parent.putExtra("chosenLocationLat", lat);
                    parent.putExtra("chosenLocationLon", lon);
                    setResult(RESULT_OK,parent);
                    finish();
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

    @Override public boolean singleTapConfirmedHelper(GeoPoint p) {
        Toast.makeText(this, "Tap on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();
//        InfoWindow.closeAllInfoWindowsOn(mapView);

        return true;
    }
    @Override public boolean longPressHelper(GeoPoint p) {
        //DO NOTHING FOR NOW:
        clearAllLocation();
        Polygon circle = new Polygon(this);
        circle.setPoints(Polygon.pointsAsCircle(p, 50.0));
        circle.setFillColor(0x12121212);
        circle.setStrokeColor(Color.RED);
        circle.setStrokeWidth(2);
        mapView.getOverlays().add(circle);
        mapView.invalidate();
        lat = p.getLatitudeE6()/1E6;
        lon = p.getLongitudeE6()/1E6;
        chosenLocation =  new GeoPoint(lat, lon);

//        circle.setInfoWindow(new BasicInfoWindow(org.osmdroid.bonuspack.R.layout.bonuspack_bubble, mapView));
//        circle.setTitle("Centered on "+p.getLatitude()+","+p.getLongitude());
        Toast.makeText(this, "Long Press on ("+p.getLatitude()+","+p.getLongitude()+")", Toast.LENGTH_SHORT).show();

        return false;
    }
    private GeoPoint getCurrentPosition() {
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
        return currentPoint;
    }

    private void clearAllLocation(){
        mapView.getOverlays().clear();
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);
        mapEventsOverlay = new MapEventsOverlay(this, this);
        mapView.getOverlays().add(0, mapEventsOverlay);
        chosenLocation = null;
        mapView.invalidate();
    }



}
