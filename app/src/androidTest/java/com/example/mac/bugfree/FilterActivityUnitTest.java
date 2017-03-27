package com.example.mac.bugfree;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.widget.Spinner;

import com.example.mac.bugfree.activity.FilterActivity;
import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.module.MoodEvent;
import com.robotium.solo.Solo;

import static android.content.Context.MODE_PRIVATE;
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by heyuehuang on 2017-02-22.
 */
public class FilterActivityUnitTest extends ActivityInstrumentationTestCase2<FilterActivity>{

    private Solo solo;

    /**
     * Instantiates a new Filter activity unit test.
     */
    public FilterActivityUnitTest() {
        super(FilterActivity.class);
    }

    public void setUp() throws Exception{
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
        SharedPreferences filterSetting = getInstrumentation().getTargetContext().getSharedPreferences("filterSetting",0);
        SharedPreferences.Editor editor = filterSetting.edit();
        editor.clear();
        editor.commit();
    }
    protected void tearDown() throws Exception {
        // Clear everything in the SharedPreferences
        super.tearDown();
        SharedPreferences filterSetting = getInstrumentation().getTargetContext().getSharedPreferences("filterSetting",0);
        SharedPreferences.Editor editor = filterSetting.edit();
        editor.clear();
        editor.commit();
    }


    // Taken from http://bitbar.com/automated-ui-testing-android-applications-robotium/
    /**
     * Test a item in mood state spinner in myself tab can be selected and
     * jump back to main activity after clicking filter menu item.
     * <p>
     * Should pass.
     */
    public void testMyselfMoodState(){

        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.spinner_myself));
        solo.clickInList(4);

        final ArrayList<MoodEvent> moodEventList = getActivity().getMoodListAfterFilter();
        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    // Taken from http://stackoverflow.com/questions/9189011/how-to-test-checkboxes-in-custom-listviews-using-robotium-in-android
    /**
     * Test most recent week checkbox in myself tab can be clicked and
     * jump back to main activity after clicking filter menu item.
     * <p>
     * Should pass.
     */
    public void testMyselfMostRecentWeek(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        CheckBox mostRecentWeek = (CheckBox) solo.getView(R.id.checkbox_recent_myself);
        solo.clickOnView(mostRecentWeek);

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    /**
     * Test reason edittext in myself can be enter a reason and
     * jump back to main activity after clicking filter menu item.
     * <p>
     * Should pass.
     */
    public void testMyselfReason(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edittext_reason_myself), "Test Filter");

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    // Taken from http://stackoverflow.com/questions/9189011/how-to-test-checkboxes-in-custom-listviews-using-robotium-in-android
    /**
     * Test display all checkbox in myself can be clicked and
     * jump back to main activity after clicking filter menu item.
     */
    public void testMyselfDisplayAll(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        CheckBox mostRecentWeek = (CheckBox) solo.getView(R.id.checkbox_display_myself);
        solo.clickOnView(mostRecentWeek);

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    // Taken from http://bitbar.com/automated-ui-testing-android-applications-robotium/
    /**
     * Test a item in mood state spinner in following tab can be selected and
     * jump back to main activity after clicking filter menu item.
     * <p>
     * Should pass.
     */
    public void testFollowingMoodState(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.clickOnText("Following");

        solo.clickOnView(solo.getCurrentActivity().findViewById(R.id.spinner_following));
        solo.clickInList(4);

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    // Taken from http://stackoverflow.com/questions/9189011/how-to-test-checkboxes-in-custom-listviews-using-robotium-in-android
    /**
     * Test most recent week checkbox in following tab can be clicked and
     * jump back to main activity after clicking filter menu item.
     * <p>
     * Should pass.
     */
    public void testFollowingMostRecentWeek(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.clickOnText("Following");

        CheckBox mostRecentWeek = (CheckBox) solo.getView(R.id.checkbox_recent_following);
        solo.clickOnView(mostRecentWeek);

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    /**
     * Test reason edittext in following can be enter a reason and
     * jump back to main activity after clicking filter menu item.
     * <p>
     * Should pass.
     */
    public void testFollowingReason(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.clickOnText("Following");

        solo.enterText((EditText) solo.getView(R.id.edittext_reason_following), "Test Filter");

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }

    // Taken from http://stackoverflow.com/questions/9189011/how-to-test-checkboxes-in-custom-listviews-using-robotium-in-android
    /**
     * Test display all checkbox in following can be clicked and
     * jump back to main activity after clicking filter menu item.
     */
    public void testFollowingDisplayAll(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.clickOnText("Following");

        CheckBox mostRecentWeek = (CheckBox) solo.getView(R.id.checkbox_display_following);
        solo.clickOnView(mostRecentWeek);

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
    }


}
