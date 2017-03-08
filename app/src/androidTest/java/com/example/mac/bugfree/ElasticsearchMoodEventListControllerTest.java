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
 * Created by mac on 2017-03-07.
 */

@RunWith(AndroidJUnit4.class)
public class ElasticsearchMoodEventListControllerTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void elasticSearchAddMoodEventListTest() {
        //ElasticsearchUserController.createIndex();
        User user = new User("John");
        MoodEventList moodEventList = new MoodEventList();
        try {
            MoodEvent moodEvent1 = new MoodEvent("Happy", user.getUsr());
            moodEventList.addMoodEvent(moodEvent1);
            MoodEvent moodEvent2 = new MoodEvent("Sad", user.getUsr());
            moodEventList.addMoodEvent(moodEvent2);
            MoodEvent moodEvent3 = new MoodEvent("Anger", user.getUsr());
            moodEventList.addMoodEvent(moodEvent3);
            MoodEvent moodEvent4 = new MoodEvent("Shame", user.getUsr());
            moodEventList.addMoodEvent(moodEvent4);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ElasticsearchMoodEventListController.AddUserTask addUserTask = new ElasticsearchMoodEventListController.AddUserTask();
        addUserTask.execute(moodEventList);

        String query = "filter";
        ElasticsearchMoodEventListController.GetUserTask getUserTask = new ElasticsearchMoodEventListController.GetUserTask();
        getUserTask.execute(query);

        MoodEventList moodEventList1 = new MoodEventList();
        try{
            moodEventList1 = getUserTask.get();
            if (moodEventList.getCount() == moodEventList1.getCount()) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        for (int i=0; i < moodEventList1.getCount(); i++){
            Log.d("test in get List", moodEventList1.getMoodEvent(i).getMoodState());
        }
    }
}
