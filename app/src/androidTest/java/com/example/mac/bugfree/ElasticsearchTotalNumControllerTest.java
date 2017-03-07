package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mac on 2017-03-06.
 */
@RunWith(AndroidJUnit4.class)
public class ElasticsearchTotalNumControllerTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);



    @Test
    public void AddNumZeroTest() {
        ElasticsearchTotalNumController.createIndex();
        String num = "";


        // add number 0 to total num in elasticsearch
        ElasticsearchTotalNumController.AddNumTask addNumTask = new ElasticsearchTotalNumController.AddNumTask();
        addNumTask.execute(num);

        ElasticsearchTotalNumController.GetNumTask getNumTask = new ElasticsearchTotalNumController.GetNumTask();
        getNumTask.execute("nothing");

        try {
            num = getNumTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

//        if (num == 1) {
//            assertTrue(true);
//        } else assertTrue(false);
    }
}
