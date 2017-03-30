package com.example.mac.bugfree.util;

import android.content.Context;

import com.example.mac.bugfree.module.MoodEvent;
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
 * 2.
 * 2.
 *
 * @author Zhi Li
 * @version2.0
 */
public class SaveFile{
    private static final String FILENAME = "file.sav";
//    private static final String IMAGEFILENAME = "image.sav";
    private static final String IMAGEONLINE = "ImageOnlineList.sav";
    private static final String IMAGEUPLOADLIST = "ImageUploadList.sav";
    private static final String IMAGEDELETELIST = "ImageDeleteList.sav";

    private String base64str;
    private ArrayList<String> imageList;

    public SaveFile() {

    }

    /**
     *
     *  This constructor is for newly signed in user, it creates all the empty file for offline function for image
     *  This image offline function is for the current user's image only.
     *  Only being called once after valid signin.
     *  There are three files to be created:
     *  1. image.sav (file stores a dictionary contains image unique id and the image base64 String,
     *                  both already exist in elastic search and image need to be upload to/delete from ElasticSearch.)
     *  2. ImageOriginList.sav (Contains the [arraylist of [strings of [image unique id]]] user Originally have in the ElasticSearch)
     *  3. ImageUploadList.sav (Contains the [arraylist of [strings of [image unique id]]] to be uploaded to the ElasticSearch)
     *  4. ImageDeleteList.sav (Contains the [arraylist of [strings of [image unique id]]] to be deleted from the ElasticSearch)
     *  5. file.sav (Json file contains the user object)
     * @param context
     * @param user
     *
     *
     * @param context the current context
     * @param user    the current user
     */
    public SaveFile(Context context, User user) {
        saveJson(context, user);
//        if (createNewImageProcedure) {
            try {
                // Base64 dictionary file
//                FileOutputStream fos = context.openFileOutput(IMAGEFILENAME, Context.MODE_PRIVATE);
//                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
//
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("base64", "something");
////                System.out.println(map.get("dog"));
////                map.remove("base64");
//                Gson gson = new Gson();
//                gson.toJson(map, out);
//                out.flush();
//                fos.close();

                // Arraylist, [strings of [image unique id]]] already in ElasticSearch
                FileOutputStream fos0 = context.openFileOutput(IMAGEONLINE, Context.MODE_PRIVATE);
                BufferedWriter out0= new BufferedWriter(new OutputStreamWriter(fos0));


                ArrayList<String> already = new ArrayList<String>();
                Gson gson0 = new Gson();
                gson0.toJson(already, out0);
                out0.flush();
                fos0.close();

                // Arraylist, [strings of [image unique id]]] to be uploaded to the ElasticSearch
                FileOutputStream fos1 = context.openFileOutput(IMAGEUPLOADLIST, Context.MODE_PRIVATE);
                BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(fos1));

                ArrayList<String> upload = new ArrayList<String>();
                Gson gson1 = new Gson();
                gson1.toJson(upload, out1);
                out1.flush();
                fos1.close();

                // Arraylist, [strings of [image unique id]]] to be deleted from ElasticSearch
                FileOutputStream fos2 = context.openFileOutput(IMAGEDELETELIST, Context.MODE_PRIVATE);
                BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(fos2));

                ArrayList<String> delete = new ArrayList<String>();
                Gson gson2 = new Gson();
                gson2.toJson(delete, out2);
                out2.flush();
                fos2.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException();
            } catch (IOException e) {
                throw new RuntimeException();
            }
//        }
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

    /**
     * Save the base64String to file name by the uniqueID
     * @param context
     * @param imageBase64
     * @param base64Id
     */
    public void saveBase64(Context context, String imageBase64, String base64Id){
        try {
            FileOutputStream fos = context.openFileOutput(base64Id, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(imageBase64, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Load the origin list and add/delete the ids in the array list appropriately
     * @param context
     * @param mode
     * @param imageIdToBeSave
     * @param imageIdToBeDelete
     */
    public void updateOfflineArrayList(Context context, String mode, String imageIdToBeSave, String imageIdToBeDelete){
        try {
            LoadFile load = new LoadFile();
            imageList = load.loadImageList(context, mode);

            // Change accordingly
            if (!imageIdToBeDelete.equals(null)) {
                imageList.remove(imageIdToBeDelete);
            }
            if(!imageIdToBeSave.equals(null)) {
                imageList.add(imageIdToBeSave);
            }

            FileOutputStream fos = context.openFileOutput(IMAGEUPLOADLIST, Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(imageList, out);
            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void saveWholeList
}
