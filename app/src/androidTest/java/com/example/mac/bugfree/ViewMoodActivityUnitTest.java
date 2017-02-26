package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * Created by mengyangchen on 2017-02-23.
 */

@RunWith(AndroidJUnit4.class)
public class ViewMoodActivityUnitTest{

    @Rule
    public ActivityTestRule<ViewMoodActivity> mActivityRule =
            new ActivityTestRule<>(ViewMoodActivity.class);

    @Test
    public void test_load_mood_list(){
        MoodEvent moodEvent = new MoodEvent("Test");
        assertEquals(moodEvent.getMoodState(), "Test");
    }
}
