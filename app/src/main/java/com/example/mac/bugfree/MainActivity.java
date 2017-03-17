package com.example.mac.bugfree;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * This Class is the main view class of the project.
 * In this Class, user interaction and file manipulation is performed
 *<pre>
 *     pre-formatted text: <br>
 *         File Explorer -> data -> data -> com.example.mac.BugFree -> files -> filter.sav
 *</pre>
 *
 * some reference: http://developer.android.com/training/material/lists-cards.html
 *
 * @author  Xinlei Chen
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME2 = "filter.sav";

    private DrawerLayout mDrawerLayout;
    //private MoodEventList moodEventArrayList = new MoodEventList();
    private String currentUserName;
    private Context context;
    private TextView drawer_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();


        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        drawer_name = (TextView) header.findViewById(R.id.drawer_user_name);
        //drawer_name.setText(currentUserName);

        ActionBar actionBar = getSupportActionBar();
        if( actionBar != null) {
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


        /**
         * Set the Listener for drawer in MainActivity
         */
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
                        // current user will be removed
                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putString("currentUser","");
                        editor.apply();

                        // filterFile will be removed
                        if (fileExists(context, FILENAME2)) {
                            File file = context.getFileStreamPath(FILENAME2);
                            file.delete();
                        }

                        // change to SignInActivity
                        intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");
        if (currentUserName.equals("")) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        } else {
            drawer_name.setText(currentUserName);
            context = getApplicationContext();
            if (fileExists(context, FILENAME2)) {
                loadFromFilterFile(context);
            } else {
                loadList(currentUserName);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_mainactivity, menu);
        return true;
    }

    /**
     * Set the listener for the toolbar(Action Bar)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.add_follow:
                Toast.makeText(this, "You clicked add_follow", Toast.LENGTH_SHORT).show();
                followDialogue();
                break;

            default:
        }
        return true;
    }


    /**
     * Get whole mood events from user and the user's follow when "filter.sav" is not exist
     * Get the data from the elastic search
     * @param currentUserName the current user name
     */
    public void loadList(String currentUserName) {
        // load List to UI

        User user = new User();

        String query = currentUserName;
        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);
        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        MoodEventList moodEventList = user.getMoodEventList();
        ElasticsearchUserController.GetUserTask getUserTask1;

        ArrayList<String> followeeList = user.getFolloweeIDs();
        for  (String followee : followeeList) {
            getUserTask1 = new  ElasticsearchUserController.GetUserTask();
            getUserTask1.execute(followee);
            try {
                User user_follow = getUserTask1.get();
                moodEventList.addMoodEventList(user_follow.getMoodEventList());
            } catch (Exception e) {
                //Log.i("Error", "Failed to get the User out of the async object");
            }
        }

        moodEventList.sortByDate();
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // Take from: http://developer.android.com/training/material/lists-cards.html
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        RecyclerView.Adapter mAdapter = new MoodEventAdapter(moodEventList, currentUserName);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * The function will be invoked when user click Add Follow from tool bar menu
     */
    public void followDialogue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Follow");
        alertDialog.setMessage("Please enter the name of user who you want follow");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_homebtn);

        alertDialog.setPositiveButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String followName = input.getText().toString();
                        addFollow(followName);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        alertDialog.show();
    }

    /**
     * To send the request of following
     * Add the user to other user's pending permission
     * @param followName
     */
    private void addFollow(String followName) {
        // if followName = currentUserName
        if (followName.equals(currentUserName)) {
            Toast.makeText(this, "You enter wrong username", Toast.LENGTH_SHORT).show();
        }

        else {
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(followName);

            try {
                User user = getUserTask.get();
                if(user != null){
                    ArrayList<String> pendingList = user.getPendingPermission();
                    ArrayList<String> followerList = user.getFollowerIDs();

                    if ( followerList.contains(currentUserName)) {
                        Toast.makeText(this, "You already followed this user", Toast.LENGTH_SHORT).show();
                    } else {
                        pendingList.add(currentUserName);
                        user.setPendingPermissions(pendingList);
                        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
                        addUserTask.execute(user);
                    }

                } else {
                    Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //Log.i("Error", "Failed to get the User out of the async object");
            }
        }

    }

    /**
     * To check if the file "filter.sav" is exist
     * @param context
     * @param filename
     * @return if it exists, return true. Vice Versa
     */
    private boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        if (file == null || !file.exists()) {
            return false;
        }
        return true;
    }

    /**
     * If the "filter.sav" is exist, load moodEventList from the file
     * @param context
     */
    private void loadFromFilterFile(Context context) {
        LoadFile loadFile = new LoadFile();
        ArrayList<MoodEvent> moodEventArrayList = new ArrayList<>();

        moodEventArrayList = loadFile.loadFilteredMoodEventList(context);
        MoodEventList moodEventList = new MoodEventList(moodEventArrayList);
        moodEventList.sortByDate();

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // Take from: http://developer.android.com/training/material/lists-cards.html
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        RecyclerView.Adapter mAdapter = new MoodEventAdapter(moodEventList, currentUserName);
        mRecyclerView.setAdapter(mAdapter);
    }

}
