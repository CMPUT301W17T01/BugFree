package com.example.mac.bugfree;

import java.util.ArrayList;

/**
 * Created by Zhi Li on 2017/2/25.
 */

public class UserList {
    private static ArrayList<User> UserList= new ArrayList<User>();
    private static Integer userListSize = 0;
    public void UserList (){
    }

    public Integer getUserListSize() {
        return userListSize;
    }
    public void addUser(User user) {
        UserList.add(user);
        this.userListSize++;
    }

    public User getUser(Integer userID) {
        return UserList.get(userID);
    }
}
