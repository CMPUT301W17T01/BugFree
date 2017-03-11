package com.example.mac.bugfree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Checkable;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static com.example.mac.bugfree.R.id.timePicker;

/**
 * @author Mengyang Chen
 */
public class CreateEditMoodActivity extends AppCompatActivity {

    //Test
    private String current_user, mood_state, social_situation, reason;
    private Date date = null;
    private boolean is_checked = false;
    private String test;
    private EditText create_edit_reason, create_edit_date;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;
    ImageView add_pic, home_tab;
    Spinner mood_state_spinner, social_situation_spinner;
    CheckBox current_time_checkbox;
    List<Integer> date_time_list = new ArrayList<>();
    GregorianCalendar dateOfRecord;
    GregorianCalendar realTime;
    DatePicker simpleDatePicker;
    TimePicker simpleTimePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_mood);


        create_edit_reason = (EditText)findViewById(R.id.create_edit_reason);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_edit);
        setSupportActionBar(toolbar);

        home_tab = (ImageView) findViewById(R.id.home_tab_add);
        social_situation_spinner= (Spinner)findViewById(R.id.social_situation);
        mood_state_spinner= (Spinner)findViewById(R.id.mood_state_spinner);
        add_pic = (ImageView)findViewById(R.id.add_picture);
        current_time_checkbox = (CheckBox)findViewById(R.id.current_time);
        simpleDatePicker = (DatePicker)findViewById(R.id.datePicker);
        simpleTimePicker = (TimePicker)findViewById(timePicker);
        realTime = real_time();

        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEditMoodActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

       add_pic.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Intent intent = new Intent(CreateEditMoodActivity.this, MainActivity.class);
               //startActivity(intent);
               //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               //startActivityForResult(i, RESULT_LOAD_IMAGE);
           }
       });


        adapter1 = ArrayAdapter.createFromResource(this,R.array.mood_states_array,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood_state_spinner.setAdapter(adapter1);
        mood_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    mood_state = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(),mood_state+" is selected.",Toast.LENGTH_SHORT).show();
                }
                else{
                    //TODO set tick to be disable
                    mood_state = null;
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
                    social_situation = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(), social_situation + " is selected.", Toast.LENGTH_SHORT).show();
                }
                else{
                    //TODO set tick to be disable
                    social_situation=null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        create_edit_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (create_edit_reason.getText().toString().split("\\s+").length>3){
                    create_edit_reason.setError("Only the first 3 words will be recorded");

                }
                else {
                    reason = create_edit_reason.getText().toString();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        current_time_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_time_checkbox.isChecked()) {
                    is_checked = true;
                    Toast.makeText(getApplicationContext(), "Use Current time ", Toast.LENGTH_LONG).show();
                }
                else{
                    //TODO add spinner for the select date and time
                }
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        simpleDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Log.d("Date", "Year=" + year + " Month=" + (month + 1) + " day=" + dayOfMonth);
            }
        });
        
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_mood, menu);

        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_tick:

                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                current_user = pref.getString("currentUser", "");
                //current_user  = "0John";
                if(mood_state == null){
                    Toast.makeText(getApplicationContext(), "Choose a mood state and a picture please", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    try {
                        setMoodEvent(current_user, mood_state, social_situation, reason, dateOfRecord);
                    } catch (MoodStateNotAvailableException e) {

                    }
                    setResult(RESULT_OK);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean add_location(){
        return true;
    }
    public boolean save_mood_list(String mood_state, String social_situation,String reason){
        return true;
    }
    public boolean load_mood_list(){
        return true;
    }


    public void setMoodEvent(String current_user, String mood_state, String social_situation, String reason, GregorianCalendar dateOfRecord)
            throws MoodStateNotAvailableException{
        User user = new User();


        String query = current_user;
        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        //Log.d("Text", user.getMoodEventList().getMoodEvent(1).getMoodState());
        MoodEvent moodEvent = new MoodEvent(mood_state, current_user);

        moodEvent.setSocialSituation(social_situation);
        moodEvent.setTriggerText(reason);

        moodEvent.setDateOfRecord(dateOfRecord);
        if(current_time_checkbox.isChecked()) {
           dateOfRecord = real_time();
        }
        moodEvent.setRealtime(realTime);

        MoodEventList moodEventList = user.getMoodEventList();
        moodEventList.addMoodEvent(moodEvent);

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);
    }
    private GregorianCalendar real_time(){
        GregorianCalendar current = new GregorianCalendar();
        date = current.getTime();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S");
        fmt.applyPattern("yyyy MM dd HH mm ss");
        try {
            date = fmt.parse(date.toString());
        } catch (ParseException e) {
            Log.i("error message", "");
        }
        test = fmt.format(date);
        String[] splited = test.split("\\s+");
        int year = 0, month = 0, day = 0, hour = 0, minute = 0, second = 0;
        try {
            year = Integer.parseInt(splited[0]);
        } catch (NumberFormatException nfe) {
        }
        try {
            month = Integer.parseInt(splited[1]);
        } catch (NumberFormatException nfe) {
        }
        try {
            day = Integer.parseInt(splited[2]);
        } catch (NumberFormatException nfe) {
        }
        try {
            hour = Integer.parseInt(splited[3]);
        } catch (NumberFormatException nfe) {
        }

        try {
            minute = Integer.parseInt(splited[4]);
        } catch (NumberFormatException nfe) {
        }
        try {
            second = Integer.parseInt(splited[5]);
        } catch (NumberFormatException nfe){
        }
        date_time_list.add(year);
        date_time_list.add(month);
        date_time_list.add(day);
        date_time_list.add(hour);
        date_time_list.add(minute);
        date_time_list.add(second);
        //GregorianCalendar dateOfRecord1 = new GregorianCalendar(fmt.format(date));
        dateOfRecord = new GregorianCalendar(date_time_list.get(0), date_time_list.get(1),
                date_time_list.get(2), date_time_list.get(3), date_time_list.get(4), date_time_list.get(5));
        return dateOfRecord;
    }
    protected void onStart(){
        super.onStart();
    }
}

