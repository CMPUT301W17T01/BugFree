package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.example.mac.bugfree.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        // important! set your agent to prevent getting banned from the osm servers
        org.osmdroid.config.Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        setContentView(R.layout.activity_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(9);
        GeoPoint startPoint = new GeoPoint(48.8583, 2.2944);
        mapController.setCenter(startPoint);


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
    }

    public void onResume() {
        super.onResume();
        org.osmdroid.config.Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    public boolean showPin(){
        return true;
    }

    public boolean showDetail(){
        return true;
    }



}
