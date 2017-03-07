package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by mac on 2017-03-05.
 */

@RunWith(AndroidJUnit4.class)
public class ElasticsearchUserControllerTest {

        @Rule
        public ActivityTestRule<MainActivity> mActivityRule =
                new ActivityTestRule<>(MainActivity.class);



        @Test
        public void elasticSearchAddUserTest(){
            ElasticsearchUserController.createIndex();
            //UserList userList = new UserList();
            User newUser = new User();
            MoodEventList moodEventList = new MoodEventList();
            try {
                MoodEvent moodEvent = new MoodEvent("Happy", 0);
                MoodEvent moodEvent1 = new MoodEvent("Anger", 0);
                moodEventList.addMoodEvent(moodEvent);
                moodEventList.addMoodEvent(moodEvent1);
                newUser.setMoodEventList(moodEventList);
            } catch (MoodStateNotAvailableException e) {
                Log.i("Error", "MoodEvent state is wrong" );
            }

            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(newUser);


            String query = Integer.toString(newUser.getUsrID());
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(query);
            try{
                User user = getUserTask.get();
                assertEquals(newUser, user);
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }

        }

        @Test
        public void elasticSearchUpdateUserTest() {
            ElasticsearchUserController.createIndex();
            User user_1 = new User();
            User user_get = new User();

            MoodEventList moodEventList = new MoodEventList();
            try {
                MoodEvent moodEvent = new MoodEvent("Happy", 0);
                MoodEvent moodEvent1 = new MoodEvent("Anger", 0);
                moodEventList.addMoodEvent(moodEvent);
                moodEventList.addMoodEvent(moodEvent1);
                user_1.setMoodEventList(moodEventList);
            } catch (MoodStateNotAvailableException e) {
                Log.i("Error", "MoodEvent state is wrong" );
            }

            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user_1);

            String query = Integer.toString(user_1.getUsrID());
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(query);
            try{
                user_get = getUserTask.get();
                assertEquals(user_1, user_get);
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }


            try {
                MoodEventList moodEventList1 = user_get.getMoodEventList();
                MoodEvent moodEvent2 = new MoodEvent("Sad", 0);
                moodEventList1.addMoodEvent(moodEvent2);
                user_get.setMoodEventList(moodEventList1);
            } catch (MoodStateNotAvailableException e) {
                Log.i("Error", "MoodEvent state is wrong" );
            }
            ElasticsearchUserController.AddUserTask addUserTask1 = new ElasticsearchUserController.AddUserTask();
            addUserTask1.execute(user_get);

            String query2 = Integer.toString(user_1.getUsrID());
            ElasticsearchUserController.GetUserTask getUserTask2 = new ElasticsearchUserController.GetUserTask();
            getUserTask2.execute(query2);

            try{
                User user_get_2 = getUserTask.get();
                assertEquals(user_get, user_get_2);
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        }

}

