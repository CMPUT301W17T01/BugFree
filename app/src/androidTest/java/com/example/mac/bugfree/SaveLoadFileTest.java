package com.example.mac.bugfree;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Zhi Li on 2017/3/8.
 */

public class SaveLoadFileTest extends ActivityInstrumentationTestCase2 {

    private User usr = new User("haha");
    private LoadFile load;
    private SaveFile save;
    private DeleteFile delete;

    public SaveLoadFileTest() {
        super(MainActivity.class);
        //Initialize a user for storage
        ArrayList<String> AL1 = new ArrayList<>();
        ArrayList<String> AL2 = new ArrayList<>();
        ArrayList<String> AL3 = new ArrayList<>();
        AL1.add("Sam");
        AL1.add("Lily");
        AL2.add("Ray");
        MoodEventList MEL = new MoodEventList();
        GregorianCalendar dateOfRecord = new GregorianCalendar(2017,2,28,13,12,30);
        GregorianCalendar dateOfRecord1 = new GregorianCalendar(2017,3,28,13,12, 30);
        try{
            MoodEvent mood = new MoodEvent("Happy", usr.getUsr());
            MoodEvent mood1 = new MoodEvent("Sad",usr.getUsr());
            mood.setTriggerText("Hello");
            mood.setSocialSituation("Alone");
            mood.setDateOfRecord(dateOfRecord);
            mood1.setDateOfRecord(dateOfRecord1);
            mood.setRealtime(dateOfRecord);
            mood1.setRealtime(dateOfRecord1);
            MEL.addMoodEvent(mood);
            MEL.addMoodEvent(mood1);
        }
        catch(Exception e){
            fail();
        }
        usr.setFolloweeIDs(AL1);
        usr.setFollowerIDs(AL2);
        usr.setPendingPermissions(AL3);
        usr.setMoodEventList(MEL);
    }

    //Test if a user file can be properly saved
    @Test
    public void testSaveUser(){
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        save = new SaveFile(context,usr);
        load = new LoadFile();
        User user = load.loadUser(context);
        assertEquals(usr,user);
    }
// This function is not available yet
//    public void testDeleteUser(){
//        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
//        delete = new DeleteFile();
//        assertTrue(delete.DeleteUserFile(context));
//    }
    //Test if a MoodEventList file can be properly saved
//    public void testSaveMoodEvent(){
//
//    }

}
