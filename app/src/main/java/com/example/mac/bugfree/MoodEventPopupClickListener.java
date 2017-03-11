package com.example.mac.bugfree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

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
                //Integer userId1 = this.moodEvent.getBelongsTo();
                break;

            case R.id.delete_card:
                deleteMoodEvent();
                break;

            default:

        }
        return true;
    }

    private void deleteMoodEvent() {
        Log.d("MoodEventDetail", moodEvent.getBelongsTo());
        Log.d("MoodEventDetail", moodEvent.getMoodState());

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

        Log.d("Text in PopupClick", user.getUsr());

        MoodEventList moodEventList = user.getMoodEventList();

        Log.d("Compare", String.valueOf(moodEventList.getMoodEvent(0).equals(moodEvent)));

        Log.d("Text in PopupClick-", String.valueOf(moodEventList.hasMoodEvent(moodEvent)));

        moodEventList.deleteMoodEvent(moodEvent);

        Log.d("Text in PopupClick", String.valueOf(moodEventList.hasMoodEvent(moodEvent)));

        user.setMoodEventList(moodEventList);

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);

        ElasticsearchUserController.GetUserTask getUserTask2 = new ElasticsearchUserController.GetUserTask();
        getUserTask2.execute(currentUserName);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        MoodEventList moodEventList2 = user.getMoodEventList();
        Log.d("Text in PopupClick----", String.valueOf(moodEventList.hasMoodEvent(moodEvent)));
    }
}
