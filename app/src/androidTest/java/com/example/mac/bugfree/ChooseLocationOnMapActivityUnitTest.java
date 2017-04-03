package com.example.mac.bugfree;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.example.mac.bugfree.activity.ChooseLocationOnMapActivity;
import com.example.mac.bugfree.activity.MapActivity;
import com.example.mac.bugfree.util.CurrentLocation;
import com.robotium.solo.Solo;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by heyuehuang on 2017-04-02.
 */

public class ChooseLocationOnMapActivityUnitTest extends ActivityInstrumentationTestCase2<ChooseLocationOnMapActivity> {
    private Solo solo;

    public ChooseLocationOnMapActivityUnitTest() {
        super(ChooseLocationOnMapActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testChooseOnTheMap(){

        solo.assertCurrentActivity("Wrong Activity", ChooseLocationOnMapActivity.class);
        View vMap = solo.getView(R.id.map_choose);
        GeoPoint loc;
        loc = getActivity().getChosenLocation();
        assertTrue(loc == null);
        solo.clickLongOnView(vMap);
        loc = getActivity().getChosenLocation();
        assertFalse(loc == null);
    }
}
