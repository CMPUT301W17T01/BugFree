package com.example.mac.bugfree;

/**
 * This class stores all user related info, ID, follower ID followee ID ,and moodEventList.
 * An user can construct without a name for testing purpose, it should normally construct with
 * a name in String format.
 * Users are distinguished by uniqueID, corresponding to position stored in User List.
 * @author Zhi Li
 */
import java.util.ArrayList;
public class User {
    private String usr;
    private static Integer uniqueID = 0;
    private Integer usrID;
    private ArrayList<Integer> followeeIDs = new ArrayList<Integer>();
    private ArrayList<Integer> followerIDs= new ArrayList<Integer>();
    private ArrayList<Integer> pendingPermissions= new ArrayList<Integer>();
    private MoodEventList moodEventList= new MoodEventList();
    private UserList usrList = new UserList();
    public static Integer currentUserID;

    public User() {
        setUsrID(uniqueID);
        usrList.addUser(this);
    }
    public User(String name){
        setUsrID(uniqueID);
        usrList.addUser(this);
        setUsr(name);
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

    public void setUsrID(int uniqueID) {
        this.usrID = uniqueID;
        setUniqueID(uniqueID+1);
    }

    public ArrayList<Integer> getFolloweeIDs() {
        return followeeIDs;
    }

    public void setFolloweeIDs(ArrayList<Integer> followeeIDs) {
        this.followeeIDs.add(uniqueID);
    }
    public void setFolloweeID(Integer followeeID) {
        this.followeeIDs.add(followeeID);
    }

    public ArrayList<Integer> getFollowerIDs() {
        return followerIDs;
    }

    public void setFollowerIDs(ArrayList<Integer> followerIDs) {
        this.followerIDs.add(uniqueID);
    }

    public void setFollowerID(Integer followerID) {
        this.followerIDs.add(followerID);
    }
    public ArrayList<Integer> getPendingPermission() {
        return pendingPermissions;
    }

    public void setPendingPermissions(ArrayList<Integer> pendingPermissions) {
        this.pendingPermissions = pendingPermissions;
    }
    public void setPendingPermission(Integer pendingPermission) {
        this.pendingPermissions.add(pendingPermission);
    }

    public MoodEventList getMoodEventList() {
        return moodEventList;
    }

    public void setMoodEventList(MoodEventList moodEventList) {
        this.moodEventList = moodEventList;
    }

    public static Integer getCurrentUserID() {
        return currentUserID;
    }

    public static void setCurrentUserID(Integer currentUserID) {
        User.currentUserID = currentUserID;
    }
}
