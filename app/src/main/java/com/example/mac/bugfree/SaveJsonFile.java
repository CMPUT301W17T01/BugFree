package com.example.mac.bugfree;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**This is a method class, the main purpose is to store the UserList passed in into a Json file.
 * (Version 1.0 contains store file locally only, upload function will be added later.)
 * To use this class,use the following steps:
 * (userList is the updated/created userList available to be stored)
 * 1. SaveJsonFile s = new saveJsonFile(userList);
 *
 * @version1.0
 * @author Zhi Li
 */

public class SaveJsonFile extends CreateEditMoodActivity {
    private static final String FILENAME = "file.sav";

    public SaveJsonFile(User user) {
        saveJson(user);
    }

    public void saveJson(User user) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(user, writer);
            writer.flush();
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
