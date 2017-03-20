package com.example.mac.bugfree;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.bugfree.activity.EditActivity;
import com.example.mac.bugfree.activity.FilterActivity;
import com.example.mac.bugfree.activity.FriendActivity;
import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.activity.SignInActivity;
import com.example.mac.bugfree.activity.ViewMoodActivity;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.User;
import com.robotium.solo.Solo;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Please run DataForTest first
 * Please first signin with username == "John"
 * Then Test
 *
 * ALERT: It wiil be all failed when it is in Signin or Signup Activity
 * @author Xinlei Chen
 */

public class MainActivityUnitTest extends ActivityInstrumentationTestCase2<MainActivity>{

    private Solo solo;
    private Context context;

    public MainActivityUnitTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
        context = getInstrumentation().getTargetContext();
    }

    // test add follow who does not exist
    public void testAddFollowNotExist(){
        //from http://stackoverflow.com/questions/20611103/robotium-testing-on-options-menu-item-click
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnMenuItem("Add Follow");
        solo.enterText(0, "Ray");
        solo.clickOnButton("Done");
        boolean actual = solo.searchText("The user does not exist");

        assertEquals(true, actual);
    }

    // test add follow who user already follow
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

    // test add follow in normal situation
    public void testAddFollow() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        int mode = context.MODE_PRIVATE;

        SharedPreferences pref = getInstrumentation().getTargetContext().getSharedPreferences("data", mode);
        String currentUserName = pref.getString("currentUser", "");

        if (currentUserName.equals("John")) {
            solo.clickOnMenuItem("Add Follow");
            solo.enterText(0,"5Sherlock");
            solo.clickOnButton("Done");

            SystemClock.sleep(3000);

            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute("5Sherlock");

            User user = new User();
            try{
                user= getUserTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }

            ArrayList<String> pendingList = user.getPendingPermission();
            Log.d("Error", pendingList.toString());
            if (pendingList.contains("John")) {
                assertTrue(true);
            } else {
                assertTrue(false);
            }
        }
    }

    // test the drawer name
    public void testDrawerName(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);

        int mode = context.MODE_PRIVATE;

        SharedPreferences pref = getInstrumentation().getTargetContext().getSharedPreferences("data", mode);
        String currentUserName = pref.getString("currentUser", "");

        if (currentUserName.equals(currentUserName)){
            solo.clickOnImageButton(0);

            assertTrue(solo.searchText(currentUserName));
        }
    }

    //test Click name on Card
    public void testClickNameInCard() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recycler_view);
        if (recyclerView.getAdapter().getItemCount() != 0) {
            View view = recyclerView.getChildAt(0);
            TextView textView = (TextView) view.findViewById(R.id.mood_event_username);
            String input = textView.getText().toString();
            solo.clickOnView(textView);
            solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);
        } else {
            assertTrue(true);
        }
    }

    //test pass to FriendsActivity
    public void testIntentToFriendsActivity(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImage(0);
        solo.clickOnText("Friends");
        solo.assertCurrentActivity("Wrong Activity", FriendActivity.class);
    }

    //test pass to FilterActivity
    public void testIntentToFilterActivity() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImage(0);
        solo.clickOnText("Filter");
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
    }

    // test to click Edit on popup up menu which in Card
    public void testEdit() {
        solo.assertCurrentActivity("Wrong", MainActivity.class);
        solo.clickOnImage(0);
        solo.clickOnText("Filter");

        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        CheckBox mostRecentWeek = (CheckBox) solo.getView(R.id.checkbox_display_myself);
        solo.clickOnView(mostRecentWeek);
        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);

        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recycler_view);
        if (recyclerView.getAdapter().getItemCount() != 0) {
            View view = recyclerView.getChildAt(0);
            ImageView imageView = (ImageView) view.findViewById(R.id.event_handle);
            solo.clickOnView(imageView);
            solo.clickOnText("Edit");
            solo.assertCurrentActivity("Wrong Activity", EditActivity.class);
        } else {
            assertTrue(true);
        }
    }

    // test Sign out in drawer
    public void testIntentSignout() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnImage(0);
        solo.clickOnText("Sign Out");
        solo.assertCurrentActivity("Wrong Activity", SignInActivity.class);
        solo.enterText(0, "John");
        solo.clickOnButton(0);
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

}
