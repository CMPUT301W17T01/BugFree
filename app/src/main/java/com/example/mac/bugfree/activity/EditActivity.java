package com.example.mac.bugfree.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.exception.MoodStateNotAvailableException;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *This activity is a sub class of CreateEditMoodActivity
 *Allow users to edit the mood events that they have already created
 * @author Mengyang Chen
 */
public class EditActivity extends CreateEditMoodActivity {

    private MoodEvent edit_mood_event;
    private String current_user, edit_mood_state, edit_social_situation, edit_trigger;
    private EditText edit_reason;
    private Spinner mood_state_spinner, social_situation_spinner;
    private DatePicker simpleDatePicker;
    private TimePicker simpleTimePicker;
    private CheckBox current_time_checkbox;

    /**
     * onCreate begins from here
     * set the spinners, pickers and EditText, store them whenever changed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_edit);
        setSupportActionBar(toolbar);
        ArrayAdapter<CharSequence> adapter1;
        ArrayAdapter<CharSequence> adapter2;
        edit_reason = (EditText)findViewById(R.id.edit_reason);
        social_situation_spinner= (Spinner)findViewById(R.id.edit_social_situation_spinner);
        mood_state_spinner= (Spinner)findViewById(R.id.edit_mood_state_spinner);
        simpleDatePicker = (DatePicker)findViewById(R.id.datePicker);
        simpleTimePicker = (TimePicker)findViewById(R.id.timePicker);
        current_time_checkbox = (CheckBox) findViewById(R.id.current_time);
        current_time_checkbox.setChecked(true);
        simpleTimePicker.setIs24HourView(true);


        SharedPreferences sharedPreferences =getSharedPreferences("editMoodEvent", MODE_PRIVATE);
        Gson gson =new Gson();
        String json = sharedPreferences.getString("moodevent","");
        edit_mood_event = gson.fromJson(json,MoodEvent.class);



        if(current_time_checkbox.isChecked()){
            simpleDatePicker.setEnabled(false);
            simpleTimePicker.setEnabled(false);
        }

        adapter1 = ArrayAdapter.createFromResource(this,R.array.mood_states_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood_state_spinner.setAdapter(adapter1);
        mood_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    edit_mood_state = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(),edit_mood_state+" is selected.",Toast.LENGTH_SHORT).show();
                }
                else{
                    edit_mood_state = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter2 = ArrayAdapter.createFromResource(this,R.array.social_situation_array,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        social_situation_spinner.setAdapter(adapter2);
        social_situation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i>0) {
                    edit_social_situation = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(), edit_social_situation + " is selected.", Toast.LENGTH_SHORT).show();
                }
                else{
                    edit_social_situation=null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edit_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit_reason.getText().toString().split("\\s+").length>3){
                    edit_reason.setError("Only the first 3 words will be sent");
                }
                else {
                    edit_trigger = edit_reason.getText().toString();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        current_time_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDatePicker.setEnabled(!current_time_checkbox.isChecked());
                simpleTimePicker.setEnabled(!current_time_checkbox.isChecked());
            }
        });

        Calendar calendar = edit_mood_event.getDateOfRecord();

        simpleDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        set_year = simpleDatePicker.getYear();
                        set_month = simpleDatePicker.getMonth();
                        set_day =  simpleDatePicker.getDayOfMonth();

                    }
                });
        simpleTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                set_hour = simpleTimePicker.getHour();
                set_minute = simpleTimePicker.getMinute();
            }
        });
        load_moodEvent(edit_mood_event);


    }
    /**
     * local functions that allow users to load the mood events
     */

    public void load_moodEvent(MoodEvent edit_mood_event ){

        mood_state_spinner.setSelection(getIndex(mood_state_spinner, edit_mood_event.getMoodState()));
        if(edit_mood_event.getSocialSituation() != null){
            social_situation_spinner.setSelection(getIndex(social_situation_spinner, edit_mood_event.getSocialSituation()));
        }
        if(edit_mood_event.getTriggerText()!=null){
            edit_reason.setText(edit_mood_event.getTriggerText());
        }

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_mood, menu);

        return true;

    }
    //http://stackoverflow.com/questions/8769368/how-to-set-position-in-spinner
    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_tick:

                User user = new User();
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                current_user = pref.getString("currentUser", "");
                ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
                getUserTask.execute(current_user);
                try{
                    user = getUserTask.get();
                } catch (Exception e) {
                    Log.i("Error", "Failed to get the User out of the async object");
                }
                MoodEventList moodEventList = user.getMoodEventList();
                moodEventList.deleteMoodEvent(edit_mood_event);
                user.setMoodEventList(moodEventList);
                ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
                addUserTask.execute(user);
                    if(edit_mood_state == null){
                        Toast.makeText(getApplicationContext(), "Choose a mood state", Toast.LENGTH_SHORT).show();
                        break;
                   }
                    else {
                        if(current_time_checkbox.isChecked()) {
                            dateOfRecord = real_time();

                        } else {
                            dateOfRecord = new GregorianCalendar(set_year, set_month+1, set_day, set_hour, set_minute);

                        }

                        try {
                           setMoodEvent(current_user, edit_mood_state, edit_social_situation, edit_trigger);
                        } catch (MoodStateNotAvailableException e) {

                    }
                    setResult(RESULT_OK);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

}
