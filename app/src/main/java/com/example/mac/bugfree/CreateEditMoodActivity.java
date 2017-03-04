package com.example.mac.bugfree;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;


public class CreateEditMoodActivity extends AppCompatActivity {

    //Test
    private String mood_state, social_situation, reason;
    private Date date;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_mood);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_create_edit);
        setSupportActionBar(toolbar);
        //ActionBar actionBar = getSupportActionBar();


        ImageView home_tab = (ImageView) findViewById(R.id.home_tab_add);
        home_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEditMoodActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        Spinner mood_state_spinner= (Spinner)findViewById(R.id.mood_state_spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.mood_states_array,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood_state_spinner.setAdapter(adapter);
        mood_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i)+" is selected.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        Button send_button = (Button) findViewById(R.id.send);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEditMoodActivity.this, MainActivity.class);
                startActivity(intent);


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

            case R.id.activity_filter:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            //TODO change icon

        }
        return super.onOptionsItemSelected(item);
    }
    public boolean add_location(){
        return true;
    }
    public boolean save_mood_list(){
        return true;
    }
    public boolean load_mood_list(){
        return true;
    }
}
