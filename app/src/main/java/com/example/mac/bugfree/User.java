package com.example.mac.bugfree;

/**
 * Created by mac on 2017-02-21.
 */
import java.util.ArrayList;
public class User {
    private String usr;
    private static Integer uniqueID;
    private Integer usrID;
    private ArrayList<Integer> followeeIDs = new ArrayList<Integer>();
    private ArrayList<Integer> followerIDs= new ArrayList<Integer>();
    private ArrayList<Integer> pendingPermission= new ArrayList<Integer>();
    private ArrayList<MoodEvent> moodEventList= new ArrayList<MoodEvent>();

    public User() {
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getUsrID() {
        return usrID;
    }

    public void setUsrID(int usrID) {
        this.usrID = usrID;
    }

    public ArrayList<Integer> getFolloweeIDs() {
        return followeeIDs;
    }

    public void setFolloweeIDs(ArrayList<Integer> followeeIDs) {
        this.followeeIDs = followeeIDs;
    }

    public ArrayList<Integer> getFollowerIDs() {
        return followerIDs;
    }

    public void setFollowerID(ArrayList<Integer> followerIDs) {
        this.followerIDs = followerIDs;
    }

    public ArrayList<Integer> getPendingPermission() {
        return pendingPermission;
    }

    public void setPendingPermission(ArrayList<Integer> pendingPermission) {
        this.pendingPermission = pendingPermission;
    }

    public ArrayList<MoodEvent> getMoodEventList() {
        return moodEventList;
    }

    public void setMoodEventList(ArrayList<MoodEvent> moodEventList) {
        this.moodEventList = moodEventList;
    }
}
