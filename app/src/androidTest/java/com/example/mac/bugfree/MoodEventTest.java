package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.io.IOException;
import java.util.GregorianCalendar;


/**
 * @author  Zhi Li
 */

public class MoodEventTest extends ActivityInstrumentationTestCase2 {
    public MoodEventTest() {
        super(MainActivity.class);

        User usr = new User();
    }
//Test construct.
    public void testMoodState() throws MoodStateNotAvailableException {
        MoodEvent mood = new MoodEvent("Happy", 0);
        if (mood.getMoodState().equals("Happy")) {
            assertTrue(true);
        } else {
            fail();
        }
    }
//Test if corresponding color and icon is set properly.
    public void testSetColorIcon() throws MoodStateNotAvailableException {
        MoodEvent mood = new MoodEvent("Happy", 0);
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
//Test if the moodEvent is stored under corresponding user.
    public void testSetBelongsTo() throws MoodStateNotAvailableException {
        MoodEvent mood = new MoodEvent("Happy", 0);
        if (mood.getBelongsTo() == 0) {
            assertTrue(true);
        } else {
            fail();
        }
    }
//Test if all other getter and setters are functional.
    public void testGetterSetter() throws IOException {
        UserList usrList = new UserList();
        User usr = new User();
        usr.setUsr("First");

        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 0);
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
//Test using a bad initialization parameters.
    public void testIllegalInit() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 0);
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));
//         Test illegal moodState
//        this is an initialization issue, therefore it should be done in CreateEditMoodActivity
//        It will fail at this time being
        try {
            MoodEvent mood1 = new MoodEvent("what", 0);
            fail();
        }
        catch (MoodStateNotAvailableException e) {
            assertTrue(true);
        }

    }
//Test if a moodEvent saves a improper trigger
    public void testTrigger() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 0);
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
//Check if an oversized image can be stored
    //will pass after implement
    public void testImage() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 0);
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
//Test if Social Situation setter allows bad parameter
    public void testSocialSituation() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
            ImageTooBigException,InvalidSSException{
        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", 0);
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
