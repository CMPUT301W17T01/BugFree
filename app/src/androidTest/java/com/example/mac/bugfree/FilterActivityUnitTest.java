package com.example.mac.bugfree;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import org.junit.Test;
import java.util.regex.Pattern;

import com.robotium.solo.Solo;

/**
 * Created by heyuehuang on 2017-02-22.
 */

public class FilterActivityUnitTest extends ActivityInstrumentationTestCase2<FilterActivity>{

    private Solo solo;

    public FilterActivityUnitTest() {
        super(com.example.mac.bugfree.FilterActivity.class);
    }

    public void setUp() throws Exception{
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();

    }
    /**
     * Test filterList() in FilterActivity
     *
     * Should fail
     */
    @Test
    public void testCheckWhichIsChosen(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edittext_reason_myself), "test...3");
    }

}
