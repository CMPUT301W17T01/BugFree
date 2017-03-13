package com.example.mac.bugfree;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * This is a method class, the main purpose is to load the UserList From a Json file.
 * (Version 1.0 contains load file locally only, load from online function will be added later.)
 * To use this class,use the following steps:
 * (need to declare: [private UserList userList;] on top)
 * 1. LoadFile load = new LoadFile();
 * 2. user = load.loadFile();
 *
 * @author Zhi Li
 * @version2.0
 */
public class LoadFile{
    private static final String FILENAME = "file.sav";
    private static final String FILENAME2 = "filter.sav";

    private User user;

    /**
     * Instantiates a new Load file.
     */
    public LoadFile(){}

    /**
     * Load CurrentUser Json file
     *
     * @param context the context
     * @return the user
     */
    public User loadUser(Context context) {
        try {
            //Taken fron https://static.javadoc.io/com.google.code.gson/gson/2.6.2/com/google/gson/Gson.html
            //2017-03-07 21:10
            FileInputStream fis = context.openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type type = new TypeToken<User>(){}.getType();
            user = gson.fromJson(in, type); // deserializes json into User
            fis.close();
            return user;
        } catch (FileNotFoundException e) {
            return null;
        } catch (NullPointerException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Load filtered mood event list array list.
     *
     * @param context the context
     * @return the array list
     */
    public ArrayList<MoodEvent> loadFilteredMoodEventList(Context context) {
        ArrayList<MoodEvent> moodEventList;

        try {
            FileInputStream fis = context.openFileInput(FILENAME2);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            moodEventList = gson.fromJson(in, new TypeToken<ArrayList<MoodEvent>>(){}.getType());

            fis.close();
            return moodEventList;

        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
