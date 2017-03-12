package com.example.mac.bugfree;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Mengyang Chen
 */

public class ViewMoodActivity extends AppCompatActivity {

    private MoodEvent moodEvent;
    private String currentUserName;
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

        moodState.setText(moodEvent.getMoodState().toString());
        if(moodEvent.getSocialSituation()==null){
            //do nothing
        }
        else {
            socialSituation.setText(moodEvent.getSocialSituation().toString());
        }
        if(moodEvent.getTriggerText()==null){
            //do nothing
        }
        else {
            reason.setText(moodEvent.getTriggerText().toString());
        }

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = fmt.format(moodEvent.getDateOfRecord().getTime());
        date_text.setText(time);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mood, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO finish functionality
            case R.id.action_edit:
                //editMoodEvent();

            case R.id.action_delete:
                //deleteMoodEvent();
                setResult(RESULT_OK);
                finish();


        }
        return super.onOptionsItemSelected(item);
    }

    public void load_moodEvent(){
        SharedPreferences sharedPreferences =getSharedPreferences("viewMoodEvent", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("moodevent","");
        moodEvent=gson.fromJson(json,MoodEvent.class);
    }
//
//    private void deleteMoodEvent() {
//        User user = new User();
//
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        currentUserName = pref.getString("currentUser", "");
//
//        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
//        getUserTask.execute(currentUserName);
//
//        try{
//            user = getUserTask.get();
//        } catch (Exception e) {
//            Log.i("Error", "Failed to get the User out of the async object");
//        }
//
//        MoodEventList moodEventList = user.getMoodEventList();
//
//        Log.d("Compare", String.valueOf(moodEventList.getMoodEvent(0).equals(moodEvent)));
//
//        moodEventList.deleteMoodEvent(moodEvent);
//        user.setMoodEventList(moodEventList);
//
//        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
//        addUserTask.execute(user);
//    }
//
//    private void editMoodEvent() {
//        User user = new User();
//
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        currentUserName = pref.getString("currentUser", "");
//
//        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
//        getUserTask.execute(currentUserName);
//
//        try{
//            user = getUserTask.get();
//        } catch (Exception e) {
//            Log.i("Error", "Failed to get the User out of the async object");
//        }
//
//        SharedPreferences.Editor editor = getSharedPreferences("editMoodEvent", Context.MODE_PRIVATE).edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(moodEvent);
//        editor.putBoolean("flag", true);
//        editor.putString("moodevent",json);
//        editor.apply();
//    }

    protected void onStart(){
        super.onStart();
    }
}
