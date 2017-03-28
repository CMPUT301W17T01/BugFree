package com.example.mac.bugfree;

import android.Manifest;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.example.mac.bugfree.activity.FilterActivity;
import com.example.mac.bugfree.activity.MapActivity;
import com.robotium.solo.Solo;

import org.junit.Test;
import org.osmdroid.util.GeoPoint;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by heyuehuang on 2017-02-22.
 */

public class MapActivityUnitTest extends ActivityInstrumentationTestCase2<MapActivity> {
    private Solo solo;

    public MapActivityUnitTest() {
        super(MapActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testDistanceBetweenTwoPoint(){
        solo.assertCurrentActivity("Wrong Activity", MapActivity.class);
        GeoPoint gp = new GeoPoint();

    }



}
