package com.example.mac.bugfree;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

/**
 * @author Mengyang Chen
 */
public class CreateEditMoodActivity extends AppCompatActivity {

    //Test
    private String current_user, mood_state, social_situation, reason;
    private Date date;

    EditText create_edit_mood, create_edit_reason, create_edit_date;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<CharSequence> adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_mood);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        //current_user = pref.getString("currentUser","");


        create_edit_reason = (EditText)findViewById(R.id.create_edit_reason);
        create_edit_mood = (EditText)findViewById(R.id.create_edit_mood);
        create_edit_reason = (EditText)findViewById(R.id.create_edit_date);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_edit);
        setSupportActionBar(toolbar);

        ImageView home_tab = (ImageView) findViewById(R.id.home_tab_add);
        Spinner mood_state_spinner= (Spinner)findViewById(R.id.mood_state_spinner);
        final Spinner social_situation_spinner= (Spinner)findViewById(R.id.social_situation);

        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEditMoodActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        create_edit_mood.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        create_edit_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int spaces = create_edit_mood.getText().toString().length() -
                        create_edit_mood.getText().toString().replace(" ", "").length();
                Toast.makeText(getApplicationContext(),spaces, Toast.LENGTH_SHORT).show();
                if (spaces>3){
                    create_edit_reason.setError("No more than 3 words!");
                }
                //TODO store the reason and limit the letters to 3
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                //TODO Save the creates & changes
                current_user  = "John";
                try {
                    setMoodEvent(current_user, mood_state, social_situation);
                }catch (MoodStateNotAvailableException e){

                }
                setResult(RESULT_OK);
                finish();

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


    public void setMoodEvent(String current_user, String mood_state, String social_situation) throws MoodStateNotAvailableException{
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
        try {
            moodEvent.setSocialSituation(social_situation);
        } catch (InvalidSSException e){}
        MoodEventList moodEventList = user.getMoodEventList();
        moodEventList.addMoodEvent(moodEvent);

        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
        addUserTask.execute(user);
    }
    protected void onStart(){
        super.onStart();
    }
}

