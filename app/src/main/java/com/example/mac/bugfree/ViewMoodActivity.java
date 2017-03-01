package com.example.mac.bugfree;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Date;

public class ViewMoodActivity extends AppCompatActivity {
    private String mood_state;
    private String social_situation;
    private String reason;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mood);
    }

    public boolean load_mood_list(){
        return true;
    }
}
