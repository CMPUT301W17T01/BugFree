package com.example.mac.bugfree;

/**
 * This is a model class to store the currentUser's unique ID
 * Pass the UniqueID of the user from signin to this function and store it.
 * Other classes and activities can get the stored unique ID to find the current user in the user list.
 * @author Zhi Li
 */

public class currentUser {
    public static Integer currentUser;

    public currentUser(Integer currentUser) {
        this.currentUser = currentUser;
    }

    public Integer getCurrentUser() {
        return currentUser;
    }

}
