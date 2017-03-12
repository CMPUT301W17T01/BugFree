package com.example.mac.bugfree;

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
                return true;

            case R.id.action_delete:
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

    protected void onStart(){
        super.onStart();
    }
}
