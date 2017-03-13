package com.example.mac.bugfree;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.junit.Test;
import java.util.regex.Pattern;

import com.robotium.solo.Solo;

/**
 * Created by heyuehuang on 2017-02-22.
 */
public class FilterActivityUnitTest extends ActivityInstrumentationTestCase2<FilterActivity>{

    private Solo solo;

    /**
     * Instantiates a new Filter activity unit test.
     */
    public FilterActivityUnitTest() {
        super(com.example.mac.bugfree.FilterActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

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

        View menu = solo.getView(R.id.activity_filter);
        solo.clickOnView(menu);
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
    }

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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
    }

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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
    }

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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
    }

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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
    }

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
        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
    }


}
