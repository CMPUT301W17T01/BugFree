package com.example.mac.bugfree;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

/**
 * Created by mac on 2017-02-25.
 */

public class MainActivityUnitTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;
    private Context context;

    public MainActivityUnitTest() {
        super(com.example.mac.bugfree.MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
        context = getInstrumentation().getTargetContext();
    }

    public void testAddFollowNotExist(){
        //from http://stackoverflow.com/questions/20611103/robotium-testing-on-options-menu-item-click
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnMenuItem("Add Follow");
        solo.enterText(0, "Ray");
        solo.clickOnButton("Done");
        boolean actual = solo.searchText("The user does not exist");

        assertEquals(true, actual);
    }

    public void testAddFollowExist() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        int mode = context.MODE_PRIVATE;

        SharedPreferences pref = getInstrumentation().getTargetContext().getSharedPreferences("data", mode);
        String currentUserName = pref.getString("currentUser", "");

        if (currentUserName.equals("John")) {
            solo.clickOnMenuItem("Add Follow");
            solo.enterText(0, "1Sam");
            solo.clickOnButton("Done");
            boolean actual = solo.searchText("You already followed this user");
            assertEquals(true, actual);
        }
    }

    public void testAddFollow() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        int mode = context.MODE_PRIVATE;

        SharedPreferences pref = getInstrumentation().getTargetContext().getSharedPreferences("data", mode);
        String currentUserName = pref.getString("currentUser", "");

        if (currentUserName.equals("John")) {
            solo.clickOnMenuItem("Add Follow");
            solo.enterText(0,"s");
        }
    }
}
