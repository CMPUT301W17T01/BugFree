package com.example.mac.bugfree.activity;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.exception.MoodStateNotAvailableException;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.util.CurrentLocation;

import org.osmdroid.util.GeoPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.mac.bugfree.R.id.login_button;
import static com.example.mac.bugfree.R.id.timePicker;

/**
 * This class allow users to create a new mood event
 *
 * @author Mengyang Chen
 */
public class CreateEditMoodActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private String current_user, mood_state , social_situation, reason;
    private Date date = null;
    public  int set_year = 0, set_month = 0, set_day = 0, set_hour, set_minute;
    private String test;
    private EditText create_edit_reason;
    private ImageView pic_preview, home_tab, earth_tab;
    private Spinner mood_state_spinner, social_situation_spinner;
    private CheckBox current_time_checkbox, currentLocationCheckbox;
    public GregorianCalendar dateOfRecord;
    private DatePicker simpleDatePicker;
    private TimePicker simpleTimePicker;
    private GeoPoint currentLocation;


    /**
     * onCreate begins from here
     * set the spinners, pickers and EditText, store them whenever changed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_mood);

        ArrayAdapter<CharSequence> adapter1;
        ArrayAdapter<CharSequence> adapter2;
        create_edit_reason = (EditText)findViewById(R.id.create_edit_reason);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_edit);
        setSupportActionBar(toolbar);
        home_tab = (ImageView) findViewById(R.id.home_tab_add);
        earth_tab = (ImageView) findViewById(R.id.earth_tab_add);
        social_situation_spinner= (Spinner)findViewById(R.id.social_situation);
        mood_state_spinner= (Spinner)findViewById(R.id.mood_state_spinner);
        pic_preview = (ImageView)findViewById(R.id.pic_preview);
        current_time_checkbox = (CheckBox)findViewById(R.id.current_time);
        simpleDatePicker = (DatePicker)findViewById(R.id.datePicker);
        simpleTimePicker = (TimePicker)findViewById(timePicker);
        simpleTimePicker.setIs24HourView(true);
        current_time_checkbox.setChecked(true);

        currentLocationCheckbox = (CheckBox) findViewById(R.id.current_location);


        if(current_time_checkbox.isChecked()){
            simpleDatePicker.setEnabled(false);
            simpleTimePicker.setEnabled(false);
        }

        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        earth_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        

        //TODO allow user to add picture in part5
//       add_pic.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               add_pic.setImageResource(R.drawable.picture_text);
//               //Intent intent = new Intent(CreateEditMoodActivity.this, MainActivity.class);
//               //startActivity(intent);
//               //Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//               //startActivityForResult(i, RESULT_LOAD_IMAGE);
//           }
//       });

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
                simpleDatePicker.setEnabled(!current_time_checkbox.isChecked());
                simpleTimePicker.setEnabled(!current_time_checkbox.isChecked());
            }
        });

        currentLocationCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionLocationRequest();
                add_location();

            }
        });


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
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
        
    }

    /**
     * Set the tool bar
     * tick on right up corner
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_edit_mood, menu);

        return true;

    }
    /**
     * whenever the right up corner's tick is clicked
     * get the real_time() use as the "ID"
     * call the setMoodEvent function
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_tick:

                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                current_user = pref.getString("currentUser", "");
                
                if(mood_state == null){
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
                        setMoodEvent(current_user, mood_state, social_situation, reason);
                    } catch (MoodStateNotAvailableException e) {

                    }
                    setResult(RESULT_OK);
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO add location in part 5
    public void add_location(){
        if (currentLocationCheckbox.isChecked()) {
            try {
                CurrentLocation locationListener = new CurrentLocation();
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if( location != null ) {
                    int latitude = (int) (location.getLatitude() * 1E6);
                    int longitude = (int) (location.getLongitude() * 1E6);
                    currentLocation =  new GeoPoint(latitude, longitude);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean save_mood_list(String mood_state, String social_situation,String reason){
        return true;
    }

    /**
     * pass the data in
     * @param current_user
     * @param mood_state
     * @param social_situation
     * @param reason
     * set the mood event and push it to online server
     * @throws MoodStateNotAvailableException
     */
    public void setMoodEvent(String current_user, String mood_state, String social_situation, String reason)
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

        MoodEvent moodEvent = new MoodEvent(mood_state, current_user);

        moodEvent.setSocialSituation(social_situation);
        moodEvent.setTriggerText(reason);

        moodEvent.setRealtime(real_time());
        moodEvent.setDateOfRecord(dateOfRecord);

        // Test for the location
        //add_location();
        if (currentLocation != null) {
            moodEvent.setLocation(currentLocation);
        }

        MoodEventList moodEventList = user.getMoodEventList();
        moodEventList.addMoodEvent(moodEvent);

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);
    }

    /**
     * This class allow the user to get the real time
     * @return time
     */
    public GregorianCalendar real_time(){
        GregorianCalendar time;
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
            Log.i("Error message","NumberFormatException");
        }
        try {
            month = Integer.parseInt(splited[1]);
        } catch (NumberFormatException nfe) {
            Log.i("Error message","NumberFormatException");
        }
        try {
            day = Integer.parseInt(splited[2]);
        } catch (NumberFormatException nfe) {
            Log.i("Error message","NumberFormatException");
        }
        try {
            hour = Integer.parseInt(splited[3]);
        } catch (NumberFormatException nfe) {
            Log.i("Error message","NumberFormatException");
        }
        try {
            minute = Integer.parseInt(splited[4]);
        } catch (NumberFormatException nfe) {
            Log.i("Error message","NumberFormatException");
        }
        try {
            second = Integer.parseInt(splited[5]);
        } catch (NumberFormatException nfe){
            Log.i("Error message","NumberFormatException");
        }

        time = new GregorianCalendar(year, month, day, hour, minute, second);

        return time;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        add_location();
    }

    public void onResume() {
        super.onResume();
        org.osmdroid.config.Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

    }

    private void permissionLocationRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showMessageOKCancel("You need to allow access to Location",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                }
            }

        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(CreateEditMoodActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}

