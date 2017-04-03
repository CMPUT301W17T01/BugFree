package com.example.mac.bugfree.activity;

import java.io.File;
import java.util.ArrayList;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.UserNameList;
import com.example.mac.bugfree.util.CurrentLocation;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.util.LoadFile;

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

/**
 * This class is aim to choose a location on map and return the location to Edit UI and Create UI.
 * <br> In this class, user interaction and file manipulation is performed.
 *
 * @author Heyue Huang & Xinlei Chen
 * @version 2.8.7
 * @since 1.0
 */
public class MapActivity extends AppCompatActivity {
    private static final String FILENAME2 = "filter.sav";

    private MapView mOpenMapView;
    /**
     * The Current point.
     */
    GeoPoint currentPoint;
    /**
     * The Start point.
     */
    GeoPoint startPoint;
    /**
     * The My location overlay.
     */
    MyLocationNewOverlay myLocationOverlay = null;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 301;

    public MapView getmOpenMapView(){
        return mOpenMapView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        // set the agent to prevent getting banned from the osm servers
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        // add Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add Tab
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

        // add map
        mOpenMapView = (MapView) findViewById(R.id.map);
        mOpenMapView.setTileSource(TileSourceFactory.MAPNIK);
        // add zoom in and out button
        mOpenMapView.setBuiltInZoomControls(true);
        mOpenMapView.setMultiTouchControls(true);
        // set center
        IMapController mapController = mOpenMapView.getController();
        mapController.setZoom(14);
        startPoint = new GeoPoint(53.56, -113.50);
        mapController.setCenter(startPoint);

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mOpenMapView);
        mOpenMapView.getOverlays().add(myScaleBarOverlay);
    }


    public void onResume() {
        super.onResume();
        org.osmdroid.config.Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        //the dialog for checking the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Show an explanation
                if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // explanation
                } else {
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(MapActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                }
            }else {
                Toast.makeText(getApplicationContext(), "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
                addMyLocationPin();
                addMoodEventPin();
            }
        }

//        addMyLocationPin();
//        addMoodEventPin();
    }


    // Taken From https://developer.android.com/reference/android/support/v4/app/ActivityCompat.OnRequestPermissionsResultCallback.html
    /**
     * Check if there is a permission for the location
     * @param requestCode: the request code
     * @param permissions: the permission int
     * @param grantResults: the grant results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                    // add my location and all moods
                    addMyLocationPin();
                    addMoodEventPin();

                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    // Taken from http://android-er.blogspot.ca/search?q=OpenStreetMap
    /**
     * Add a yellow person to show my current location
     */
    public void addMyLocationPin() {
        //Add MyLocationOverlay
        this.myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()) , mOpenMapView);
        this.myLocationOverlay.enableMyLocation();
        mOpenMapView.getOverlays().add(this.myLocationOverlay);
    }

    /**
     * If there is no filter mood event list, show all participants' most recent mood in the map,
     * otherwise, show the filtered mood events.
     */
    public void addMoodEventPin() {
        if (fileExists(getApplicationContext(), FILENAME2)) {
            loadFromFilterFile(getApplicationContext());
        } else {
            // load all participants' moods
            loadAllParticipant();
        }
    }

    /**
     * check if the file is exits
     *
     * @param context: Application context
     * @param filename: the file name
     * @return: boolean
     */
    private boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    /**
     * Load the filtered moods from filter.sav, add all moods to overlay and display them
     *
     * @param context: Application Context
     */
    private void loadFromFilterFile(Context context) {
        LoadFile loadFile = new LoadFile();
        ArrayList<MoodEvent> moodEventArrayList = new ArrayList<>();
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        moodEventArrayList = loadFile.loadFilteredMoodEventList(context);
        MoodEventList moodEventList = new MoodEventList(moodEventArrayList);

        //the overlay
        int len = moodEventList.getCount();
        for (int i = 0; i<len; i++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(i);
            GeoPoint geoPoint = moodEvent.getLocation();

            if (geoPoint != null) {
                items.add(new OverlayItem(moodEvent.getBelongsTo(), moodEvent.getMoodState(), geoPoint));
            }
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this,items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //Print the location's position
                        Toast.makeText(MapActivity.this, item.getTitle() + "\n"
                                        + item.getPoint().getLatitude() + " : " + item.getPoint().getLongitude(),
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

    /**
     * Load all participants' moods, check if it is in the 5km, add all satisfied
     * moods to overlay and display them.
     */
    private void loadAllParticipant() {
        UserNameList userList = new UserNameList();
        MoodEventList moodEventList = new MoodEventList();
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask.execute("name");
        try{
            userList = getUserListTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the UserList out of the async object");
        }
        ArrayList<String> arrayList = userList.getUserNameList();

        ElasticsearchUserController.GetUserTask getUserTask1;
        for (String userName : arrayList) {
            getUserTask1 = new  ElasticsearchUserController.GetUserTask();
            getUserTask1.execute(userName);
            try {
                User user = getUserTask1.get();
                MoodEventList userMoodList = user.getMoodEventList();
                userMoodList.sortByDate();
                moodEventList.addMoodEvent(userMoodList.getMoodEvent(0));
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        }

        int len = moodEventList.getCount();
        for (int i = 0; i<len; i++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(i);
            GeoPoint geoPoint = moodEvent.getLocation();

            if (geoPoint != null) {
                if (distanceBetweenPoints(geoPoint) <= 5){
                    items.add(new OverlayItem(moodEvent.getBelongsTo(), moodEvent.getMoodState(), geoPoint));
                }
            }
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this,items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //Print the location's position
                        Toast.makeText(MapActivity.this, item.getTitle() + "\n"
                                        + item.getPoint().getLatitude() + " : " + item.getPoint().getLongitude(),
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

    /**
     * Calculate distance between the mood's location and the current location.
     *
     * @param moodPoint: the mood point
     * @return the double: the distance in km
     */
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
        return distance/1000;
    }


}
