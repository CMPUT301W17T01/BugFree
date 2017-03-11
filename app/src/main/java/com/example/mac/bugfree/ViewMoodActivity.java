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

import java.util.Date;

/**
 * @author Mengyang Chen
 */

public class ViewMoodActivity extends AppCompatActivity {
    private String mood_state;
    private String social_situation;
    private String reason;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_view);
        setSupportActionBar(toolbar);

        TextView moodState = (TextView) findViewById(R.id.moodState_textView);
        TextView socialSituation = (TextView) findViewById(R.id.socialSituation_textView);
        TextView reason = (TextView) findViewById(R.id.reason_textView);
        TextView date = (TextView) findViewById(R.id.date_textView);
        ImageView image = (ImageView) findViewById(R.id.imageView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_mood, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO change icon
            case R.id.action_edit:
                return true;

            case R.id.action_delete:
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public boolean load_mood_list(){
        return true;
    }

    protected void onStart(){
        super.onStart();
    }
}
