package com.example.mac.bugfree;

/**
 * This class stores all user related info, name, follower ID list, followee ID list,and moodEventList.
 * An user should construct with a name in String format, this name is unique.
 * @author Zhi Li,Xinlei Chen
 */
import java.util.ArrayList;
public class User {

    private String usr;
    private ArrayList<String> followeeIDs = new ArrayList<>();
    private ArrayList<String> followerIDs = new ArrayList<>();
    private ArrayList<String> pendingPermissions = new ArrayList<>();
    private MoodEventList moodEventList= new MoodEventList();

    public User (){}
    public User(String name){
        setUsr(name);
    }
    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

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
