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
import com.example.mac.bugfree.module.UserNameList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        UserNameList userNameList = new UserNameList();

        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask.execute("name");
        try{
            userNameList = getUserListTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        if (userNameList != null) {
            userNameList.addUserName("Ray");
            ElasticsearchUserListController.AddUserListTask addUserListTask = new ElasticsearchUserListController.AddUserListTask();
            addUserListTask.execute(userNameList);
        } else {
            ArrayList<String> usrNameList = new ArrayList<String>();
            usrNameList.add("Ray");
            userNameList = new UserNameList(usrNameList);
            ElasticsearchUserListController.AddUserListTask addUserListTask = new ElasticsearchUserListController.AddUserListTask();
            addUserListTask.execute(userNameList);
        }

        ElasticsearchUserListController.AddUserListTask addUserListTask1 = new ElasticsearchUserListController.AddUserListTask();
        addUserListTask1.execute(userNameList);

        UserNameList userNameListGet = new UserNameList();
        ElasticsearchUserListController.GetUserListTask getUserListTask1 = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask1.execute("name");
        try{
            userNameListGet = getUserListTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        assertTrue(userNameListGet.hadUserName("Ray"));

        userNameListGet.deleteUserName("Ray");
        ElasticsearchUserListController.AddUserListTask addUserListTask2 = new ElasticsearchUserListController.AddUserListTask();
        addUserListTask2.execute(userNameListGet);
    }
}
