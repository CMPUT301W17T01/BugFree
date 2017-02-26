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
        User currentFriend = new User();
        try {
            Friends.LoadList(currentFriend);
            //fail();
        }catch(IllegalArgumentException e){}
        assertFalse(Friends.hasFriend(currentFriend));
    }

    @Test
    public void testNotification(){
        FriendActivity Friends = new FriendActivity();
        User currentFriend = new User();
        try {
            Friends.manageNotification(currentFriend);
        }catch(IllegalArgumentException e){}
        assertFalse(Friends.hasNotification(currentFriend));
    }

}
