package com.example.mac.bugfree.module;

import java.util.ArrayList;

/**
 * Created by mac on 2017-03-23.
 */

public class UserNameList {

    private ArrayList<String> userNameList;

    public UserNameList(ArrayList<String> userNameArrayList) {
        this.userNameList = userNameArrayList;
    }


    public UserNameList () {
        this.userNameList = new ArrayList<String>();
    }

    public ArrayList<String> getUserNameList() {
        return this.userNameList;
    }

    public boolean hadUserName(String userName) {
        return this.userNameList.contains(userName);
    }

    public void addUserName(String userName) {
        this.userNameList.add(userName);
    }

    public void deleteUserName(String userName) {
        this.userNameList.remove(userName);
    }

    public int getCount() {
        return this.userNameList.size();
    }


}
