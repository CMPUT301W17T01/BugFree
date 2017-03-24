package com.example.mac.bugfree.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.activity.EditActivity;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.module.User;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * This is the listener for popup menu which in card view
 * Reference: http://stackoverflow.com/questions/34641240/toolbar-inside-cardview-to-create-a-popup-menu-overflow-icon
 *
 * @author Xinlei Chen
 */

public class MoodEventPopupClickListener implements PopupMenu.OnMenuItemClickListener {

    private static final String FILENAME2 = "filter.sav";

    private int position;
    private MoodEvent moodEvent;
    private Context context;
    private String currentUserName;

    /**
     * Instantiates a new Mood event popup click listener.
     *
     * @param position  the position
     * @param moodEvent the mood event
     * @param context   the context
     */
    public MoodEventPopupClickListener(int position, MoodEvent moodEvent, Context context) {
        this.position = position;
        this.moodEvent = moodEvent;
        this.context = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.edit_card:
                editMoodEvent();
                break;

            case R.id.delete_card:
                deleteMoodEvent();
                break;

            default:

        }
        return true;
    }

    /**
     * The function will be invoked when click delete in pop up menu
     * Delete moodEvent
     */
    private void deleteMoodEvent() {
        User user = new User();

        SharedPreferences pref = context.getSharedPreferences("data", context.MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");

        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(currentUserName);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        MoodEventList moodEventList = user.getMoodEventList();

        Log.d("Compare", String.valueOf(moodEventList.getMoodEvent(0).equals(moodEvent)));

        moodEventList.deleteMoodEvent(moodEvent);
        user.setMoodEventList(moodEventList);

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);

        if (fileExists(context, FILENAME2)) {
            saveInFile(moodEventList);
        }

    }

    /**
     * The function will be invoked when click edit in pop up menu
     * Edit the moodEvent
     * go to EditActivity
     */
    private void editMoodEvent() {
        SharedPreferences.Editor editor = context.getSharedPreferences("editMoodEvent",Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(moodEvent);
        editor.putString("moodevent",json);
        editor.apply();

        Intent intent = new Intent(context, EditActivity.class);
        context.startActivity(intent);
    }

    /**
     * To check if the file "filter.sav" is exist
     * @param context
     * @param filename
     * @return if it exists, return true. Vice Versa
     */
    private boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    private void saveInFile(MoodEventList moodEventList) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME2,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            ArrayList<MoodEvent> moodEventArrayList = moodEventList.transferMoodEventListToArray();

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
