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
            User newUser = new User();
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(newUser);

//            String query = "\n" +
//                    "       \"query\": {\n" +
//                    "           \"filtered\" : {\n" +
//                    "               \"query\" : {\n" +
//                    "                   \"query_string\" : {\n" +
//                    "                       \"query\" : \"test\"\n" +
//                    "                   }\n" +
//                    "               },\n" +
//                    "               \"filter\" : {\n" +
//                    "                   \"term\" : { \"usrID\" : \"0\" }\n" +
//                    "               }\n" +
//                    "           }\n" +
//                    "       }\n" +
//                    "}";
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
}

