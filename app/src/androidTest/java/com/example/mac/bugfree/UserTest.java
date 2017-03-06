package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Zhi Li
 */

public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest(){ super(MainActivity.class);}

    // Test Getter and Setters
    public void testGetterSetter(){
        User usr = new User();
        UserList usrList = new UserList();
        usr.setUsr("John");
        ArrayList<Integer> AL1 = new ArrayList<Integer>(Arrays.asList(2,3));
        usr.setFolloweeIDs(AL1);
        ArrayList<Integer> AL2 = new ArrayList<Integer>(Arrays.asList(4,5));
        usr.setFollowerIDs(AL2);
        ArrayList<Integer> AL3 = new ArrayList<Integer>(Arrays.asList(6,7));
        usr.setPendingPermissions(AL3);
        MoodEventList MEL = new MoodEventList();
        try{MoodEvent mood = new MoodEvent("Happy",0);
            MEL.addMoodEvent(mood);
            MoodEvent mood1 = new MoodEvent("Sad",0);
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
    //Test UniqueID
    public void testUniqueID(){
        User usr1 = new User();
        User usr2 = new User();
        User usr3 = new User();
        Integer unique = usr1.getUniqueID();
        UserList usrList = new UserList();
        Integer i = usrList.getUserListSize();
        Boolean testResult = true;
        for (Integer n = 0; n<i;n++){
            if (usrList.getUser(n).getUsrID() == unique){
                testResult =false;
            }
        }
        assertTrue(testResult);
    }
}
