package com.example.mac.bugfree;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.example.mac.bugfree.activity.CreateEditMoodActivity;
import com.robotium.solo.Solo;


/**
 * Created by mengyangchen on 2017-02-23.
 */


public class CreateEditMoodActivityUnitTest  extends ActivityInstrumentationTestCase2<CreateEditMoodActivity> {
    private Solo solo;

    public CreateEditMoodActivityUnitTest(){
        super(CreateEditMoodActivity.class);
    }
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }
    public void testcheckbox(){
        solo.assertCurrentActivity("Wrong Activity", CreateEditMoodActivity.class);
        View cb = solo.getView(R.id.current_time);
        solo.clickOnView(cb);
    }

    public void testspinner1(){
        solo.assertCurrentActivity("Wrong Activity", CreateEditMoodActivity.class);
        View spnr = solo.getView(R.id.mood_state_spinner);
        solo.clickOnView(spnr);
    }

    public void testspinner2(){
        solo.assertCurrentActivity("Wrong Activity", CreateEditMoodActivity.class);
        View spnr = solo.getView(R.id.social_situation);
        solo.clickOnView(spnr);
    }

    public void testreason(){
        solo.assertCurrentActivity("Wrong Activity", CreateEditMoodActivity.class);
        solo.enterText((EditText) solo.getView(R.id.create_edit_reason), "cool!");
        assertTrue(solo.searchText("cool!"));
    }

    public void testchecktick(){
        View ct = solo.getView(R.id.action_add_tick);
        solo.pressSpinnerItem(0,1);
        solo.pressSpinnerItem(1,1);
        solo.enterText((EditText) solo.getView(R.id.create_edit_reason), "cool!");
        solo.clickOnView(ct);
    }

}
