package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by mac on 2017-03-05.
 */

@RunWith(AndroidJUnit4.class)
public class GsonUnitTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void saveJsonFileTest(){
        UserList userList = new UserList();
        User user = new User();
        Integer num = 1;
        assertEquals(userList.getUserListSize(), num);
    }

}
