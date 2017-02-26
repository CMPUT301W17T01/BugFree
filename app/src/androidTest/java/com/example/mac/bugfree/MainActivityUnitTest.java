package com.example.mac.bugfree;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by mac on 2017-02-25.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityUnitTest{
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * all extra function is related to UI
     * nothing need to test so far
     * Maybe evolve in the future
     */

    @Test
    public void test(){

    }
}
