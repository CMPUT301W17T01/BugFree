package com.example.mac.bugfree;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**This is a method class, the main purpose is to store the UserList passed in into a Json file.
 * (Version 1.0 contains store file locally only, upload function will be added later.)
 * To use this class,use the following steps:
 * (userList is the updated/created userList available to be stored)
 * 1. SaveFile s = new saveJsonFile(User user);
 * The file name is file.save.
 * @version2.0
 * @author Zhi Li
 */

public class SaveFile{
    private static final String FILENAME = "file.sav";

    public SaveFile(Context context,User user) {
        saveJson(context, user);
    }

    public void saveJson(Context context, User user) {
        try {

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
}
