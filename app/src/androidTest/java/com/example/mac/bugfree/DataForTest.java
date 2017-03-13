package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static junit.framework.Assert.fail;

/**
 * Created by Zhi Li on 2017/3/8.
 */

public class DataForTest extends ActivityInstrumentationTestCase2 {
    private String name0 = "John";
    private String name1 = "1Sam";
    private String name2 = "2Mike";
    private String name3 = "3Watson";
    private String name4 = "4Mary";
    private String name5 = "5Sherlock";
    private String name6 = "6Tom";
    private String name7 = "7Jerry";
    private String name8 = "8Jack";
    private String name9 = "9Rose";
    private User usr0 = new User(name0);
    private User usr1 = new User(name1);
    private User usr2 = new User(name2);
    private User usr3 = new User(name3);
    private User usr4 = new User(name4);
    private User usr5 = new User(name5);
    private User usr6 = new User(name6);
    private User usr7 = new User(name7);
    private User usr8 = new User(name8);
    private User usr9 = new User(name9);

    public DataForTest() {super(MainActivity.class);}

    public void testDataCreation() {
        //Can only add first 4 user because of unknown reason
        try {
            String md0 = "Anger";
            String md1 = "Confusion";
            String md2 = "Disgust";
            String md3 = "Fear";
            String md4 = "Happy";
            String md5 = "Sad";
            String md6 = "Shame";
            String md7 = "Surprise";

            String ss0 = "Alone";
            String ss1 = "With one other person";
            String ss2 = "Two to several people";
            String ss3 = "With a crowd";

            //User0
            ArrayList<String> AL1 = new ArrayList<>();//Followee
            ArrayList<String> AL2 = new ArrayList<>();//Follower
            ArrayList<String> AL3 = new ArrayList<>();//Pending

            AL1.add(name1);
            AL1.add(name2);
            AL1.add(name7);
            AL2.add(name3);
            AL2.add(name8);
            AL2.add(name9);
            AL3.add(name4);

            MoodEventList MEL = new MoodEventList();
            GregorianCalendar dateOfRecord = new GregorianCalendar(2016, 1, 27, 13, 12);
            GregorianCalendar dateOfRecord1 = new GregorianCalendar(2017, 1, 28, 13, 12);
            try {
                MoodEvent mood = new MoodEvent(md0, usr0.getUsr());
                MoodEvent mood1 = new MoodEvent(md1, usr0.getUsr());
                mood.setTriggerText("Hello");
                mood.setSocialSituation(ss0);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr0.setFolloweeIDs(AL1);
            usr0.setFollowerIDs(AL2);
            usr0.setPendingPermissions(AL3);
            usr0.setMoodEventList(MEL);

            //User1
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name2);
            AL1.add(name3);
            AL1.add(name8);
            AL2.add(name4);
            AL2.add(name0);
            AL2.add(name9);
            AL3.add(name5);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2016, 2, 27, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 2, 28, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md1, usr1.getUsr());
                MoodEvent mood1 = new MoodEvent(md2, usr1.getUsr());
                mood.setTriggerText("Hello1");
                mood.setSocialSituation(ss1);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr1.setFolloweeIDs(AL1);
            usr1.setFollowerIDs(AL2);
            usr1.setPendingPermissions(AL3);
            usr1.setMoodEventList(MEL);

            //User2
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name3);
            AL1.add(name4);
            AL1.add(name9);
            AL2.add(name5);
            AL2.add(name0);
            AL2.add(name1);
            AL3.add(name6);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 7, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 3, 8, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md2, usr2.getUsr());
                MoodEvent mood1 = new MoodEvent(md3, usr2.getUsr());
                mood.setTriggerText("Hello2");
                mood.setSocialSituation(ss2);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr2.setFolloweeIDs(AL1);
            usr2.setFollowerIDs(AL2);
            usr2.setPendingPermissions(AL3);
            usr2.setMoodEventList(MEL);

            //User3
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name4);
            AL1.add(name5);
            AL1.add(name0);
            AL2.add(name6);
            AL2.add(name1);
            AL2.add(name2);
            AL3.add(name7);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 6, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 3, 5, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md3, usr3.getUsr());
                MoodEvent mood1 = new MoodEvent(md4, usr3.getUsr());
                mood.setTriggerText("Hello3");
                mood.setSocialSituation(ss3);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr3.setFolloweeIDs(AL1);
            usr3.setFollowerIDs(AL2);
            usr3.setPendingPermissions(AL3);
            usr3.setMoodEventList(MEL);

            //User4
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name5);
            AL1.add(name6);
            AL1.add(name0);
            AL2.add(name7);
            AL2.add(name2);
            AL2.add(name3);
            AL3.add(name8);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 6, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 3, 28, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md4, usr4.getUsr());
                MoodEvent mood1 = new MoodEvent(md5, usr4.getUsr());
                mood.setTriggerText("Hello4");
                mood.setSocialSituation(ss0);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr4.setFolloweeIDs(AL1);
            usr4.setFollowerIDs(AL2);
            usr4.setPendingPermissions(AL3);
            usr4.setMoodEventList(MEL);

            //User5
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name6);
            AL1.add(name7);
            AL1.add(name2);
            AL2.add(name8);
            AL2.add(name3);
            AL2.add(name4);
            AL3.add(name9);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 5, 15, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 2, 5, 15, 12);

            try {
                MoodEvent mood = new MoodEvent(md5, usr5.getUsr());
                MoodEvent mood1 = new MoodEvent(md6, usr5.getUsr());
                mood.setTriggerText("Hello5");
                mood.setSocialSituation(ss1);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr5.setFolloweeIDs(AL1);
            usr5.setFollowerIDs(AL2);
            usr5.setPendingPermissions(AL3);
            usr5.setMoodEventList(MEL);

            //User6
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name7);
            AL1.add(name8);
            AL1.add(name3);
            AL2.add(name9);
            AL2.add(name4);
            AL2.add(name5);
            AL3.add(name0);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 1, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 6, 28, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md6, usr6.getUsr());
                MoodEvent mood1 = new MoodEvent(md7, usr6.getUsr());
                mood.setTriggerText("Hello6");
                mood.setSocialSituation(ss2);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr6.setFolloweeIDs(AL1);
            usr6.setFollowerIDs(AL2);
            usr6.setPendingPermissions(AL3);
            usr6.setMoodEventList(MEL);

            //User7
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name8);
            AL1.add(name9);
            AL1.add(name4);
            AL2.add(name0);
            AL2.add(name5);
            AL2.add(name6);
            AL3.add(name1);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 10, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 2, 18, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md7, usr7.getUsr());
                MoodEvent mood1 = new MoodEvent(md0, usr7.getUsr());
                mood.setTriggerText("Hello7");
                mood.setSocialSituation(ss3);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr7.setFolloweeIDs(AL1);
            usr7.setFollowerIDs(AL2);
            usr7.setPendingPermissions(AL3);
            usr7.setMoodEventList(MEL);

            //User8
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name9);
            AL1.add(name0);
            AL1.add(name5);
            AL2.add(name1);
            AL2.add(name7);
            AL2.add(name6);
            AL3.add(name2);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 9, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 3, 15, 13, 12);

            try {
                MoodEvent mood = new MoodEvent(md0, usr8.getUsr());
                MoodEvent mood1 = new MoodEvent(md1, usr8.getUsr());
                mood.setTriggerText("Hello8");
                mood.setSocialSituation(ss0);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr8.setFolloweeIDs(AL1);
            usr8.setFollowerIDs(AL2);
            usr8.setPendingPermissions(AL3);
            usr8.setMoodEventList(MEL);

            //User9
            AL1 = new ArrayList<>();//Followee
            AL2 = new ArrayList<>();//Follower
            AL3 = new ArrayList<>();//Pending

            AL1.add(name0);
            AL1.add(name1);
            AL1.add(name6);
            AL2.add(name7);
            AL2.add(name2);
            AL2.add(name8);
            AL3.add(name3);

            MEL = new MoodEventList();
            dateOfRecord = new GregorianCalendar(2017, 3, 6, 13, 12);
            dateOfRecord1 = new GregorianCalendar(2017, 2, 14, 13, 12);
            try {
                MoodEvent mood = new MoodEvent(md1, usr9.getUsr());
                MoodEvent mood1 = new MoodEvent(md2, usr9.getUsr());
                mood.setTriggerText("Hello9");
                mood.setSocialSituation(ss1);
                mood.setDateOfRecord(dateOfRecord);
                mood1.setDateOfRecord(dateOfRecord1);
                mood.setRealtime(dateOfRecord);
                mood1.setRealtime(dateOfRecord1);
                MEL.addMoodEvent(mood);
                MEL.addMoodEvent(mood1);
            } catch (Exception e) {
                fail();
            }
            usr9.setFolloweeIDs(AL1);
            usr9.setFollowerIDs(AL2);
            usr9.setPendingPermissions(AL3);
            usr9.setMoodEventList(MEL);

            //Add all users to Elastic Search
            //Clear Online data
            ElasticsearchUserController.createIndex();

            //Add
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(usr0);
            addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(usr1);
            addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(usr2);

            assertTrue(true);
        } catch (Exception e){
            assertTrue(false);
        }
    }
    public void testDataCreation2(){

        //Add
        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr3);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr4);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr5);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr6);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr7);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr8);
        addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(usr9);
    }
}
