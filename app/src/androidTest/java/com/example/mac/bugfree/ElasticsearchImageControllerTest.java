package com.example.mac.bugfree;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.controller.ElasticsearchImageController;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.module.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * Created by mac on 2017-03-25.
 */

@RunWith(AndroidJUnit4.class)
public class ElasticsearchImageControllerTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void elasticSearchAddGetImageTest() {
        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute("9Rose");
        User user = new User();

        try{
             user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        MoodEventList moodEventList = user.getMoodEventList();
        GregorianCalendar dateOfRecord = new GregorianCalendar(2016, 1, 27, 13, 12,30);
        String dateString = dateOfRecord.getTime().toString().replaceAll("\\s", "");
        Log.i("Test", dateString+user.getUsr());
        String uniqueID = dateString+user.getUsr();

        try {
            MoodEvent moodEvent = new MoodEvent("Happy", user.getUsr());
            moodEvent.setRealtime(dateOfRecord);
            moodEvent.setDateOfRecord(dateOfRecord);
            moodEvent.setPicId(uniqueID);


            ImageForElasticSearch image = new ImageForElasticSearch("DSIjsdjgfrsiIHNUI798JNJKBIYGIUJNKLNIOU98789JKNKJBKJBNJNN");
            image.setUniqueId(uniqueID);
            ElasticsearchImageController.AddImageTask addImageTask = new ElasticsearchImageController.AddImageTask();
            addImageTask.execute(image);

            moodEventList.addMoodEvent(moodEvent);

            user.setMoodEventList(moodEventList);
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user);

        } catch (Exception e) {
            e.printStackTrace();
        }

        ElasticsearchImageController.GetImageTask getImageTask = new ElasticsearchImageController.GetImageTask();
        getImageTask.execute(uniqueID);

        ImageForElasticSearch imageForElasticSearch = new ImageForElasticSearch();

        try {
            imageForElasticSearch = getImageTask.get();
            assertEquals(imageForElasticSearch.getImageBase64(), "DSIjsdjgfrsiIHNUI798JNJKBIYGIUJNKLNIOU98789JKNKJBKJBNJNN");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
