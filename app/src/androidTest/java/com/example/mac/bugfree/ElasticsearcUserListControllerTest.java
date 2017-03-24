package com.example.mac.bugfree;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.activity.MapActivity;
import com.example.mac.bugfree.activity.SignUpActivity;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;
import com.example.mac.bugfree.module.User;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by mac on 2017-03-21.
 */

@RunWith(AndroidJUnit4.class)
public class ElasticsearcUserListControllerTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void elasticSearchAddUserListTest() {
        //ElasticsearchUserController.createIndex();
//        List<String> userNameList = new ArrayList<>();
//        userNameList.add("Sam");
//        userNameList.add("Tom");
//        userNameList.add("Kevin");
//        userNameList.add("Ray");
//        SystemClock.sleep(3000);
//        ElasticsearchUserListController.AddUserListTask addUserListTask = new ElasticsearchUserListController.AddUserListTask();
//        addUserListTask.execute("Sam");

//        String query = "{\n" +
//                        "       \"query\" : {\n" +
//                        "       }\n" +
//                        "}";
//
//        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
//        getUserListTask.execute(query);
//        try {
//            userList = getUserListTask.get();
//            Log.d("NameList", userList.toString());
//
//        } catch (Exception e) {
//            Log.i("Error", "Failed to get the username out of the async object");
//        }

    }
}
