package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by yipengzhou on 2017/2/22.
 */

public class FriendActivityUnitTest extends ActivityInstrumentationTestCase2 {

    public FriendActivityUnitTest() {
        super(FriendActivity.class);
    }

    public void testLoadList(){
        FriendActivity Friends = new FriendActivity();
        User currentFriend = new User();
        try {
            Friends.LoadList(currentFriend);
            fail();
        }catch(IllegalArgumentException e){}
        assertFalse(Friends.hasFriend(currentFriend));
    }
    public void testNotification(){
        FriendActivity Friends = new FriendActivity();
        User currentFriend = new User();
        try {
            Friends.manageNotification(currentFriend);
        }catch(IllegalArgumentException e){}
        assertFalse(Friends.hasNotification(currentFriend));
    }
}
