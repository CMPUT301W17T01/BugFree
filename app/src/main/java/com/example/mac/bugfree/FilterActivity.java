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
    private String selectedMyMoodState;
    private String selectedFoMoodState;
    private String enteredMyReason;
    private String enteredFoReason;
    private Boolean checkedMyRecent = false;
    private Boolean checkedFoRecent = false;
    private Boolean checkedMyDisAll = false;
    private Boolean checkedFoDisAll = false;


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

        //Store the content of reason edittext
        enteredMyReason = myReasonEditText.getText().toString();
        enteredFoReason = foReasonEditText.getText().toString();

        // checkbox fo myself most recent week
        myMostRecentWeekCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMostRecentWeekCheckbox.isChecked()) {
                    // test
                    myDisplayAllCheckbox.setChecked(false);
                    checkedMyRecent = true;

//                    filterByMyMostRece();
                }
            }
        });
        // checkbox fo myself display all
        myDisplayAllCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDisplayAllCheckbox.isChecked()) {
                    //test
                    myMostRecentWeekCheckbox.setChecked(false);
                    checkedMyDisAll = true;

//                    filterByMyDisplayAll();

                }
            }
        });
        // checkbox fo following most recent week
        foMostRecentWeekCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foMostRecentWeekCheckbox.isChecked()) {
                    //test
                    foDisplayAllCheckbox.setChecked(false);
                    checkedFoRecent = true;

//                    filterByFoMostRece();
                }
            }
        });
        // checkbox fo following display all
        foDisplayAllCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foDisplayAllCheckbox.isChecked()) {
                    //test
                    foMostRecentWeekCheckbox.setChecked(false);
                    checkedFoDisAll = true;
//                    filterByFoDisplayAll();
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
                selectedMyMoodState = myEmotionalStateSpinner.getSelectedItem().toString();
//                filterByMyMoodState(selectedMyMoodState);
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
                selectedFoMoodState = foEmotionalStateSpinner.getSelectedItem().toString();
//                filterByFoMoodState(selectedFoMoodState);
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
                checkWhichischoosen();
                startActivity(new Intent(this, MainActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void checkWhichischoosen(){

    }
    public boolean filterList(){
        return true;
    }

    //TODO
    public void filterByMyMostRece(){
        //test
        Toast.makeText(this,"Myself Most Recent Week",Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByMyDisplayAll(){
        //test
        Toast.makeText(this,"Myself Display All",Toast.LENGTH_LONG).show();
    }
    //TODO
    public void filterByFoMostRece(){
        //test
        Toast.makeText(this,"Following Most Recent Week",Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByFoDisplayAll(){
        //test
        Toast.makeText(this,"Following Display All",Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByMyMoodState(String selectedMoodState){
        //test
        Toast.makeText(this,"Myself Mood State",Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByFoMoodState(String selectedMoodState){
        //test
        Toast.makeText(this,"Following Mood State",Toast.LENGTH_LONG).show();
    }


}
