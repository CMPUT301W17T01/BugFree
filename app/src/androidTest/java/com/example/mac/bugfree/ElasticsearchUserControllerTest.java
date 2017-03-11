package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
            ElasticsearchUserController.createIndex();//clear our team index, everything will gone
            User newUser = new User("John");//create user named john
            MoodEventList moodEventList = new MoodEventList();
            try {
                MoodEvent moodEvent = new MoodEvent("Happy", newUser.getUsr());
                MoodEvent moodEvent1 = new MoodEvent("Anger", newUser.getUsr());
                moodEventList.addMoodEvent(moodEvent);
                moodEventList.addMoodEvent(moodEvent1);
                newUser.setMoodEventList(moodEventList);
            } catch (MoodStateNotAvailableException e) {
                Log.i("Error", "MoodEvent state is wrong" );
            }

            ArrayList<String> followerList = new ArrayList<>();
            followerList.add("apple");
            followerList.add("banana");
            followerList.add("orange");
            newUser.setFollowerIDs(followerList);

            ArrayList<String> followList = new ArrayList<>();
            followList.add("apple");
            followList.add("banana");
            followList.add("orange");
            newUser.setFolloweeIDs(followList);



            //these two lines uploads the user
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(newUser);

            //get user name
            String query = newUser.getUsr();
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
        public void elasticSearchGetUserTest() {
            ElasticsearchUserController.createIndex();

            User user_1 = new User("John");
            User user_2 = new User("Sam");


            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user_1);

            ElasticsearchUserController.AddUserTask addUserTask2 = new ElasticsearchUserController.AddUserTask();
            addUserTask2.execute(user_2);

            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(user_1.getUsr());


            try{
                User user = getUserTask.get();
                assertEquals(user_1, user);
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        }

        @Test
        public void elasticSearchUpdateUserTest() {
            ElasticsearchUserController.createIndex();
            User user_1 = new User("John");
            User user_get = new User("Sam");

            MoodEventList moodEventList = new MoodEventList();
            try {
                MoodEvent moodEvent = new MoodEvent("Happy", user_1.getUsr());
                MoodEvent moodEvent1 = new MoodEvent("Anger", user_1.getUsr());
                moodEventList.addMoodEvent(moodEvent);
                moodEventList.addMoodEvent(moodEvent1);
                user_1.setMoodEventList(moodEventList);
            } catch (MoodStateNotAvailableException e) {
                Log.i("Error", "MoodEvent state is wrong" );
            }

            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user_1);

            //update
            String query = user_1.getUsr();
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
                MoodEvent moodEvent2 = new MoodEvent("Sad", user_get.getUsr());
                moodEventList1.addMoodEvent(moodEvent2);
                user_get.setMoodEventList(moodEventList1);
            } catch (MoodStateNotAvailableException e) {
                Log.i("Error", "MoodEvent state is wrong" );
            }

//            ElasticsearchUserController.AddUserTask addUserTask1 = new ElasticsearchUserController.AddUserTask();
//            addUserTask1.execute(user_get);
            //the following two lines updates the online version
            ElasticsearchUserController.UpdateUserTask updateUserTask = new ElasticsearchUserController.UpdateUserTask();
            updateUserTask.execute(user_get);

            String query2 = user_get.getUsr();
            ElasticsearchUserController.GetUserTask getUserTask2 = new ElasticsearchUserController.GetUserTask();
            getUserTask2.execute(query2);

            try{
                User user_get_2 = getUserTask2.get();
                assertEquals(user_get, user_get_2);
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        }

        @Test
        public void elsaticseatchIsExistTest() {
            ElasticsearchUserController.createIndex();

            User user_1 = new User("John");
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user_1);
            //check if the user requested exists
            ElasticsearchUserController.IsExist isExist = new ElasticsearchUserController.IsExist();
            isExist.execute(user_1.getUsr());

            try {
                assertTrue(isExist.get());
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }

            ElasticsearchUserController.IsExist isExist2 = new ElasticsearchUserController.IsExist();
            isExist2.execute("Sam");
            try {
                assertFalse(isExist2.get());
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }

        }

    @Test
    public void elasticsearchRenewIndex(){
        ElasticsearchUserController.createIndex();
    }

    @Test
    public void elasticsearchAddData() {
        ElasticsearchUserController.createIndex();//clear our team index, everything will gone

        User newUser_1 = new User("John");//create user named john
        MoodEventList moodEventList = new MoodEventList();
        try {
            MoodEvent moodEvent = new MoodEvent("Happy", newUser_1.getUsr());
            try {
                moodEvent.setTriggerText("Cake");
            } catch (Exception e) {
                e.printStackTrace();
            }
            MoodEvent moodEvent1 = new MoodEvent("Anger", newUser_1.getUsr());
            moodEventList.addMoodEvent(moodEvent);
            moodEventList.addMoodEvent(moodEvent1);
            newUser_1.setMoodEventList(moodEventList);
        } catch (MoodStateNotAvailableException e) {
            Log.i("Error", "MoodEvent state is wrong" );
        }

        ArrayList<String> followerList = new ArrayList<>();
        followerList.add("apple");
        followerList.add("banana");
        followerList.add("orange");
        newUser_1.setFollowerIDs(followerList);

        ArrayList<String> followList = new ArrayList<>();
        followList.add("apple");
        followList.add("banana");
        followList.add("orange");
        newUser_1.setFolloweeIDs(followList);


        //these two lines uploads the user
        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(newUser_1);

        User user_2 = new User("apple");
        MoodEventList moodEventList_1 = new MoodEventList();
        try {
            MoodEvent moodEvent = new MoodEvent("Happy", user_2.getUsr());
            MoodEvent moodEvent1 = new MoodEvent("Anger", user_2.getUsr());
            moodEventList_1.addMoodEvent(moodEvent);
            moodEventList_1.addMoodEvent(moodEvent1);
            user_2.setMoodEventList(moodEventList_1);
        } catch (MoodStateNotAvailableException e) {
            Log.i("Error", "MoodEvent state is wrong" );
        }

        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user_2);

        User banana = new User("banana");
        User orange = new User("orange");
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(banana);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(orange);
    }

    @Test
    public void elasticsearchDisplayDate() {
        ElasticsearchUserController.createIndex();
        User user = new User("John");
        GregorianCalendar dateOfRecord = new GregorianCalendar(2016, 1, 27, 13, 12);
        GregorianCalendar dateOfRecord1 = new GregorianCalendar(2017, 1, 28, 13, 12);
        MoodEventList moodEventList = new MoodEventList();

        try {
            MoodEvent moodEvent = new MoodEvent("Happy", user.getUsr());
            moodEvent.setDateOfRecord(dateOfRecord);
            MoodEvent moodEvent1 = new MoodEvent("Sad", user.getUsr());
            moodEvent1.setDateOfRecord(dateOfRecord1);
            moodEventList.addMoodEvent(moodEvent);
            moodEventList.addMoodEvent(moodEvent1);
            user.setMoodEventList(moodEventList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);

    }



}

