package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchImageController;
import com.example.mac.bugfree.controller.ElasticsearchImageOfflineController;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.controller.MoodEventAdapter;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.util.InternetConnectionChecker;
import com.example.mac.bugfree.util.LoadFile;
import com.example.mac.bugfree.util.SaveFile;
import com.google.gson.Gson;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * This class is used to view the details for each sent mood events
 *
 * @author Mengyang Chen
 */

public class ViewMoodActivity extends AppCompatActivity  {

    private MoodEvent moodEvent;
    private String currentUserName;

    /**
     * OnCreate starts from here
     * firstly call load_moodEvent() to get the moodevent that user want to see  everytime
     * Then set the text
     * @param savedInstanceState: savedInstanceState
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
        TextView location_text =(TextView) findViewById(R.id.Location_textView);
        ImageView picImage = (ImageView) findViewById(R.id.imageView);
        ImageView emoji = (ImageView) findViewById(R.id.imageView2);

        Context context = getApplicationContext();
        InternetConnectionChecker checker = new InternetConnectionChecker();
        final boolean isOnline = checker.isOnline(context);

        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");

        if (!isOnline && !currentUserName.equals("")){
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putBoolean("hasBeenOffline", true);
            editor.apply();
        }

        moodState.setText(moodEvent.getMoodState());
        if(moodEvent.getMoodState().equals("Anger")){
            emoji.setImageResource(R.drawable.anger);
        }
        if(moodEvent.getMoodState().equals("Confusion")){
            emoji.setImageResource(R.drawable.confusion);
        }
        if(moodEvent.getMoodState().equals("Disgust")){
            emoji.setImageResource(R.drawable.disgust);
        }
        if(moodEvent.getMoodState().equals("Fear")){
            emoji.setImageResource(R.drawable.fear);
        }
        if(moodEvent.getMoodState().equals("Happy")){
            emoji.setImageResource(R.drawable.happy);
        }
        if(moodEvent.getMoodState().equals("Sad")){
            emoji.setImageResource(R.drawable.sad);
        }
        if(moodEvent.getMoodState().equals("Shame")){
            emoji.setImageResource(R.drawable.shame);
        }
        if(moodEvent.getMoodState().equals("Surprise")){
            emoji.setImageResource(R.drawable.surprise);
        }
        if(moodEvent.getSocialSituation()!=null){
            socialSituation.setText(moodEvent.getSocialSituation());
        }

        if(moodEvent.getTriggerText()!=null){
            reason.setText(moodEvent.getTriggerText());
        }


        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = fmt.format(moodEvent.getDateOfRecord().getTime());
        date_text.setText(time);

        if (moodEvent.getPicId() != null){
            try{
            if(isOnline ||currentUserName.equals(moodEvent.getBelongsTo())) {
                Bitmap image = getImage(moodEvent);
                picImage.setImageBitmap(image);
            }else if(!isOnline){
                picImage.setImageResource(R.drawable.picture_text);
            }}catch(Exception e){
                Log.i("bitmap_error","null");
            }
        } else {
            picImage.setImageResource(R.drawable.umood);
        }

        if (moodEvent.getLocation()!=null){
            location_text.setText(moodEvent.getLocation().toString());
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mood, menu);
        return true;

    }

    /**
     * If select the delete or edit icons
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
                    setResult(RESULT_OK);
                    finish();
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

    /**
     *If users click on delete, delte this mood event online
     */
    private void deleteMoodEvent() {

        User user = new User();

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "").replace("\"", "");

        // When the moodEvent has been created, check for internet connection.
        // If online, sync to Elastic search and save locally.
        // If offline, save locally
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);

        if(isOnline) {
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(currentUserName);
            try {
                user = getUserTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        } else{
            LoadFile load = new LoadFile();
            user = load.loadUser(context);
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putBoolean("hasBeenOffline", true);
            editor.apply();
        }

        MoodEventList moodEventList = user.getMoodEventList();
        moodEventList.deleteMoodEvent(moodEvent);
        user.setMoodEventList(moodEventList);

        if (moodEvent.getPicId() != null) {
            if (isOnline) {
                ElasticsearchImageController.DeleteImageTask deleteImageTask =
                        new ElasticsearchImageController.DeleteImageTask();
                deleteImageTask.execute(moodEvent.getPicId());
                ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                elasticsearchImageOfflineController.DeleteImageTask(context,moodEvent.getPicId());
            } else {
                ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                elasticsearchImageOfflineController.DeleteImageTask(context,moodEvent.getPicId());
            }
            File file = context.getFileStreamPath(moodEvent.getPicId());
            file.delete();

        }

        if(isOnline) {
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user);
            SaveFile s = new SaveFile(context, user);
        } else{
            SaveFile s = new SaveFile(context, user);
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putBoolean("hasBeenOffline", true);
            editor.apply();
        }

    }
    /**
     * If users click on edit, jump to the EditActivity
     */
    private void editMoodEvent() {
        User user = new User();
        //TODO: use of user?
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

    /**
     *
     * @param moodEvent mood event from elastic search
     * @return imageForElasticSearch.base64ToImage()
     */
    private Bitmap getImage(MoodEvent moodEvent){
        ImageForElasticSearch imageForElasticSearch = new ImageForElasticSearch();
        String uniqueId = moodEvent.getPicId();
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String current_user = pref.getString("currentUser", "");


        if (isOnline) {
            ElasticsearchImageController.GetImageTask getImageTask = new ElasticsearchImageController.GetImageTask();
            getImageTask.execute(uniqueId);

            //imageForElasticSearch = new ImageForElasticSearch();

            try {
                imageForElasticSearch = getImageTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (imageForElasticSearch ==null){
                ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                imageForElasticSearch = elasticsearchImageOfflineController.GetImageTask(context,uniqueId);
            }
        } else if (current_user.equals(moodEvent.getBelongsTo())){
            ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
            imageForElasticSearch = elasticsearchImageOfflineController.GetImageTask(context,uniqueId);
        }

        return imageForElasticSearch.base64ToImage();
    }

    protected void onStart(){
        super.onStart();
    }
}
