package com.example.mac.bugfree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;


public class FilterActivity extends AppCompatActivity {
    private Spinner myEmotionalStateSpinner;
    private CheckBox myMostRecentWeekCheckbox;
    private EditText myReasonEditText;
    private CheckBox myDisplayAllCheckbox;
    private Spinner foEmotionalStateSpinner;
    private CheckBox foMostRecentWeekCheckbox;
    private EditText foReasonEditText;
    private CheckBox foDisplayAllCheckbox;
//    private ArrayList<MoodEvent> moodList = new ArrayList<MoodEvent>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // content of tab Myself
        myEmotionalStateSpinner = (Spinner) findViewById(R.id.spinner_myself);
        myMostRecentWeekCheckbox = (CheckBox) findViewById(R.id.checkbox_recent_myself);
        myReasonEditText = (EditText) findViewById(R.id.edittext_reason_myself);
        myDisplayAllCheckbox = (CheckBox) findViewById(R.id.checkbox_display_myself);

        // content of following
        foEmotionalStateSpinner = (Spinner) findViewById(R.id.spinner_following);
        foMostRecentWeekCheckbox = (CheckBox) findViewById(R.id.checkbox_recent_following);
        foReasonEditText = (EditText) findViewById(R.id.edittext_reason_following);
        foDisplayAllCheckbox = (CheckBox) findViewById(R.id.checkbox_display_following);

        if(myMostRecentWeekCheckbox.isChecked()){
            myMostRecentWeekCheckbox.setChecked(false);
        }
        if(myDisplayAllCheckbox.isChecked()){
            myDisplayAllCheckbox.setChecked(false);
        }
        if(foMostRecentWeekCheckbox.isChecked()){
            foMostRecentWeekCheckbox.setChecked(false);
        }
        if(foDisplayAllCheckbox.isChecked()){
            foDisplayAllCheckbox.setChecked(false);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);

        TabHost tabHost = (TabHost)findViewById(R.id.filter_tabHost);

        tabHost.setup();

        //Tab Myself
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Myself");
        tab1.setIndicator("Myself");
        tab1.setContent(R.id.myself);
        tabHost.addTab(tab1);

        //Tab Following
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Following");
        tab2.setIndicator("Following");
        tab2.setContent(R.id.following);
        tabHost.addTab(tab2);
        
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);

        return true;

    }
    // Determines if Action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.activity_filter:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            //TODO change icon

        }
        return super.onOptionsItemSelected(item);
    }

    public boolean filterList(){
        return true;
    }


}
