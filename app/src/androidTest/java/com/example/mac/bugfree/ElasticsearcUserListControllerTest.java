package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

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
        ArrayList<String> userList = new ArrayList<String>();
        userList.add("Sam");
        userList.add("Tom");
        userList.add("Kevin");
        userList.add("Ray");

        ElasticsearchUserListController.AddUserListTask addUserListTask = new ElasticsearchUserListController.AddUserListTask();
        addUserListTask.execute(userList);
    }
}
