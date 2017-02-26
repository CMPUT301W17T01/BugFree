package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by mac on 2017-02-25.
 */

public class MoodEventListUnitTest extends ActivityInstrumentationTestCase2{
    public MoodEventListUnitTest() {
        super(MainActivity.class);
    }

    public void testAddMoodEvent() {
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Test MoodEvent_1");

        moodEventList.addMoodEvent(moodEvent);
        assertTrue(moodEventList.hasMoodEvent(moodEvent));

        try {
            moodEventList.addMoodEvent(moodEvent);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    public void testHasMoodEvent() {
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Test MoodEvent_2");

        assertFalse(moodEventList.hasMoodEvent(moodEvent));
        moodEventList.addMoodEvent(moodEvent);
        assertTrue(moodEventList.hasMoodEvent(moodEvent));
    }

    public void testDeleteMoodEvent() {
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Test MoodEvent_3");

        moodEventList.addMoodEvent(moodEvent);
        moodEventList.deleteMoodEvent(moodEvent);

        assertFalse(moodEventList.hasMoodEvent(moodEvent));
    }

    public void testGetMoodEvent() {
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent = new MoodEvent("Test MoodEvent_4");

        moodEventList.addMoodEvent(moodEvent);
        MoodEvent returnedMoodEvent = moodEventList.getMoodEvent(0);

        assertEquals(returnedMoodEvent.getMoodState(), moodEvent.getMoodState());
    }

    public void testGetCount() {
        MoodEventList moodEventList = new MoodEventList();
        assertEquals(moodEventList.getCount(), 0);
        MoodEvent moodEvent = new MoodEvent("Test MoodEvent_5");

        moodEventList.addMoodEvent(moodEvent);
        assertEquals(moodEventList.getCount(), 1);

        moodEventList.deleteMoodEvent(moodEvent);
        assertEquals(moodEventList.getCount(), 0);

    }

    public void testSortByDate() {
        MoodEventList moodEventList = new MoodEventList();
        MoodEvent moodEvent1 = new MoodEvent("Test MoodEvent_6");
        moodEvent1.setDateOfRecord(new GregorianCalendar(2017,2,25));
        MoodEvent moodEvent2 = new MoodEvent("Test MoodEvent_7");
        moodEvent2.setDateOfRecord(new GregorianCalendar(2017,1,25));
        MoodEvent moodEvent3 = new MoodEvent("Test MoodEvent_8");
        moodEvent3.setDateOfRecord(new GregorianCalendar(2017,3,25));

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
