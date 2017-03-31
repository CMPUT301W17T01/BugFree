package com.example.mac.bugfree.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
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
import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * This activity is a sub class of CreateEditMoodActivity
 * Allow users to edit the mood events that they have already created
 * @author Mengyang Chen
 */
public class EditActivity extends CreateEditMoodActivity {

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private MoodEvent edit_mood_event;
    private String current_user, edit_mood_state, edit_social_situation, edit_trigger;
    private EditText edit_reason;
    private Spinner mood_state_spinner, social_situation_spinner;
    private DatePicker simpleDatePicker;
    private TimePicker simpleTimePicker;
    private CheckBox current_time_checkbox, currentLocationCheckbox;
    private ImageView pic_preview;
    private Uri imageFileUri;
    private ImageForElasticSearch imageForElasticSearch = null;
    private GeoPoint currentLocation;

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
        edit_reason = (EditText) findViewById(R.id.edit_reason);
        social_situation_spinner = (Spinner) findViewById(R.id.edit_social_situation_spinner);
        mood_state_spinner = (Spinner) findViewById(R.id.edit_mood_state_spinner);
        simpleDatePicker = (DatePicker) findViewById(R.id.datePicker);
        simpleTimePicker = (TimePicker) findViewById(R.id.timePicker);
        pic_preview = (ImageView) findViewById(R.id.pic_preview);
        current_time_checkbox = (CheckBox) findViewById(R.id.current_time);
        current_time_checkbox.setChecked(true);
        simpleTimePicker.setIs24HourView(true);
        currentLocationCheckbox = (CheckBox) findViewById(R.id.current_location);


        SharedPreferences sharedPreferences = getSharedPreferences("editMoodEvent", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("moodevent", "");
        edit_mood_event = gson.fromJson(json, MoodEvent.class);

        if (current_time_checkbox.isChecked()) {
            simpleDatePicker.setEnabled(false);
            simpleTimePicker.setEnabled(false);
        }


        adapter1 = ArrayAdapter.createFromResource(this, R.array.mood_states_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood_state_spinner.setAdapter(adapter1);
        mood_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    edit_mood_state = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(), edit_mood_state + " is selected.", Toast.LENGTH_SHORT).show();
                } else {
                    edit_mood_state = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adapter2 = ArrayAdapter.createFromResource(this, R.array.social_situation_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        social_situation_spinner.setAdapter(adapter2);
        social_situation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    edit_social_situation = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(getApplicationContext(), edit_social_situation + " is selected.", Toast.LENGTH_SHORT).show();
                } else {
                    edit_social_situation = null;
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
                if (edit_reason.getText().toString().split("\\s+").length > 3) {
                    edit_reason.setError("Only the first 3 words will be sent");
                } else {
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
                if(Build.VERSION.SDK_INT>=23)
                    simpleTimePicker.setEnabled(!current_time_checkbox.isChecked());
            }
        });

        currentLocationCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    permissionLocationRequest();
                }
                add_location();
            }
        });

        Calendar calendar = edit_mood_event.getDateOfRecord();

        simpleDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        set_year = simpleDatePicker.getYear();
                        set_month = simpleDatePicker.getMonth();
                        set_day = simpleDatePicker.getDayOfMonth();

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

    public void load_moodEvent(MoodEvent edit_mood_event) {

        edit_mood_state = edit_mood_event.getMoodState();
        mood_state_spinner.setSelection(getIndex(mood_state_spinner, edit_mood_event.getMoodState()));
        if (edit_mood_event.getSocialSituation() != null) {
            social_situation_spinner.setSelection(getIndex(social_situation_spinner, edit_mood_event.getSocialSituation()));
        }
        if (edit_mood_event.getTriggerText() != null) {
            edit_reason.setText(edit_mood_event.getTriggerText());
        }
        if (edit_mood_event.getLocation() != null) {
            currentLocationCheckbox.setChecked(true);
        }
        if (edit_mood_event.getPicId() != null) {
            Bitmap image = getImage(edit_mood_event);
            pic_preview.setImageBitmap(image);
        } else {
            pic_preview.setImageResource(R.drawable.umood);
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_mood, menu);

        return true;

    }

    //http://stackoverflow.com/questions/8769368/how-to-set-position-in-spinner
    private int getIndex(Spinner spinner, String myString) {

        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(myString)) {
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
                if (edit_mood_state == null) {
                    Toast.makeText(getApplicationContext(), "Choose a mood state", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    User user = new User();
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    current_user = pref.getString("currentUser", "");

                    // When the moodEvent has been edited, check for internet connection.
                    // If online, sync to Elastic search and save locally.
                    // If offline, save locally
                    InternetConnectionChecker checker = new InternetConnectionChecker();
                    Context context = getApplicationContext();
                    final boolean isOnline = checker.isOnline(context);

                    if (isOnline) {
                        String query = current_user;
                        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
                        getUserTask.execute(current_user);
                        try {
                            user = getUserTask.get();
                        } catch (Exception e) {
                            Log.i("Error", "Failed to get the User out of the async object");
                        }
                    } else {
                        LoadFile load = new LoadFile();
                        user = load.loadUser(context);
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putBoolean("hasBeenOffline", true);
                        editor.apply();
                    }

                    MoodEventList moodEventList = user.getMoodEventList();
                    moodEventList.deleteMoodEvent(edit_mood_event);

                    //TODO: delete offline
                    //if online/offline
                    if (edit_mood_event.getPicId() != null) {
                        if (isOnline) {
                            ElasticsearchImageController.DeleteImageTask deleteImageTask =
                                    new ElasticsearchImageController.DeleteImageTask();
                            deleteImageTask.execute(edit_mood_event.getPicId());
                            ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                            elasticsearchImageOfflineController.DeleteImageTask(context,edit_mood_event.getPicId());
                        } else {
                            ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                            elasticsearchImageOfflineController.DeleteImageTask(context,edit_mood_event.getPicId());
                        }
                        File file = context.getFileStreamPath(edit_mood_event.getPicId());
                        file.delete();

                    }
                    user.setMoodEventList(moodEventList);

                    if (isOnline) {
                        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
                        addUserTask.execute(user);
                        SaveFile s = new SaveFile(context, user);
                    } else {
                        SaveFile s = new SaveFile(context, user);
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putBoolean("hasBeenOffline", true);
                        editor.apply();
                    }

                    if (current_time_checkbox.isChecked()) {
                        dateOfRecord = real_time();
                    } else {
                        dateOfRecord = new GregorianCalendar(set_year, set_month + 1, set_day, set_hour, set_minute);
                    }
                    try {
                        setMoodEvent(current_user, edit_mood_state, edit_social_situation, edit_trigger, imageForElasticSearch, currentLocation);
                    } catch (MoodStateNotAvailableException e) {
                        Log.i("Error", "Failed to get the Mood state");
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
                }
                return true;

            case R.id.expanded_menu_gallery:
                if (ContextCompat.checkSelfPermission(EditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void takeAPhoto() {

        File folder = new File(getExternalCacheDir(), "output_img.jpg");
        try {
            if (folder.exists()) {
                folder.delete();
            }
            folder.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageFileUri = FileProvider.getUriForFile(EditActivity.this,
                    "com.example.mac.bugfree.fileprovider", folder);
        } else {
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
                if (resultCode == RESULT_OK) {
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
            //TODO
            case REQ_CODE_CHILD:
                if (resultCode == RESULT_OK){
                    Double lat = data.getDoubleExtra("chosenLocationLat",0);
                    Double lon = data.getDoubleExtra("chosenLocationLon",0);
                    currentLocation = new GeoPoint(lat, lon);
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
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
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

    private Bitmap getImage(MoodEvent moodEvent) {
        String uniqueId = moodEvent.getPicId();
        InternetConnectionChecker checker = new InternetConnectionChecker();
        Context context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);
//        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//        current_user = pref.getString("currentUser", "");


        if (isOnline) {
            ElasticsearchImageController.GetImageTask getImageTask = new ElasticsearchImageController.GetImageTask();
            getImageTask.execute(uniqueId);

            //imageForElasticSearch = new ImageForElasticSearch();

            try {
                imageForElasticSearch = getImageTask.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (imageForElasticSearch ==null){
                ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                imageForElasticSearch = elasticsearchImageOfflineController.GetImageTask(context,uniqueId);
            }

        } else if (uniqueId!=null){
            ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
            imageForElasticSearch = elasticsearchImageOfflineController.GetImageTask(context,uniqueId);
        }

        return imageForElasticSearch.base64ToImage();
    }


    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    private void permissionLocationRequest() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showMessageOKCancel("You need to allow access to Location",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQUEST_CODE_ASK_PERMISSIONS);
                                }
                            });
                }
            }

        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(EditActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void add_location() {
        if (currentLocationCheckbox.isChecked()) {
            try {
                CurrentLocation locationListener = new CurrentLocation();
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
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
    public void chooseLocation(View v) {
        if(currentLocationCheckbox.isChecked()){
            Toast.makeText(getApplicationContext(),"Sorry, You have already chosen CURRENT LOCATION.",Toast.LENGTH_LONG).show();
        } else {
            Intent child = new Intent(getApplicationContext(),ChooseLocationOnMapActivity.class);
            startActivityForResult(child, REQ_CODE_CHILD);
        }
    }

}