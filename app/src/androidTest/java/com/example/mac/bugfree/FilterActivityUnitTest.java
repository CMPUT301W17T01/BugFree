package com.example.mac.bugfree;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.ListView;

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


    /**
     * Test filterList() in FilterActivity
     *
     * Should fail
     */
    public void testReason(){
        solo.assertCurrentActivity("Wrong Activity", FilterActivity.class);
        solo.enterText((EditText) solo.getView(R.id.edittext_reason_myself), "test...3");

        solo.clickOnMenuItem("Filter");
//        solo.assertCurrentActivity("Wrong Acticity", MainActivity.class);
//        final ListView oldTweetList = MoodEventList.get(0);
//        Tweet tweet = (Tweet) oldTweetList.getItemAtPosition(0);
//        assertEquals("Test Tweet!", tweet.getMessage());

    }

}
