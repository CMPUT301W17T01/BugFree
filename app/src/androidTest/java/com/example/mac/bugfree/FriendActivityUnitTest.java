package com.example.mac.bugfree;


import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

/**
 * Created by yipengzhou on 2017/3/11.
 */

public class FriendActivityUnitTest extends ActivityInstrumentationTestCase2<FriendActivity> {
    private Solo solo;

    public FriendActivityUnitTest(){
        super(com.example.mac.bugfree.FriendActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testNotification(){
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);
        solo.clickOnText("Notification");
    }

    public void testFollower(){
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);
        solo.clickOnText("Follower");
    }

}
