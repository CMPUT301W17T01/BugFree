package com.example.mac.bugfree.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.module.UserNameList;

import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {
    private String current_user;
    private User user = new User();
    ArrayList<Integer> total_list = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        TextView stat_anger = (TextView)findViewById(R.id.stat_anger);
        TextView stat_confusion = (TextView)findViewById(R.id.stat_confusion);
        TextView stat_disgust = (TextView)findViewById(R.id.stat_disgust);
        TextView stat_fear = (TextView)findViewById(R.id.stat_fear);
        TextView stat_happy = (TextView)findViewById(R.id.stat_happy);
        TextView stat_sad = (TextView)findViewById(R.id.stat_sad);
        TextView stat_shame = (TextView)findViewById(R.id.stat_shame);
        TextView stat_surprise = (TextView)findViewById(R.id.stat_surprise);

        TextView stat_anger_others = (TextView)findViewById(R.id.stat_anger_others);
        TextView stat_confusion_others = (TextView)findViewById(R.id.stat_confusion_others);
        TextView stat_disgust_others = (TextView)findViewById(R.id.stat_disgust_others);
        TextView stat_fear_others = (TextView)findViewById(R.id.stat_fear_others);
        TextView stat_happy_others = (TextView)findViewById(R.id.stat_happy_others);
        TextView stat_sad_others = (TextView)findViewById(R.id.stat_sad_others);
        TextView stat_shame_others = (TextView)findViewById(R.id.stat_shame_others);
        TextView stat_surprise_others = (TextView)findViewById(R.id.stat_surprise_others);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        current_user = pref.getString("currentUser", "");
        String query = current_user;
        ElasticsearchUserController.GetUserTask getUserTask =
                new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        MoodEventList moodEventList = user.getMoodEventList();
        int angers = get_anger(moodEventList);
        int confusions = get_confusion(moodEventList);
        int disgusts = get_disgust(moodEventList);
        int fears = get_fear(moodEventList);
        int happies = get_happy(moodEventList);
        int sads = get_sad(moodEventList);
        int shames = get_shame(moodEventList);
        int surprises = get_surprise(moodEventList);

        stat_anger.setText(String.valueOf(angers));
        stat_confusion.setText(String.valueOf(confusions));
        stat_disgust.setText(String.valueOf(disgusts));
        stat_fear.setText(String.valueOf(fears));
        stat_happy.setText(String.valueOf(happies));
        stat_sad.setText(String.valueOf(sads));
        stat_shame.setText(String.valueOf(shames));
        stat_surprise.setText(String.valueOf(surprises));

        UserNameList userNameList = new UserNameList();
        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask.execute("name");
        try{
            userNameList = getUserListTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        ArrayList unlist = userNameList.getUserNameList();
        totalCount(unlist);
        stat_anger_others.setText(String.valueOf(total_list.get(0)));
        stat_confusion_others.setText(String.valueOf(total_list.get(1)));
        stat_disgust_others.setText(String.valueOf(total_list.get(2)));
        stat_fear_others.setText(String.valueOf(total_list.get(3)));
        stat_happy_others.setText(String.valueOf(total_list.get(4)));
        stat_sad_others.setText(String.valueOf(total_list.get(5)));
        stat_shame_others.setText(String.valueOf(total_list.get(6)));
        stat_surprise_others.setText(String.valueOf(total_list.get(7)));
    }

    private int get_anger(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Anger")){
                count++;
            }
        }
        return count;
    }
    private int get_confusion(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Confusion") ) {
                count++;
            }
        }
        return count;
    }
    private int get_disgust(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Disgust")){
                count++;
            }
        }
        return count;
    }
    private int get_fear(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Fear")) {
                count++;
            }
        }
        return count;
    }
    private int get_happy(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Happy")){
                count++;
            }
        }
        return count;
    }
    private int get_sad(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Sad")) {
                count++;
            }
        }
        return count;
    }
    private int get_shame(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Shame")){
                count++;
            }
        }
        return count;
    }
    private int get_surprise(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Surprise")) {
                count++;
            }
        }
        return count;
    }

    private void totalCount(ArrayList unlist){
        int i=0;
        int angers_total=0, confusions_total=0,disgusts_total=0,fears_total=0,
                happies_total=0, sads_total=0, shames_total=0, surprises_total=0;

        for (i=0;i<unlist.size();i++) {
            String total_user = unlist.get(i).toString();
            ElasticsearchUserController.GetUserTask getUserTask =
                    new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(total_user);

            try{
                user = getUserTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
            MoodEventList total_moodEventList = user.getMoodEventList();
            angers_total+=get_anger(total_moodEventList);
            confusions_total+=get_confusion(total_moodEventList);
            disgusts_total+=get_disgust(total_moodEventList);
            fears_total+=get_fear(total_moodEventList);
            happies_total+=get_happy(total_moodEventList);
            sads_total+=get_sad(total_moodEventList);
            shames_total+=get_shame(total_moodEventList);
            surprises_total+=get_surprise(total_moodEventList);
        }
        total_list.add(angers_total);
        total_list.add(confusions_total);
        total_list.add(disgusts_total);
        total_list.add(fears_total);
        total_list.add(happies_total);
        total_list.add(sads_total);
        total_list.add(shames_total);
        total_list.add(surprises_total);

    }


}
