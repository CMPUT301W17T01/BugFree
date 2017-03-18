package com.example.mac.bugfree;

import com.example.mac.bugfree.activity.MapActivity;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
/**
 * Created by heyuehuang on 2017-02-22.
 */

public class MapActivityUnitTest  {

    /**
     * Test showPin() in MapActivity
     *
     * Should fail
     */
    @Test
    public void testShowPin(){
        MapActivity pin = new MapActivity();
        assertFalse(pin.showPin());
    }

    /**
     * Test showDetail() in MapActivity
     *
     * Should fail
     */
    @Test
    public void testShowDetail(){
        MapActivity detail = new MapActivity();
        assertFalse(detail.showDetail());
    }

 }
