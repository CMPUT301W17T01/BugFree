package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.util.GregorianCalendar;


/**
 * Created by Zhi Li on 2017/2/25.
 */

public class MoodEventTest extends ActivityInstrumentationTestCase2 {
    public MoodEventTest() {
        super(MainActivity.class);
        User usr = new User();
    }

    public void testMoodState() throws MoodStateNotAvailableException {
        MoodEvent mood = new MoodEvent("Happy", 1);
        if (mood.getMoodState().equals("Happy")) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    public void testSetColorIcon() throws MoodStateNotAvailableException {
        MoodEvent mood = new MoodEvent("Happy", 1);
        if (mood.getMoodColor() == -256) {
            assertTrue(true);
        } else {
            fail();
        }
        if (mood.getMoodIcon().equals("happy.png")) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    public void testSetBelongsTo() throws MoodStateNotAvailableException {
        MoodEvent mood = new MoodEvent("Happy", 1);
        if (mood.getBelongsTo() == 1) {
            assertTrue(true);
        } else {
            fail();
        }
    }

    public void testGetterSetter() throws IOException {
        UserList usrList = new UserList();
        User usr = new User();
        usr.setUsr("First");

        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 1);
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));

        //test the setters & getters
        try{
            assertTrue(mood.getUrlPic().equals("fake.png"));
            assertTrue(mood.getSocialSituation().equals("Alone"));
            assertTrue(mood.getTriggerText().equals("School"));
            GregorianCalendar date1 = new GregorianCalendar(2017,2,2,15,16,17);
            // Reference to http://javarevisited.blogspot.ca/2012/02/3-example-to-compare-two-dates-in-java.html
            //2017-2-26-19:25
            assertTrue((mood.getDateOfRecord()).compareTo(date1) ==0);
        }
        catch(Exception e){
            fail();
        }
    }

    public void testIllegalInit() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 1);
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));
//         Test illegal moodState
//        this is an initialization issue, therefore it should be done in CreateEditMoodActivity
//        It will fail at this time being
        try {
            MoodEvent mood1 = new MoodEvent("what", 1);
            fail();
        } catch (MoodStateNotAvailableException e) {
            assertTrue(true);
        }

    }

    public void testTrigger() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 1);
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));

        // Test trigger length
        try {
            mood.setTriggerText("a b c d");
            mood.setTriggerText("Thisisaveryveryveryveylongtrigger");
            fail();
        }catch (TriggerTooLongException e){
            assertTrue(true);
        }
    }

    public void testImage() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 1);
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));

        // Test Image size
        //fails
        try {
            mood.setUrlPic("A_big_image.png");
            fail();
        }catch (ImageTooBigException e){
            assertTrue(true);
        }

    }

    public void testSocialSituation() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 1);
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));

        // Test social situation
        try {
            mood.setSocialSituation("Alone!!!!");
            fail();
        }catch (InvalidSSException e){
            assertTrue(true);
        }
    }
}
