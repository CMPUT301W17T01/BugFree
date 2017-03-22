package com.example.mac.bugfree.module;

/**
 * This class stores all user related info, ID, follower ID followee ID ,and moodEventList.
 * An user can construct without a name for testing purpose, it should normally construct with
 * a name in String format.
 * Users are distinguished by uniqueID, corresponding to position stored in User List.
 * @author Zhi Li
 */

import java.util.ArrayList;

/**
 * The type User.
 */
public class User {

    private String usr;
    private ArrayList<String> followeeIDs = new ArrayList<>();
    private ArrayList<String> followerIDs = new ArrayList<>();
    private ArrayList<String> pendingPermissions = new ArrayList<>();
    private MoodEventList moodEventList= new MoodEventList();


    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param name the name
     */
    public User(String name){
        setUsr(name);
    }

    /**
     * Gets usr.
     *
     * @return the usr
     */
    public String getUsr() {
        return usr;
    }

    /**
     * Sets usr.
     *
     * @param usr the usr
     */
    public void setUsr(String usr) {
        this.usr = usr;
    }

    /**
     * Gets followee array.
     *
     * @return the followee array
     */
    public ArrayList<String> getFolloweeIDs() {
        return this.followeeIDs;
    }

    /**
     * Sets followee array
     *
     * @param followeeIDs the followee array
     */
    public void setFolloweeIDs(ArrayList<String> followeeIDs) {
        this.followeeIDs.addAll(followeeIDs);
    }

    /**
     * Gets follower array
     *
     * @return the follower array
     */
    public ArrayList<String> getFollowerIDs() {
        return followerIDs;
    }

    /**
     * Sets follower array
     *
     * @param followerIDs the follower array
     */
    public void setFollowerIDs(ArrayList<String> followerIDs) {
        this.followerIDs.addAll(followerIDs);
    }

    /**
     * Gets pending permission array.
     *
     * @return the pending permission array
     */
    public ArrayList<String> getPendingPermission() {
        return pendingPermissions;
    }

    /**
     * Sets pending permissions.
     *
     * @param pendingPermissions the pending permissions
     */
    public void setPendingPermissions(ArrayList<String> pendingPermissions) {
        this.pendingPermissions = pendingPermissions;
    }


    /**
     * Gets mood event list.
     *
     * @return the mood event list
     */
    public MoodEventList getMoodEventList() {
        return moodEventList;
    }

    /**
     * Sets mood event list.
     *
     * @param moodEventList the mood event list
     */
    public void setMoodEventList(MoodEventList moodEventList) {
        this.moodEventList = moodEventList;
    }

    @Override
    /**
     * This method check if the object passed in is equals to this User object.
     * @param obj Object (User object expected)
     * @returns boolean
     */
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof User)) return false;
        User otherUser = (User) obj;
        if(otherUser.getUsr().compareTo(this.getUsr())  == 0) return true;

        return false;
    }
}
