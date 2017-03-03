package com.example.mac.bugfree;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


/**
 * Created by mengyangchen on 2017-02-23.
 */

public class CreateEditMoodActivityUnitTest{
    @Test
    public void test_load_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        assertThat(mood.load_mood_list(),is(true));
    }

    @Test
    public void test_save_mood_list(){
        CreateEditMoodActivity mood= new CreateEditMoodActivity();
        User new_mood = new User();
        assertThat(mood.save_mood_list(),is(true));
    }

    @Test
    public void test_add_location(){
        CreateEditMoodActivity location= new CreateEditMoodActivity();
        User new_mood = new User();
        assertThat(location.add_location(),is(true));
    }
}
