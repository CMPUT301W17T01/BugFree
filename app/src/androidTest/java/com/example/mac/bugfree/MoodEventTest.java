package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.exception.MoodStateNotAvailableException;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.User;

import java.io.IOException;
import java.util.GregorianCalendar;


/**
 * @author  Zhi Li
 */

public class MoodEventTest extends ActivityInstrumentationTestCase2 {

    public MoodEventTest() {
        super(MainActivity.class);

    }

//Test construct.
    public void testMoodState() throws MoodStateNotAvailableException {
        User usr = new User("John");
        MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
        if (mood.getMoodState().equals("Happy")) {
            assertTrue(true);
        } else {
            fail();
        }
    }

//Test if corresponding color and icon is set properly.
    public void testSetColorIcon() throws MoodStateNotAvailableException {
        User usr = new User("John");
        MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
        if (mood.getMoodColor() == -256) {
            assertTrue(true);
        } else {
            fail();
        }
        if (mood.getMoodIcon().equals(R.drawable.happy)) {
            assertTrue(true);
        } else {
            fail();
        }
    }

//Test if the moodEvent is stored under corresponding user.
    public void testSetBelongsTo() throws MoodStateNotAvailableException {
        User usr = new User("John");
        MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
        if (mood.getBelongsTo() == "John") {
            assertTrue(true);
        } else {
            fail();
        }
    }

//Test if all other getter and setters are functional.
    public void testGetterSetter() throws IOException {
        User usr = new User("First");

        //Initialize a normal MoodEvent
        MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
        mood.setUrlPic("fake.png");
        mood.setSocialSituation("Alone");
        mood.setTriggerText("School");
        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));
        mood.setRealtime(new GregorianCalendar(2017,2,2,15,16,17));

        //test the setters & getters
        try{
            assertTrue(mood.getUrlPic().equals("fake.png"));
            assertTrue(mood.getSocialSituation().equals("Alone"));
            assertTrue(mood.getTriggerText().equals("School"));
            GregorianCalendar date1 = new GregorianCalendar(2017,2,2,15,16,17);
            // Reference to http://javarevisited.blogspot.ca/2012/02/3-example-to-compare-two-dates-in-java.html
            //2017-2-26-19:25
            assertTrue((mood.getDateOfRecord()).compareTo(date1) ==0);
            assertTrue((mood.getRealtime()).compareTo(date1) ==0);
        }
        catch(Exception e){
            fail();
        }
    }

    //Check if an oversized image can be stored
    // This function is not ready yet
    //will pass after implement
//    public void testImage() throws MoodStateNotAvailableException,EmptyInputException,TriggerTooLongException,
//            ImageTooBigException,InvalidSSException{
//        //Initialize a normal MoodEvent
//        User usr = new User("John");
//        MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
//        mood.setUrlPic("fake.png");
//        mood.setSocialSituation("Alone");
//        mood.setTriggerText("School");
//        mood.setDateOfRecord(new GregorianCalendar(2017,2,2,15,16,17));
//
//        // Test Image size
//        //fails
//        try {
//            mood.setUrlPic("A_big_image.png");
//            fail();
//        }catch (ImageTooBigException e){
//            assertTrue(true);
//        }
//
//    }

}
