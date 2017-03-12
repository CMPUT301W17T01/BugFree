package com.example.mac.bugfree;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.robotium.solo.Solo;


/**
 * Created by mengyangchen on 2017-02-23.
 */


public class CreateEditMoodActivityUnitTest  extends ActivityInstrumentationTestCase2<CreateEditMoodActivity> {
    private Solo solo;

    public CreateEditMoodActivityUnitTest(){
        super(com.example.mac.bugfree.CreateEditMoodActivity.class);
    }
    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }
    public void testcheckbox(){
        solo.assertCurrentActivity("Wrong Activity", CreateEditMoodActivity.class);


    }

}
