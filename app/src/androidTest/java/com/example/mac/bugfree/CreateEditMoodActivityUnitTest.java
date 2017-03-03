package com.example.mac.bugfree;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mengyangchen on 2017-02-23.
 */


@RunWith(AndroidJUnit4.class)
public class CreateEditMoodActivityUnitTest{

    @Rule
    public ActivityTestRule<CreateEditMoodActivity> mActivityRule =
            new ActivityTestRule<>(CreateEditMoodActivity.class);

    @Test
    public void test(){

    }

    @Test
    public void test_load_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        User new_mood = new User();
        //assertThat(location.add_location(),is(true));
        //assertFalse(mood.load_mood_list());
    }

    @Test
    public void test_save_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        User new_mood = new User();

    }

    @Test
    public void test_add_location(){
        CreateEditMoodActivity location= new CreateEditMoodActivity();
        User new_mood = new User();
        assertThat(location.add_location(),is(true));
        //assertFalse(location.add_location());
    }
}
