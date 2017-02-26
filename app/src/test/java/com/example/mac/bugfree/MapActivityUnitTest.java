package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by heyuehuang on 2017-02-22.
 */

public class MapActivityUnitTest extends ActivityInstrumentationTestCase2 {

    public MapActivityUnitTest() {
        super(MapActivity.class);
    }

    public void testShowPin(){
        MapActivity pin = new MapActivity();
        assertFalse(pin.showPin());
    }

    public void testShowDetail(){
        MapActivity detail = new MapActivity();
        assertFalse(detail.showDetail());
    }

 }
