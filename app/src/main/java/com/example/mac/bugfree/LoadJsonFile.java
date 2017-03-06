package com.example.mac.bugfree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**This is a method class, the main purpose is to load the UserList From a Json file.
 * (Version 1.0 contains load file locally only, load from online function will be added later.)
 * To use this class,use the following steps:
 *  (need to declare: [private UserList userList;] on top)
 * 1. LoadJsonFile load = new LoadJsonFile();
 * 2. userList = load.loadFile();
 * @version1.0
 * @author Zhi Li
 */

public class LoadJsonFile extends MainActivity {
    private static final String FILENAME = "file.sav";
    private UserList userList;
    public UserList loadFile(){
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            Type listType = new TypeToken<UserList>(){}.getType();
            userList = gson.fromJson(in, listType);
            return userList;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            userList = new UserList();
            return userList;
        }
//        catch (IOException e) {
//            // TODO Auto-generated catch block
//            throw new RuntimeException();
//        }
    }
}
