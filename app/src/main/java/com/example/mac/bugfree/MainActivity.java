package com.example.mac.bugfree;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static junit.framework.Assert.assertTrue;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    // Test CardView
    private MoodEventList moodEventArrayList = new MoodEventList();
    public User currentUser;
    private boolean online;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(MainActivity.this, SignInActivity.class);
//        startActivity(intent);
//        ElasticsearchUserController.createIndex();

        //online = isNetworkAvailable();
        //If internet connection is available, get file from elastic search first
        if (true) {
            String currentUserName;
            try{
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                currentUserName = pref.getString("currentUser", "");}
            catch(Exception e){
                 currentUserName = "";
            }
            if (currentUserName.equals("")) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
//            SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
//            currentUserName = pref.getString("currentUser", "");
            //TODO find user using elastic search and preference
        }
//        else {
//            Toast.makeText(getApplicationContext(),
//                    "This device is not connected to internet.",
//                    Toast.LENGTH_SHORT).show();
//            //if no internet connection is available, load from local file
//            LoadJsonFile load = new LoadJsonFile();
//            // Load current user to local file
//            currentUser = load.loadFile();
//            // if currentUser is null, nobody has signed in, go to signInActivity
//            if (currentUser == null) {
//                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
//                startActivity(intent);
//            }
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        ImageView add_tab = (ImageView) findViewById(R.id.add_tab_home);
        add_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateEditMoodActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });


//        ImageView earth_tab = (ImageView) findViewById(R.id.earth_tab_home);
//        earth_tab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CreateEditMoodActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                startActivity(intent);
//            }
//        });

        //navigationView.setCheckedItem(R.id.drawer_filter);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_filter:
                        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.drawer_friend:
                        intent = new Intent(MainActivity.this, FriendActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.drawer_sign_out:
                        intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        // Test CardView
//        try{
//            TestCardView(moodEventArrayList);
//        }catch(MoodStateNotAvailableException e){
//            Toast.makeText(getApplicationContext(),
//                    "Invalid mood state tested.",
//                    Toast.LENGTH_SHORT).show();
//        };


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        RecyclerView.Adapter mAdapter = new MoodEventAdapter(moodEventArrayList);
        mRecyclerView.setAdapter(mAdapter);

    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.toolbar_mainactivity, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawerLayout.openDrawer(GravityCompat.START);
                    break;

                case R.id.add_follow:
                    Toast.makeText(this, "You clicked add_follow", Toast.LENGTH_SHORT).show();
                    break;

                default:
            }
            return true;
        }
//
//    public void TestCardView(MoodEventList List) throws MoodStateNotAvailableException{
//
//        User user1 = new User();
//        Log.d("user1 id", Integer.toString(user1.getUsrID()));
//        MoodEvent moodEvent1 = new MoodEvent("Anger", user1.getUsrID());
//        MoodEvent moodEvent2 = new MoodEvent("Happy", user1.getUsrID());
//
//        moodEventArrayList = user1.getMoodEventList();
//
//    }


    public void loadList(ArrayList<MoodEvent> moodEventArrayList) {
        // load List to UI
    }

    public void followDialogue(String username) {
        //
    }

    public void onitemDialogue() {
        //
    }
    //Taken from http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
    //2017-03-07
//    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
//    }
}
