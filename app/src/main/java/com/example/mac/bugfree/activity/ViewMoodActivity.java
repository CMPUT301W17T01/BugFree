package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;

/**
 * This class is used to view the details for each sent mood events
 *
 * @author Mengyang Chen
 */

public class ViewMoodActivity extends AppCompatActivity {

    private MoodEvent moodEvent;
    private String currentUserName;

    /**
     * OnCreate starts from here
     * firstly call load_moodEvent() to get the moodevent that user want to see  everytime
     * Then set the text
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);

        load_moodEvent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);

        TextView moodState = (TextView) findViewById(R.id.moodState_textView);
        TextView socialSituation = (TextView) findViewById(R.id.socialSituation_textView);
        TextView reason = (TextView) findViewById(R.id.reason_textView);
        TextView date_text = (TextView) findViewById(R.id.date_textView);
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.picture_text);



        moodState.setText(moodEvent.getMoodState());
        if(moodEvent.getSocialSituation()!=null){
            socialSituation.setText(moodEvent.getSocialSituation());
        }

        if(moodEvent.getTriggerText()!=null){
            reason.setText(moodEvent.getTriggerText());
        }


        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = fmt.format(moodEvent.getDateOfRecord().getTime());
        date_text.setText(time);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mood, menu);
        return true;

    }

    /**
     * If selet the delete or edit icons
     * check if this event belongs to current user first
     * if not, it will not allow the users to change it
     *
     * @param item
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:

                if (moodEvent.getBelongsTo().equals(currentUserName)) {
                    editMoodEvent();
                    Intent intent = new Intent(ViewMoodActivity.this, EditActivity.class);
                    startActivity(intent);
                    return true;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Your can only edit your own mood events",Toast.LENGTH_SHORT).show();
                    break;
                }
            case R.id.action_delete:
                if (moodEvent.getBelongsTo().equals(currentUserName)) {
                    deleteMoodEvent();
                    Toast.makeText(getApplicationContext(), "deleted",Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    return true;
                }
                else{
                    Toast.makeText(getApplicationContext(), "You can only delete your own mood events",Toast.LENGTH_SHORT).show();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     *
     * local functions that allow users to load, edit, delete the mood events
     */

    public void load_moodEvent(){
        SharedPreferences sharedPreferences =getSharedPreferences("viewMoodEvent", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("moodevent","");
        moodEvent=gson.fromJson(json,MoodEvent.class);
        currentUserName = sharedPreferences.getString("currentUser", "").replace("\"", "");


    }


    private void deleteMoodEvent() {
        User user = new User();

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "").replace("\"", "");

        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(currentUserName);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        MoodEventList moodEventList = user.getMoodEventList();

        moodEventList.deleteMoodEvent(moodEvent);
        user.setMoodEventList(moodEventList);

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);
    }

    private void editMoodEvent() {
        User user = new User();

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "").replace("\"", "");

        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(currentUserName);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        SharedPreferences.Editor editor = getSharedPreferences("editMoodEvent", Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(moodEvent);
        editor.putString("moodevent",json);
        editor.apply();
    }

    protected void onStart(){
        super.onStart();
    }
}
