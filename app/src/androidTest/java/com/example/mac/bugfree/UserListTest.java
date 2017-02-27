package com.example.mac.bugfree;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by Zhi Li on 2017/2/25.
 */

public class UserListTest extends ActivityInstrumentationTestCase2 {
    public UserListTest(){super(MainActivity.class);}

    // Test addUser&getUser
    public void testAddGetUser(){
        UserList usrList = new UserList();
        User usr1 = new User();
        assertEquals(usr1,usrList.getUser(0));
    }

    //Test clean list
    public void testCleanList(){
        UserList usrList = new UserList();
        User usr1 = new User();
        User usr2 = new User();
        User usr3 = new User();
        usrList.cleanList();
        assertTrue(0==usrList.getUserListSize());
    }

    // Test get size
    public void testGetSize(){
        UserList usrList = new UserList();
        usrList.cleanList();
        User usr1 = new User();
        User usr2 = new User();
        User usr3 = new User();
        assertTrue(3==usrList.getUserListSize());
    }
}
