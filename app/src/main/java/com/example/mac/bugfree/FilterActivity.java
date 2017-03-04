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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import java.util.ArrayList;


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
    ArrayAdapter<CharSequence> adapter;
    private ArrayList<MoodEventList> moodList;



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

        // checkbox fo myself most recent week
        myMostRecentWeekCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMostRecentWeekCheckbox.isChecked()) {
                    filterByMostRece();
                }
            }
        });
        // checkbox fo myself display all
        myDisplayAllCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDisplayAllCheckbox.isChecked()) {
                    filterByMostRece();
                }
            }
        });

        //spinner for myself
        adapter = ArrayAdapter.createFromResource(this,R.array.mood_states_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myEmotionalStateSpinner.setAdapter(adapter);
        myEmotionalStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedMoodState = myEmotionalStateSpinner.getSelectedItem().toString();
                filterByMoodState(selectedMoodState);
                //test
//                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i)+" is selected.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //spinner for following
        adapter = ArrayAdapter.createFromResource(this,R.array.mood_states_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foEmotionalStateSpinner.setAdapter(adapter);
        foEmotionalStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedMoodState = foEmotionalStateSpinner.getSelectedItem().toString();
                filterByMoodState(selectedMoodState);
//                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i)+" is selected.",Toast.LENGTH_LONG).show();
//                // test the stored value
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    public void filterByMostRece(){
        //test
        Toast.makeText(this,"Most recent week",Toast.LENGTH_LONG).show();

    }
    public void filterByMoodState(String selectedMoodState){
        //test
        Toast.makeText(this,"Mood state",Toast.LENGTH_LONG).show();


    }



}
