package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.example.mac.bugfree.activity.ViewMoodActivity;
import com.robotium.solo.Solo;

import static org.junit.Assert.assertEquals;

/**
 * Created by mengyangchen on 2017-02-23.
 */

public class ViewMoodActivityUnitTest extends ActivityInstrumentationTestCase2<ViewMoodActivity> {
    private Solo solo;

    public ViewMoodActivityUnitTest(){
        super(ViewMoodActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testedit(){
        solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);
        View edit = solo.getView(R.id.action_edit);
        solo.clickOnView(edit);
    }

    public void testdelete(){
        solo.assertCurrentActivity("Wrong Activity", ViewMoodActivity.class);
        View edit = solo.getView(R.id.action_delete);
        solo.clickOnView(edit);
    }

}
