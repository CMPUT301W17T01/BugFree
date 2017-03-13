package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

/**
 * @author Zhi Li
 */

public class UserListTest extends ActivityInstrumentationTestCase2 {
    public UserListTest(){super(MainActivity.class);}
//Since User List is nolonger exist, These tests are all not needed
//    // Test addUser&getUser
//    public void testAddGetUser(){
//        UserList usrList = new UserList();
//        User usr1 = new User();
//        assertEquals(usr1,usrList.getUser(0));
//    }
//
//    //Test clean list
//    public void testCleanList(){
//        UserList usrList = new UserList();
//        User usr1 = new User();
//        User usr2 = new User();
//        User usr3 = new User();
//        usrList.cleanList();
//        assertTrue(0==usrList.getUserListSize());
//    }
//
//    // Test get size
//    public void testGetSize(){
//        UserList usrList = new UserList();
//        usrList.cleanList();
//        User usr1 = new User();
//        User usr2 = new User();
//        User usr3 = new User();
//        assertTrue(3==usrList.getUserListSize());
//    }
}
