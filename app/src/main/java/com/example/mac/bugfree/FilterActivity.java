package com.example.mac.bugfree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;


public class FilterActivity extends AppCompatActivity {
    private Spinner myEmotionalStateSpinner;
    private CheckBox myMostRecentWeekCheckbox;
    private EditText myReasonEditText;
    private CheckBox myDisplayAllCheckbox;
    private Spinner foEmotionalStateSpinner;
    private CheckBox foMostRecentWeekCheckbox;
    private EditText foReasonEditText;
    private CheckBox foDisplayAllCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        TabHost tabHost = (TabHost)findViewById(R.id.filter_tabHost);

        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("Myself");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Following");

        //Tab Myself
        tab1.setIndicator("Myself");
        tab1.setContent(R.id.myself);

        tab2.setIndicator("Following");
        tab2.setContent(R.id.following);
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
//        //Tab Following
//        spec = host.newTabSpec("Following");
//        spec.setContent(R.id.following);
//        spec.setIndicator("Following");
//        host.addTab(spec);


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
                startActivity(new Intent(this, MainActivity.class));
                return true;
            //TODO change icon

        }
        return super.onOptionsItemSelected(item);
    }


}
