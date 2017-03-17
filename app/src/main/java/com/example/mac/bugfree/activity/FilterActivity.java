package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
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

import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Arrays;

/**
 * This class is aim to provides 8 filter options for user to choose. When the user select one option, it will
 * filter the moodevent list which get from the elasticsearch, and store the mood event list into a "filter.sav"
 * Gson file after finishing filtering.<br> In this class,
 * user interaction and file manipulation is performed.
 * All files are in the form of "json" files that are stored in Emulator's
 * accessible from Android Device Monitor:
 * <pre>
 *     pre-formatted text: <br>
 *         File Explorer -> data -> data -> ca.ualberta.cs.lonelytwitter -> files -> filter.sav.
 * </pre>
 * <code> begin <br>
 * some pseduo code here <br>
 * end.</code>
 * The file name is indicated in the &nbsp &nbsp &nbsp FILENAME constant.
 *
 * @author Heyue Huang
 * @version 1.4.2
 * @since 1.0
 */
public class FilterActivity extends AppCompatActivity {

    // gson file initial
    private FilterActivity activity = this;
    private static final String FILENAME = "filter.sav";
    // UI for myself and following initial set up
    private Spinner myEmotionalStateSpinner;
    private CheckBox myMostRecentWeekCheckbox;
    private EditText myReasonEditText;
    private CheckBox myDisplayAllCheckbox;
    private Spinner foEmotionalStateSpinner;
    private CheckBox foMostRecentWeekCheckbox;
    private EditText foReasonEditText;
    private CheckBox foDisplayAllCheckbox;
    private ArrayAdapter<CharSequence> adapter;
    // store the chosen content
    private String selectedMyMoodState;
    private String selectedFoMoodState;
    private String enteredMyReason;
    private String enteredFoReason;
    // store the num of chosen options
    private int flag;
    // store the moodevent list get from elasticsearch
    private ArrayList<String> followeeList;
    private MoodEventList moodListBeforeFilterMy = new MoodEventList();
    private MoodEventList moodListBeforeFilterFo = new MoodEventList();
    // store the mood event list after filtering
    private ArrayList<MoodEvent> moodListAfterFilter = new ArrayList<>();
    // store the date, mood state, and the reason of each mood in the list
    private Calendar currentDATE;
    private Calendar lowerBoundDATE;
    private Calendar dateOfMood;
    private String stateOfMood;
    private String keyOfReason;
    public ArrayList<MoodEvent> getMoodListAfterFilter(){
        return moodListAfterFilter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        // get current user's mood event list
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String current_user = pref.getString("currentUser", "");
        User user = new User();
        String query = current_user;
        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        // store the user's mood event list and followee id list
        moodListBeforeFilterMy = user.getMoodEventList();
        followeeList  = user.getFolloweeIDs();

        // get current user following people's mood event list
        ElasticsearchUserController.GetUserTask getUserTask1 = new ElasticsearchUserController.GetUserTask();

        for  (String followee : followeeList) {
            getUserTask1 = new  ElasticsearchUserController.GetUserTask();
            getUserTask1.execute(followee);
            try {
                User user_follow = getUserTask1.get();
                // store the user's following people's mood event list
                moodListBeforeFilterFo.addMoodEventList(user_follow.getMoodEventList());
            } catch (Exception e) {
                //Log.i("Error", "Failed to get the User out of the async object");
            }
        }

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
        // Take from http://stackoverflow.com/questions/16204372/the-type-new-adapterview-onitemselectedlistener-must-implement-the-inherited
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

        // Taken from http://stackoverflow.com/questions/19726115/comparing-user-input-date-with-current-date
        // set date 's format
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        // current time
        currentDATE = Calendar.getInstance();
        currentDATE.add(Calendar.MONTH, 1);
        // one week ago
        lowerBoundDATE = Calendar.getInstance();
        lowerBoundDATE.add(Calendar.MONTH, 1);
        lowerBoundDATE.add(Calendar.DATE, -6);

        // set up the tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);
        // Taken from http://www.androidhive.info/2015/09/android-material-design-working-with-tabs/
        // set up the tab host
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

    // Taken from http://stackoverflow.com/questions/35648913/how-to-set-menu-to-toolbar-in-android
    // combine the menu and the layout
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        return true;
    }

    // Taken from http://stackoverflow.com/questions/7479992/handling-a-menu-item-click-event-android
    // Determines if Action bar item was selected. If true then do corresponding action.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle presses on the action bar items
        switch (item.getItemId()) {
            // if the item in tool bar is selected
            case R.id.activity_filter:
                // check which option is selected
                checkWhichIsChosen();
                // if there are more than one options are selected, then braek
                if(flag > 1){
                    Toast.makeText(this,"Warning: More than one option is chosen" ,Toast.LENGTH_LONG).show();
                    setErrorMessages();
                    break;
                }
                // if there is only one option is selected, then save the mood event list in the file
                if(flag == 1){
                    saveInFile();
                }
                // if no option is selected, then delete the file.
                if (flag == 0){
                    deleteFile("filter.sav");
                }
                // jump to main activity
                startActivity(new Intent(this, MainActivity.class));
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Check which is option is chosen,
     * if one option is selected, then flag plus one
     */
    public void checkWhichIsChosen(){
        // reset all initial set up
        moodListAfterFilter.clear();
        deleteFile("filter.sav");
        flag = 0;
        // key of reason and moos state which is entered by user
        enteredMyReason = myReasonEditText.getText().toString();
        enteredFoReason = foReasonEditText.getText().toString();
        selectedMyMoodState = myEmotionalStateSpinner.getSelectedItem().toString();
        selectedFoMoodState = foEmotionalStateSpinner.getSelectedItem().toString();
        // if Myself Mood state is selected, then jump to its filter function
        if(selectedMyMoodState != null && !selectedMyMoodState.isEmpty()){
            filterByMyMoodState(selectedMyMoodState);
            flag ++;
        }
        // if Following Mood state is selected, then jump to its filter function
        if(selectedFoMoodState != null && !selectedFoMoodState.isEmpty()){
            filterByFoMoodState(selectedFoMoodState);
            flag ++;
        }
        // if Myself most recent week is selected, then jump to its filter function
        if (myMostRecentWeekCheckbox.isChecked()){
            filterByMyMostRece();
            flag ++;
        }
        // if Following most recent week is selected, then jump to its filter function
        if (foMostRecentWeekCheckbox.isChecked()){
            filterByFoMostRece();
            flag ++;
        }
        // if Myself display all is selected, then jump to its filter function
        if (myDisplayAllCheckbox.isChecked()){
            filterByMyDisplayAll();
            flag ++;
        }
        // if Following display all is selected, then jump to its filter function
        if (foDisplayAllCheckbox.isChecked()){
            filterByFoDisplayAll();
            flag ++;
        }
        // if Myself key of reason is entered, then jump to its filter function
        if(enteredMyReason != null && !enteredMyReason.isEmpty()){
            filterByMyReason(enteredMyReason);
            flag ++;
        }
        // if Following key of reason is entered, then jump to its filter function
        if(enteredFoReason != null && !enteredFoReason.isEmpty()){
            filterByFoReason(enteredFoReason);
            flag ++;
        }
    }

    /**
     * Filter current user's own mood events by most recent week.
     */
    public void filterByMyMostRece() {
        // for each mood event in the list
        for (int i = 0; i < moodListBeforeFilterMy.getCount(); i++ ){
            // get the mood's date
            dateOfMood = moodListBeforeFilterMy.getMoodEvent(i).getDateOfRecord();
            // if it within the range, then add it to the new list
            if (dateOfMood.compareTo(lowerBoundDATE) >= 0 && dateOfMood.compareTo(currentDATE) <= 0) {
                moodListAfterFilter.add(moodListBeforeFilterMy.getMoodEvent(i));
            }
        }
    }
    /**
     * Filter current user's all mood events.
     */
    public void filterByMyDisplayAll(){
        // add them into the new list
        for (int i = 0; i < moodListBeforeFilterMy.getCount(); i++ ){
            moodListAfterFilter.add(moodListBeforeFilterMy.getMoodEvent(i));
        }
    }
    /**
     * Filter current user's following people's mood events by most recent week.
     */
    public void filterByFoMostRece(){
        // for each mood event in the list
        for (int i = 0; i < moodListBeforeFilterFo.getCount(); i++ ){
            // get the mood's date
            dateOfMood = moodListBeforeFilterFo.getMoodEvent(i).getDateOfRecord();
            // if it within the range, then add it to the new list
            if (dateOfMood.compareTo(lowerBoundDATE) >= 0 && dateOfMood.compareTo(currentDATE) <= 0) {
                moodListAfterFilter.add(moodListBeforeFilterFo.getMoodEvent(i));
            }
        }
    }
    /**
     * Filter current user's following people's all mood events.
     */
    public void filterByFoDisplayAll(){
        // add them into the new list
        for (int i = 0; i < moodListBeforeFilterFo.getCount(); i++ ){
            moodListAfterFilter.add(moodListBeforeFilterFo.getMoodEvent(i));
        }
    }
    /**
     * Filter current user's mood events by a specific mood state.
     *
     * @param selectedMoodState the mood state which user select in the Filter page
     */
    public void filterByMyMoodState(String selectedMoodState){
        // for each mood event in the list
        for (int i = 0; i < moodListBeforeFilterMy.getCount(); i++ ){
            // get the mood event's mood state
            stateOfMood = moodListBeforeFilterMy.getMoodEvent(i).getMoodState();
            // if it equals the selected mood state, then add it to the new list
            if (stateOfMood.equals(selectedMoodState)) {
                moodListAfterFilter.add(moodListBeforeFilterMy.getMoodEvent(i));
            }
        }
    }
    /**
     * Filter current user's following people's mood events by a specific mood state.
     *
     * @param selectedMoodState the mood state which user select in the Filter page
     */
    public void filterByFoMoodState(String selectedMoodState){
        // for each mood event in the list
        for (int i = 0; i < moodListBeforeFilterFo.getCount(); i++ ){
            // get the mood event's mood state
            stateOfMood = moodListBeforeFilterFo.getMoodEvent(i).getMoodState();
            // if it equals the selected mood state, then add it to the new list
            if (stateOfMood.equals(selectedMoodState)) {
                moodListAfterFilter.add(moodListBeforeFilterFo.getMoodEvent(i));
            }
        }
    }
    /**
     * Filter current user's mood events by a specific key of reason.
     *
     * @param enteredReason the entered key of reason
     */
    public void filterByMyReason(String enteredReason){
        // for each mood event in the list
        for (int i = 0; i < moodListBeforeFilterMy.getCount(); i++ ){
            // get the mood event's trigger text
            keyOfReason = moodListBeforeFilterMy.getMoodEvent(i).getTriggerText();
            // if it contains the entered key of reason, then add it to the new list
            if (keyOfReason != null && keyOfReason.toLowerCase().contains(enteredReason.toLowerCase())) {
                moodListAfterFilter.add(moodListBeforeFilterMy.getMoodEvent(i));
            }
        }
    }
    /**
     * Filter current user's following people's mood events by a specific key of reason.
     *
     * @param enteredReason the entered key of reason
     */
    public void filterByFoReason(String enteredReason){
        // for each mood event in the list
        for (int i = 0; i < moodListBeforeFilterFo.getCount(); i++ ){
            // get the mood event's trigger text
            keyOfReason = moodListBeforeFilterFo.getMoodEvent(i).getTriggerText();
            // if it contains the entered key of reason, then add it to the new list
            if (keyOfReason != null && keyOfReason.toLowerCase().contains(enteredReason.toLowerCase())) {
                moodListAfterFilter.add(moodListBeforeFilterFo.getMoodEvent(i));
            }
        }
    }

    /**
     * Set error messages when there is more than option is selected.
     */
    public void setErrorMessages(){
        // if mood state in myself tab is chosen
        if(selectedMyMoodState != null && !selectedMyMoodState.isEmpty()){
            ((TextView)myEmotionalStateSpinner.getSelectedView()).setError("More than one option is chosen");
        }
        // if mood state in following tab is chosen
        if(selectedFoMoodState != null && !selectedFoMoodState.isEmpty()){
            ((TextView)foEmotionalStateSpinner.getSelectedView()).setError("More than one option is chosen");
        }
        // if most recent week in myself tab is chosen
        if (myMostRecentWeekCheckbox.isChecked()){
            myMostRecentWeekCheckbox.setError("More than one option is chosen");
        }
        // if most recent week in foloowing tab is chosen
        if (foMostRecentWeekCheckbox.isChecked()){
            foMostRecentWeekCheckbox.setError("More than one option is chosen");
        }
        // if display all in myself tab is chosen
        if (myDisplayAllCheckbox.isChecked()){
            myDisplayAllCheckbox.setError("More than one option is chosen");
        }
        // if display all in following tab is chosen
        if (foDisplayAllCheckbox.isChecked()){
            foDisplayAllCheckbox.setError("More than one option is chosen");
        }
        // if reason in myself tab is chosen
        if(enteredMyReason != null && !enteredMyReason.isEmpty()){
            myReasonEditText.setError("More than one option is chosen");
        }
        // if reason in following tab is chosen
        if(enteredFoReason != null && !enteredFoReason.isEmpty()){
            foReasonEditText.setError("More than one option is chosen");
        }
    }

    // Taken from LonelyTwiiter lab
    /**
     * Save the filtered mood event list into "filter.sav"
     */
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
