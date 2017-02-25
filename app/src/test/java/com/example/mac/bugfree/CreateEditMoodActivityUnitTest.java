package com.example.mac.bugfree;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by mengyangchen on 2017-02-23.
 */

public class CreateEditMoodActivityUnitTest {

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
