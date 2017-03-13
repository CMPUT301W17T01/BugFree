package com.example.mac.bugfree;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 *
 * Please first signin with username == "John"
 * Then Test
 *
 * @author Xinlei Chen
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

    public void testClickNameInCard() {
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recycler_view);
        View view = recyclerView.getChildAt(0);
        TextView textView = (TextView) view.findViewById(R.id.mood_event_username);

        String input = textView.getText().toString();
        solo.clickOnView(textView);
        solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);
    }

    public void testDelete() {
        solo.assertCurrentActivity("Wrong", MainActivity.class);
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recycler_view);
        int before = recyclerView.getAdapter().getItemCount();
        View view = recyclerView.getChildAt(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.event_handle);
        //solo.clickOnView(imageView);
        //solo.clickOnText("Delete");
        solo.clickOnImage(0);


        solo.clickOnText("Filter");
        solo.assertCurrentActivity("Wrong", FilterActivity.class);
//        ImageView imageView1 = (ImageView) view.findViewById(R.id.activity_filter);
//        solo.clickOnView(imageView);
        solo.clickOnMenuItem("activity_filter");

        RecyclerView recyclerView2 = (RecyclerView) solo.getView(R.id.recycler_view);
        int after = recyclerView2.getAdapter().getItemCount();

        assertEquals(before, after+1);
    }

    public void testEdit() {
        solo.assertCurrentActivity("Wrong", MainActivity.class);
        RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.recycler_view);
        View view = recyclerView.getChildAt(0);
        ImageView imageView = (ImageView) view.findViewById(R.id.event_handle);

        solo.clickOnView(imageView);
        solo.clickOnText("Edit");
        solo.assertCurrentActivity("Wrong Activity", EditActivity.class);
    }
}
