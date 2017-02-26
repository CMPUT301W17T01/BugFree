package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Zhi Li on 2017/2/25.
 */

public class MoodEventTest extends ActivityInstrumentationTestCase2 {
    public MoodEventTest(){ super(MoodEvent.class);}

    public void testMoodState(){
        MoodEvent mood = new MoodEvent("Happy",666);
        if(mood.getMoodState().equals("Happy")){
            assertTrue(true);
        }else{
            fail();
        }
    }
    public void testSetColorIcon(){
        MoodEvent mood = new MoodEvent("Happy",666);
        if(mood.getMoodColor()== -256){
            assertTrue(true);
        }else{
            fail();
        }
        if(mood.getMoodIcon().equals("happy.png")){
            assertTrue(true);
        }else{
            fail();
        }
    }
    public void testSetBelongsTo(){
        MoodEvent mood = new MoodEvent("Happy",666);
        if(mood.getBelongsTo()== 666){
            assertTrue(true);
        }else{
            fail();
        }
    }
}
