package com.example.mac.bugfree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by mac on 2017-02-21.
 */

public class MoodEventPopupClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;
    private MoodEvent moodEvent;
    private Context context;
    private String currentUserName;

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

//        MoodEventList moodEventList = user.getMoodEventList();
//        for (MoodEvent moodEvent1 : moodEventList.transferMoodEventListToArray()){
//            Log.d("Error check in Popup", String.valueOf(moodEvent1.equals(moodEvent)) );
//            Log.d("Error in belongs", String.valueOf(moodEvent1.getBelongsTo().equals(moodEvent.getBelongsTo())));
//            Log.d("Error in realtime", String.valueOf(moodEvent1.getRealtime().equals(moodEvent.getRealtime())));
//        }

    }

    private void editMoodEvent() {
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

        SharedPreferences.Editor editor = context.getSharedPreferences("editMoodEvent",Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(moodEvent);
        editor.putBoolean("flag", true);
        editor.putString("moodevent",json);
        editor.apply();

        Intent intent = new Intent(context, EditActivity.class);
        context.startActivity(intent);
    }
}
