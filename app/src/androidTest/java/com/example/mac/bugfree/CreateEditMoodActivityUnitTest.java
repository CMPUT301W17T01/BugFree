package com.example.mac.bugfree;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mengyangchen on 2017-02-23.
 */

public class CreateEditMoodActivityUnitTest extends ActivityInstrumentationTestCase2{
    public CreateEditMoodActivityUnitTest(){
        super(CreateEditMoodActivity.class);
    }

    public void test_load_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        User new_mood = new User();
        assertFalse(mood.load_mood_list());
    }

    public void test_save_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        User new_mood = new User();
        assertFalse(mood.save_mood_list());
    }

    public void test_add_location(){
        CreateEditMoodActivity location= new CreateEditMoodActivity();
        User new_mood = new User();
        assertFalse(location.add_location());
    }
}
