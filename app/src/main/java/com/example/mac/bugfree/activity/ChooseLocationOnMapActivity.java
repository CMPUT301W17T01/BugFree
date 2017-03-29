package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.Intent;
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
import com.example.mac.bugfree.util.SaveFile;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.Projection;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Created by heyuehuang on 2017-03-29.
 */

public class ChooseLocationOnMapActivity extends MapActivity {

    private MapView mapView;
    GeoPoint currentPoint;
    GeoPoint startPoint;
    GeoPoint p;
    IGeoPoint  loc;
    private String longitude;
    private String latitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        // important! set your agent to prevent getting banned from the osm servers
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_choose_location_on_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = (MapView) findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);

        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(14);
        startPoint = new GeoPoint(53.56, -113.50);
        mapController.setCenter(startPoint);

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mapView);
        mapView.getOverlays().add(myScaleBarOverlay);

        addMyLocationPin();

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
            case R.id.activity_choose_location_on_map:

                setResult(RESULT_OK);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int actionType = ev.getAction();
        switch (actionType) {
            case MotionEvent.ACTION_UP:
                Projection projection = mapView.getProjection();
                loc = projection.fromPixels((int)ev.getX(), (int)ev.getY());
                longitude = Double.toString(((double)loc.getLongitudeE6())/1E6);
                latitude = Double.toString(((double)loc.getLatitudeE6())/1E6);

                Toast toast = Toast.makeText(getApplicationContext(), "Longitude: "+ longitude +" Latitude: "+ latitude , Toast.LENGTH_LONG);
                toast.show();

        }
        return super.dispatchTouchEvent(ev);
    }



}
