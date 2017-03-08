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

//    private static Integer uniqueID = 0;
//    private Integer usrID;
//    private ArrayList<Integer> followeeIDs = new ArrayList<Integer>();
//    private ArrayList<Integer> followerIDs= new ArrayList<Integer>();
//    private ArrayList<Integer> pendingPermissions= new ArrayList<Integer>();
//    private UserList usrList = new UserList();

    private String usr;
    private ArrayList<String> followeeIDs = new ArrayList<>();
    private ArrayList<String> followerIDs = new ArrayList<>();
    private ArrayList<String> pendingPermissions = new ArrayList<>();
    private MoodEventList moodEventList= new MoodEventList();


    public User() {
    }
    public User(String name){
        setUsr(name);
    }
    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

//    public int getUniqueID() {
//        return uniqueID;
//    }

//    public void setUniqueID(int uniqueID) {
//        this.uniqueID = uniqueID;
//    }

//    public int getUsrID() {
//        return usrID;
//    }

//    public void setUsrID(int uniqueID) {
//        this.usrID = uniqueID;
//        setUniqueID(uniqueID+1);
//    }

    public ArrayList<String> getFolloweeIDs() {
        return this.followeeIDs;
    }

    public void setFolloweeIDs(ArrayList<String> followeeIDs) {
        this.followeeIDs.addAll(followeeIDs);
    }
    public void setFolloweeID(String followeeID) {
        this.followeeIDs.add(followeeID);
    }

    public ArrayList<String> getFollowerIDs() {
        return followerIDs;
    }

    public void setFollowerIDs(ArrayList<String> followerIDs) {
        this.followerIDs.addAll(followerIDs);
    }

    public void setFollowerID(String followerID) {
        this.followerIDs.add(followerID);
    }
    public ArrayList<String> getPendingPermission() {
        return pendingPermissions;
    }

    public void setPendingPermissions(ArrayList<String> pendingPermissions) {
        this.pendingPermissions = pendingPermissions;
    }
    public void setPendingPermission(String pendingPermission) {
        this.pendingPermissions.add(pendingPermission);
    }

    public MoodEventList getMoodEventList() {
        return moodEventList;
    }

    public void setMoodEventList(MoodEventList moodEventList) {
        this.moodEventList = moodEventList;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof User)) return false;
        User otherUser = (User) obj;
        if(otherUser.getUsr().compareTo(this.getUsr())  == 0) return true;

        return false;
    }
}
