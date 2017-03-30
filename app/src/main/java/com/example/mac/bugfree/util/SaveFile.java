package com.example.mac.bugfree.util;

import android.content.Context;

import com.example.mac.bugfree.controller.ElasticsearchImageController;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.module.User;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a method class, the main purpose is to store the UserList passed in into a Json file.
 * (Version 1.0 contains store file locally only, upload function will be added later.)
 * To use this class,use the following steps:
 * (userList is the updated/created userList available to be stored)
 * 1. Context context = getApplicationContext();
 * 1. SaveFile s = new SaveFile(context, user);
 * The file name is file.save.
 *
 * @author Zhi Li
 * @version2.0
 */
public class SaveFile{
    private static final String FILENAME = "file.sav";
//    private static final String IMAGEFILENAME = "image.sav";
//    private static final String IMAGEONLINE = "ImageOnlineList.sav";
    private static final String IMAGEUPLOADLIST = "ImageUploadList.sav";
    private static final String IMAGEDELETELIST = "ImageDeleteList.sav";

    private String base64str;
    private ArrayList<String> imageList;

    public SaveFile() {

    }
/**
    * Creates file.sav (Json file contains the user object)
    *
    * @param context the current context
    * @param user    the current user
 */
    public SaveFile(Context context, User user) {
        saveJson(context, user);
    }



    /**
     * Save json.
     *
     * @param context the current context
     * @param user    the current user
     */
    public void saveJson(Context context, User user) {
        try {

            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(user, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void saveArrayList(Context context, ArrayList<MoodEvent> moodEventArrayList, String saveFILENAME) {
        try {
            FileOutputStream fos = context.openFileOutput(saveFILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            // save the record list to Json
            Gson gson = new Gson();
            gson.toJson(moodEventArrayList, out);

            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
