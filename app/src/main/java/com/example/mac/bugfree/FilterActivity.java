package com.example.mac.bugfree;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class FilterActivity extends AppCompatActivity {
    private static final String FILENAME = "filter.sav";
    private Spinner myEmotionalStateSpinner;
    private CheckBox myMostRecentWeekCheckbox;
    private EditText myReasonEditText;
    private CheckBox myDisplayAllCheckbox;
    private Spinner foEmotionalStateSpinner;
    private CheckBox foMostRecentWeekCheckbox;
    private EditText foReasonEditText;
    private CheckBox foDisplayAllCheckbox;
    ArrayAdapter<CharSequence> adapter;
    private ArrayList<MoodEventList> moodList;
    private String selectedMyMoodState;
    private String selectedFoMoodState;
    private String enteredMyReason;
    private String enteredFoReason;
    private int flag;
    private ArrayList<String> followeeList;
    private MoodEventList moodListBeforeFilter = new MoodEventList();
    private ArrayList<MoodEvent> moodListAfterFilter = new ArrayList<MoodEvent>();
//    private MoodEventList moodListAfterFilter = new MoodEventList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        String current_user = pref.getString("currentUser", "");

        User user = new User("John");
        String query = user.getUsr();
        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        followeeList  = user.getFolloweeIDs();
        moodListBeforeFilter = user.getMoodEventList();


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

        // checkbox for myself most recent week
        myMostRecentWeekCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myMostRecentWeekCheckbox.isChecked()) {
                    myDisplayAllCheckbox.setChecked(false);
                    foMostRecentWeekCheckbox.setChecked(false);
                    foDisplayAllCheckbox.setChecked(false);
                }
            }
        });
        // checkbox for myself display all
        myDisplayAllCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myDisplayAllCheckbox.isChecked()) {
                    myMostRecentWeekCheckbox.setChecked(false);
                    foMostRecentWeekCheckbox.setChecked(false);
                    foDisplayAllCheckbox.setChecked(false);
                }
            }
        });
        // checkbox for following most recent week
        foMostRecentWeekCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foMostRecentWeekCheckbox.isChecked()) {
                    myMostRecentWeekCheckbox.setChecked(false);
                    myDisplayAllCheckbox.setChecked(false);
                    foDisplayAllCheckbox.setChecked(false);
                }
            }
        });
        // checkbox for following display all
        foDisplayAllCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foDisplayAllCheckbox.isChecked()) {
                    myMostRecentWeekCheckbox.setChecked(false);
                    myDisplayAllCheckbox.setChecked(false);
                    foMostRecentWeekCheckbox.setChecked(false);
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
                if(i > 0) {
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i) + " is selected.", Toast.LENGTH_LONG).show();
                }
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
                if(i > 0) {
                    Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i) + " is selected.", Toast.LENGTH_LONG).show();
                }
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
                checkWhichIsChoosen();
                if(flag > 1){
                    Toast.makeText(this,"Warning: More than one option is chosen" ,Toast.LENGTH_LONG).show();
                    setErrorMessages();
                    break;
                }

                saveInFile();
                startActivity(new Intent(this, MainActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void checkWhichIsChoosen(){
        moodListAfterFilter.clear();
        deleteFile("filter.sav");
        flag = 0;
        enteredMyReason = myReasonEditText.getText().toString();
        enteredFoReason = foReasonEditText.getText().toString();
        selectedMyMoodState = myEmotionalStateSpinner.getSelectedItem().toString();
        selectedFoMoodState = foEmotionalStateSpinner.getSelectedItem().toString();

        if(selectedMyMoodState != null && !selectedMyMoodState.isEmpty()){
//            Toast.makeText(this,"This is the selected Mood State of Myself " + selectedMyMoodState,Toast.LENGTH_LONG).show();
            filterByMyMoodState(selectedMyMoodState);
            flag ++;
        }
        if(selectedFoMoodState != null && !selectedFoMoodState.isEmpty()){
//            Toast.makeText(this,"This is the selected Mood State of Following " + selectedFoMoodState,Toast.LENGTH_LONG).show();
            filterByFoMoodState(selectedFoMoodState);
            flag ++;
        }

        if (myMostRecentWeekCheckbox.isChecked()){
//            Toast.makeText(this,"Myself Most Recent Week duile",Toast.LENGTH_LONG).show();
            filterByMyMostRece();
            flag ++;
        }
        if (foMostRecentWeekCheckbox.isChecked()){
//            Toast.makeText(this,"Following Most Recent Week duile",Toast.LENGTH_LONG).show();
            filterByFoMostRece();
            flag ++;
        }

        if (myDisplayAllCheckbox.isChecked()){
//            Toast.makeText(this,"Myself Display All duile",Toast.LENGTH_LONG).show();
            filterByMyDisplayAll();
            flag ++;
        }
        if (foDisplayAllCheckbox.isChecked()){
//            Toast.makeText(this,"Following Display All duile",Toast.LENGTH_LONG).show();
            filterByFoDisplayAll();
            flag ++;
        }

        if(enteredMyReason != null && !enteredMyReason.isEmpty()){
//            Toast.makeText(this,"This is the Reason of Myself: " + enteredMyReason,Toast.LENGTH_LONG).show();
            filterByMyReason(enteredMyReason);
            flag ++;
        }

        if(enteredFoReason != null && !enteredFoReason.isEmpty()){
//            Toast.makeText(this,"This is the Reason of Following: " + enteredFoReason,Toast.LENGTH_LONG).show();
            filterByFoReason(enteredFoReason);
            flag ++;
        }

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
        for (int i = 0; i < moodListBeforeFilter.getCount(); i++ ){
            moodListAfterFilter.add(moodListBeforeFilter.getMoodEvent(i));
        }
//        moodListAfterFilter = moodListBeforeFilter;
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
        Toast.makeText(this,"Myself Mood State: "+ selectedMoodState,Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByFoMoodState(String selectedMoodState){
        //test
        Toast.makeText(this,"Following Mood State: " + selectedMoodState,Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByMyReason(String enteredReason){
        //test
        Toast.makeText(this,"Myself Reason: "+ enteredReason,Toast.LENGTH_LONG).show();
    }

    //TODO
    public void filterByFoReason(String enteredReason){
        //test
        Toast.makeText(this,"Following Reason: "+ enteredReason,Toast.LENGTH_LONG).show();
    }

    public void setErrorMessages(){

        if(selectedMyMoodState != null && !selectedMyMoodState.isEmpty()){
            ((TextView)myEmotionalStateSpinner.getSelectedView()).setError("More than one option is chosen");
        }
        if(selectedFoMoodState != null && !selectedFoMoodState.isEmpty()){
            ((TextView)foEmotionalStateSpinner.getSelectedView()).setError("More than one option is chosen");
        }

        if (myMostRecentWeekCheckbox.isChecked()){
            myMostRecentWeekCheckbox.setError("More than one option is chosen");
        }

        if (foMostRecentWeekCheckbox.isChecked()){
            foMostRecentWeekCheckbox.setError("More than one option is chosen");
        }

        if (myDisplayAllCheckbox.isChecked()){
            myDisplayAllCheckbox.setError("More than one option is chosen");
        }
        if (foDisplayAllCheckbox.isChecked()){
            foDisplayAllCheckbox.setError("More than one option is chosen");
        }

        if(enteredMyReason != null && !enteredMyReason.isEmpty()){
            myReasonEditText.setError("More than one option is chosen");
        }

        if(enteredFoReason != null && !enteredFoReason.isEmpty()){
            foReasonEditText.setError("More than one option is chosen");
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            // save the record list to Json
            Gson gson = new Gson();
            gson.toJson(moodListAfterFilter, out);

            out.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
