package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by mac on 2017-02-25.
 * @author Xinlei Chen
 */

public class MoodEventListUnitTest extends ActivityInstrumentationTestCase2{
    public MoodEventListUnitTest() {
        super(MainActivity.class);
        User usr = new User();
    }
//Test if a mood event can be added to a MoodEventList
    public void testAddMoodEvent() throws MoodStateNotAvailableException{

        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Happy",0);

        moodEventList.addMoodEvent(moodEvent);
        assertTrue(moodEventList.hasMoodEvent(moodEvent));

        try {
            moodEventList.addMoodEvent(moodEvent);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }
//Test if a mood event is in a list
    public void testHasMoodEvent() throws MoodStateNotAvailableException{
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Anger",0);

        assertFalse(moodEventList.hasMoodEvent(moodEvent));
        moodEventList.addMoodEvent(moodEvent);
        assertTrue(moodEventList.hasMoodEvent(moodEvent));
    }
//Test if a mood event can be deleted from a MoodEventList(MEL)
    public void testDeleteMoodEvent() throws MoodStateNotAvailableException{
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Confusion",0);

        moodEventList.addMoodEvent(moodEvent);
        moodEventList.deleteMoodEvent(moodEvent);

        assertFalse(moodEventList.hasMoodEvent(moodEvent));
    }
//Tests if a MoodEvent can be found from a MEL
    public void testGetMoodEvent() throws MoodStateNotAvailableException{
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Disgust",0);
        moodEventList.addMoodEvent(moodEvent);
        MoodEvent returnedMoodEvent = moodEventList.getMoodEvent(0);

        assertEquals(returnedMoodEvent.getMoodState(), moodEvent.getMoodState());
    }
//Test if MEL counter work properly
    public void testGetCount() throws MoodStateNotAvailableException{
        MoodEventList moodEventList = new MoodEventList();

        assertEquals(moodEventList.getCount(), 0);
        MoodEvent moodEvent = new MoodEvent("Fear",0);
        moodEventList.addMoodEvent(moodEvent);
        assertEquals(moodEventList.getCount(), 1);

        moodEventList.deleteMoodEvent(moodEvent);
        assertEquals(moodEventList.getCount(), 0);

    }
//Test if MoodEvents in a MEL can be sort according to date
    public void testSortByDate() throws MoodStateNotAvailableException{
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent1 = new MoodEvent("Surprise",0);
        moodEvent1.setDateOfRecord(new GregorianCalendar(2017,2,25,15,16,17));
        MoodEvent moodEvent2 = new MoodEvent("Sad",0);
        moodEvent2.setDateOfRecord(new GregorianCalendar(2017,1,25,15,16,17));
        MoodEvent moodEvent3 = new MoodEvent("Shame",0);
        moodEvent3.setDateOfRecord(new GregorianCalendar(2017,3,25,15,16,17));

        moodEventList.addMoodEvent(moodEvent1);
        moodEventList.addMoodEvent(moodEvent2);
        moodEventList.addMoodEvent(moodEvent3);

        moodEventList.sortByDate();
        if (moodEventList.getMoodEvent(0).getDateOfRecord().compareTo(moodEventList.getMoodEvent(1).getDateOfRecord()) <0) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }


    }
}
