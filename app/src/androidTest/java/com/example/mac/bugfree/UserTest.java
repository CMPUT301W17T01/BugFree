package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;


/**
 * @author Zhi Li
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){ super(MainActivity.class);}

    // Test Getter and Setters
    public void testGetterSetter(){
        User usr = new User("John");

        ArrayList<String> AL1 = new ArrayList<>();
        ArrayList<String> AL2 = new ArrayList<>();
        ArrayList<String> AL3 = new ArrayList<>();

        AL1.add("Sam");
        AL1.add("Lily");
        AL1.add("Ray");
        AL2.addAll(AL1);

        usr.setFolloweeIDs(AL1);
        usr.setFollowerIDs(AL2);

        usr.setPendingPermissions(AL3);

        MoodEventList MEL = new MoodEventList();
        try{MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
            MEL.addMoodEvent(mood);
            MoodEvent mood1 = new MoodEvent("Sad",usr.getUsr());
            MEL.addMoodEvent(mood1);
        }
        catch (MoodStateNotAvailableException e){}
        usr.setMoodEventList(MEL);
        try {
            assertEquals("John",usr.getUsr());
            assertEquals(AL1,usr.getFolloweeIDs());
            assertEquals(AL2,usr.getFollowerIDs());
            assertEquals(AL3,usr.getPendingPermission());
            assertEquals(MEL, usr.getMoodEventList());
        }catch (Exception e){
            fail();
        }
    }

}
