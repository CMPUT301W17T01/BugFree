package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import static junit.framework.Assert.assertFalse;

/**
 * Created by mengyangchen on 2017-02-23.
 */

public class ViewMoodActivityUnitTest extends ActivityInstrumentationTestCase2 {
    public ViewMoodActivityUnitTest(){
        super(ViewMoodActivity.class);
    }
    public void test_load_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        User new_mood = new User();
        assertFalse(mood.load_mood_list());
    }
}
