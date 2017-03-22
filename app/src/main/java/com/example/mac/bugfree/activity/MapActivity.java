package com.example.mac.bugfree.activity;

import java.io.File;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MapActivity extends AppCompatActivity {
    private static final String FILENAME2 = "filter.sav";

    private MapView mOpenMapView;
    //private String currentUserName;

    ArrayList<OverlayItem> anotherOverlayItemArray;

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

        // get the current user name
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        currentUserName = pref.getString("currentUser", "");

        mOpenMapView = (MapView) findViewById(R.id.map);
        mOpenMapView.setTileSource(TileSourceFactory.MAPNIK);

        mOpenMapView.setBuiltInZoomControls(true);
        mOpenMapView.setMultiTouchControls(true);

        IMapController mapController = mOpenMapView.getController();
        mapController.setZoom(14);
        GeoPoint startPoint = new GeoPoint(53.56, -113.50);
        mapController.setCenter(startPoint);

        //Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mOpenMapView);
        mOpenMapView.getOverlays().add(myScaleBarOverlay);

        addMyLocationPin();
        addMoodEventPin();

    }


    public void onResume() {
        super.onResume();
        org.osmdroid.config.Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

    }

    public void addMyLocationPin() {
        //Add MyLocationOverlay
        this.myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()) , mOpenMapView);
        this.myLocationOverlay.enableMyLocation();
        mOpenMapView.getOverlays().add(this.myLocationOverlay);
    }

    public void addMoodEventPin() {
        if (fileExists(getApplicationContext(), FILENAME2)) {
            loadFromFilterFile(getApplicationContext());
        } else {
            // TODO: add all participate's moodEvent in 5km
            //loadAllParticipant();
        }
    }

    private boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    private void loadFromFilterFile(Context context) {
        LoadFile loadFile = new LoadFile();
        ArrayList<MoodEvent> moodEventArrayList = new ArrayList<>();
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        moodEventArrayList = loadFile.loadFilteredMoodEventList(context);
        MoodEventList moodEventList = new MoodEventList(moodEventArrayList);

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
                        //do something
                        Toast.makeText(MapActivity.this, item.getTitle() + "\n"
                                        + item.getPoint().getLatitudeE6() + " : " + item.getPoint().getLongitudeE6(),
                                Toast.LENGTH_LONG).show();
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

    private void loadAllParticipant(){
        ArrayList<String> userList = new ArrayList<>();
        MoodEventList moodEventList = new MoodEventList();
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();

        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask.execute("1");
        try{
            userList = getUserListTask.get();
            Log.i("Test in load", userList.get(0));
        } catch (Exception e) {
            Log.i("Error", "Failed to get the UserList out of the async object");
        }

        ElasticsearchUserController.GetUserTask getUserTask1;
        for (String userName : userList) {
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
                items.add(new OverlayItem(moodEvent.getBelongsTo(), moodEvent.getMoodState(), geoPoint));
            }
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(this,items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        Toast.makeText(MapActivity.this, item.getTitle() + "\n"
                                        + item.getPoint().getLatitudeE6() + " : " + item.getPoint().getLongitudeE6(),
                                Toast.LENGTH_LONG).show();
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
}
