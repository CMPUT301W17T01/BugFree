package com.example.mac.bugfree.activity;


import android.Manifest;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
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

import com.example.mac.bugfree.controller.ElasticsearchImageController;
import com.example.mac.bugfree.controller.ElasticsearchImageOfflineController;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.Image;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.exception.MoodStateNotAvailableException;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.util.CurrentLocation;
import com.example.mac.bugfree.util.InternetConnectionChecker;
import com.example.mac.bugfree.util.LoadFile;
import com.example.mac.bugfree.util.SaveFile;

import org.osmdroid.util.GeoPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.example.mac.bugfree.R.id.timePicker;

/**
 * This class allow users to create a new mood event
 *
 * @author Mengyang Chen
 */
public class CreateEditMoodActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    /**
     * The constant TAKE_PHOTO.
     */
    public static final int TAKE_PHOTO = 1;
    /**
     * The constant CHOOSE_PHOTO.
     */
    public static final int CHOOSE_PHOTO = 2;
    /**
     * The constant REQ_CODE_CHILD.
     */
    public final static int REQ_CODE_CHILD = 233;


    private String current_user, mood_state , social_situation, reason;
    private Date date = null;
    /**
     * The Set year.
     */
    public  int set_year = 0, /**
     * The Set month.
     */
    set_month = 0, /**
     * The Set day.
     */
    set_day = 0, /**
     * The Set hour.
     */
    set_hour, /**
     * The Set minute.
     */
    set_minute;
    private String test;
    private EditText create_edit_reason;
    private ImageView pic_preview, home_tab, earth_tab;
    private CheckBox current_time_checkbox, currentLocationCheckbox;
    /**
     * The Date of record.
     */
    public GregorianCalendar dateOfRecord;
    private DatePicker simpleDatePicker;
    private TimePicker simpleTimePicker;
    private Uri imageFileUri;
    private GeoPoint currentLocation;
    private ImageForElasticSearch imageForElasticSearch = null;

    /**
     * onCreate begins from here
     * set the spinners, pickers and EditText, store them locally whenever something type in or spinner item been selected
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
        Spinner social_situation_spinner= (Spinner)findViewById(R.id.social_situation);
        Spinner mood_state_spinner= (Spinner)findViewById(R.id.mood_state_spinner);
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
                InternetConnectionChecker checker = new InternetConnectionChecker();
                Context context = getApplicationContext();
                final boolean isOnline = checker.isOnline(context);
                if(isOnline) {
                    setResult(RESULT_OK);
                    finish();
                } else{
                    Toast.makeText(getApplicationContext(), "Map is not available when this device is offline.", Toast.LENGTH_LONG).show();
                }
            }
        });

        pic_preview.setImageResource(R.drawable.umood);
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
                if(Build.VERSION.SDK_INT>=23)
                    simpleTimePicker.setEnabled(!current_time_checkbox.isChecked());
            }
        });

        currentLocationCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetConnectionChecker checker = new InternetConnectionChecker();
                Context context = getApplicationContext();
                final boolean isOnline = checker.isOnline(context);
                if(!isOnline) {
                    currentLocationCheckbox.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Location is not available when this device is offline.", Toast.LENGTH_LONG).show();
                }
                if(isOnline) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        permissionLocationRequest();
                    }
                }

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
        /**
         * API need to be greater than or equal to 23 to use the getHour() and getMinute()
         */
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

                if (mood_state == null) {
                    Toast.makeText(getApplicationContext(), "Choose a mood state", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if (current_time_checkbox.isChecked()) {
                        dateOfRecord = real_time();

                    } else {
                        dateOfRecord = new GregorianCalendar(set_year, set_month + 1, set_day, set_hour, set_minute);

                    }

                    try {
                        setMoodEvent(current_user, mood_state, social_situation, reason, imageForElasticSearch,currentLocation);
                    } catch (MoodStateNotAvailableException e) {
                        Log.i("Error", "(MoodState is Not Available");
                    }

                    setResult(RESULT_OK);
                    finish();

                }
                return true;
            
            case R.id.expanded_menu_camera:

                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 12345);
                    } else {
                        takeAPhoto();
                    }
                } else {
                    takeAPhoto();
                }
                return true;

            case R.id.expanded_menu_gallery:
                if (ContextCompat.checkSelfPermission(CreateEditMoodActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateEditMoodActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Using GPS to add current location.
     */
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
                    currentLocation = new GeoPoint(latitude, longitude);
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            currentLocation = null;
        }
    }

    /**
     * pass the data in
     *
     * @param current_user          the current user
     * @param mood_state            the mood state
     * @param social_situation      the social situation
     * @param reason                set the mood event and push it to online server
     * @param imageForElasticSearch the image for elastic search
     * @param currLocation          the curr location
     * @throws MoodStateNotAvailableException the mood state not available exception
     */
    public void setMoodEvent(String current_user, String mood_state, String social_situation, String reason, ImageForElasticSearch imageForElasticSearch, GeoPoint currLocation)
            throws MoodStateNotAvailableException{
        User user = new User();

        // When the moodEvent has been created, check for internet connection.
        // If online, sync to Elastic search and save locally.
        // If offline, save locally
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);

        if(isOnline) {
            String query = current_user;
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(query);
            try {
                user = getUserTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        } else{
            LoadFile load = new LoadFile();
            user = load.loadUser(context);
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putBoolean("hasBeenOffline", true);
            editor.apply();
        }

        MoodEvent moodEvent = new MoodEvent(mood_state, current_user);

        moodEvent.setSocialSituation(social_situation);
        moodEvent.setTriggerText(reason);

        GregorianCalendar realT = real_time();
        moodEvent.setRealtime(realT);
        moodEvent.setDateOfRecord(dateOfRecord);

        if (currLocation != null) {
            moodEvent.setLocation(currLocation);
        }

        if (imageForElasticSearch != null) {
            String uniqueID = realT.getTime().toString().replaceAll("\\s", "") + current_user;
            uniqueID = uniqueID.replaceAll(":","");
            moodEvent.setPicId(uniqueID);
            if (isOnline){
                uploadImage(imageForElasticSearch, uniqueID);
                ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                elasticsearchImageOfflineController.AddImageTask(context,imageForElasticSearch.getImageBase64(),uniqueID,null);
            }else {
                ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                elasticsearchImageOfflineController.AddImageTask(context,imageForElasticSearch.getImageBase64(),uniqueID,null);
            }
        }

        MoodEventList moodEventList = user.getMoodEventList();
        moodEventList.addMoodEvent(moodEvent);


        if(isOnline) {
            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
            addUserTask.execute(user);
            SaveFile s = new SaveFile(context, user);
        } else{
            SaveFile s = new SaveFile(context, user);
            SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
            editor.putBoolean("hasBeenOffline", true);
            editor.apply();
        }

    }

    /**
     * This class allow the user to get the real time
     *
     * @return time gregorian calendar
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


    /**
     * Enter the camera to take photo
     */
    private void takeAPhoto() {

        File folder = new File(getExternalCacheDir(), "output_img.jpg");
        try {
            if (folder.exists()){
                folder.delete();
            }
            folder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageFileUri = FileProvider.getUriForFile(CreateEditMoodActivity.this,
                    "com.example.mac.bugfree.fileprovider", folder);
        }
        else {

            imageFileUri = Uri.fromFile(folder);
        }

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(intent, TAKE_PHOTO);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 12345:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takeAPhoto();
                    // Now user should be able to use camera
                } else {
                    // Your app will not have this permission. Turn off all functions
                    // that require this permission or it will force close like your
                    // original question
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK){
                    try {
                        Bitmap bitmap = BitmapFactory.
                                decodeStream(getContentResolver().openInputStream(imageFileUri));
                        pic_preview.setImageBitmap(bitmap);
                        Image image = new Image(bitmap);

                        imageForElasticSearch = new
                                ImageForElasticSearch(image.getImageBase64());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            // when go back to this intent
            case REQ_CODE_CHILD:
                if (resultCode == RESULT_OK){
                    // get the point
                    Double lat = data.getDoubleExtra("chosenLocationLat",0);
                    Double lon = data.getDoubleExtra("chosenLocationLon",0);
                    String mess = data.getStringExtra("flag");
                    if (mess == null){
                        currentLocation = new GeoPoint(lat, lon);
                    } else {
                        currentLocation = null;
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" +id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.
                        parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * get the image path of album or camera
     *
     * @param uri uri
     * @param selection selected position
     * @return path
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * Get the image path and set the picture preview as selected images
     * @param imagePath imagepath
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            Image image = new Image(bitmap);
            imageForElasticSearch = new ImageForElasticSearch(image.getImageBase64());
            pic_preview.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * allow users to open the album and select photos from gallery
     */
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Grand the gps permission
     */
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

    /**
     * set the image and push it to online server
     * @param ifes: ifes
     * @param uniqueId: unique picture id
     */
    private void uploadImage (ImageForElasticSearch ifes, String uniqueId){
        ifes.setUniqueId(uniqueId);

        ElasticsearchImageController.AddImageTask addImageTask = new ElasticsearchImageController.AddImageTask();
        addImageTask.execute(ifes);
    }

    /**
     * When the Location TextView is chosen, if it is online then change to the map,
     * if it is offline then send the warning message
     *
     * @param v the view
     */
    public void chooseLocation(View v) {
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);

        if(isOnline) {
            if (currentLocationCheckbox.isChecked()) {
                Toast.makeText(getApplicationContext(), "Sorry, You have already chosen CURRENT LOCATION.", Toast.LENGTH_LONG).show();
            } else {
                Intent child = new Intent(getApplicationContext(), ChooseLocationOnMapActivity.class);
                startActivityForResult(child, REQ_CODE_CHILD);
            }
        } else{
            Toast.makeText(getApplicationContext(), "Map is not available when this device is offline.", Toast.LENGTH_LONG).show();
        }
    }

}

