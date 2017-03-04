package com.example.mac.bugfree;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by yipengzhou on 2017/2/22.
 */

@RunWith(AndroidJUnit4.class)
public class FriendActivityUnitTest{

    @Rule
    public ActivityTestRule<FriendActivity> mActivityRule =
            new ActivityTestRule<>(FriendActivity.class);

    @Test
    public void testLoadList(){
        FriendActivity Friends = new FriendActivity();
        assertTrue(Friends.LoadList());
    }

    @Test
    public void testNotification(){
        FriendActivity Friends = new FriendActivity();
        assertTrue(Friends.manageNotification());
    }

}
