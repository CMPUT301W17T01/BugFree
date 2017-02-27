package com.example.mac.bugfree;

import java.util.ArrayList;

/**
 * Created by Zhi Li on 2017/2/25.
 */

public class UserList {
    private static ArrayList<User> UserList= new ArrayList<User>();
    public UserList() {
    }

    public Integer getUserListSize() {
        return this.UserList.size();
    }
    public void addUser(User user) {
        UserList.add(user);
    }

    public User getUser(Integer userID) {
        return UserList.get(userID);
    }
    public void cleanList(){UserList.clear();}
}
