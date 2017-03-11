package com.example.mac.bugfree;

import android.app.Notification;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TabHost;

import org.junit.Rule;
import org.junit.Test;
import com.robotium.solo.Solo;

import static com.example.mac.bugfree.R.id.tabHost;

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
    }

}
