
package com.example.mac.bugfree.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchImageController;
import com.example.mac.bugfree.controller.ElasticsearchImageOfflineController;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.UserNameList;
import com.example.mac.bugfree.util.InternetConnectionChecker;
import com.example.mac.bugfree.util.LoadFile;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.controller.MoodEventAdapter;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.util.SaveFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    private static final String FILENAME = "file.sav";

    private DrawerLayout mDrawerLayout;
    private String currentUserName;
    private Context context;
    private TextView drawer_name;
    private SwipeRefreshLayout swipeRefreshLayout;
    private InternetConnectionChecker checker = new InternetConnectionChecker();

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

        ImageView earth_tab = (ImageView) findViewById(R.id.earth_tab_home);
        earth_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = getApplicationContext();
                final boolean isOnline = checker.isOnline(context);
                if(isOnline) {
                    Intent intent = new Intent(MainActivity.this, MapActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                } else{
                    Toast.makeText(getApplicationContext(), "Map is not available when this device is offline.", Toast.LENGTH_LONG).show();
                }
            }
        });


        /**
         * Set the Listener for drawer in MainActivity
         */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                context = getApplicationContext();
                final boolean isOnline = checker.isOnline(context);
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.drawer_filter:
                        if (isOnline) {
                            intent = new Intent(MainActivity.this, FilterActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case R.id.drawer_friend:
                        if (isOnline) {
                            intent = new Intent(MainActivity.this, FriendActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case R.id.drawer_block:
                        if (isOnline) {
                            intent  = new Intent(MainActivity.this, BlockListActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case R.id.drawer_stat:
                        if (isOnline) {
                            intent  = new Intent(MainActivity.this, StatActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.drawer_sign_out:
                        // current user will be removed
                        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                        editor.putString("currentUser","");
                        editor.apply();

                        // Set has been offline to false
                        editor.putBoolean("hasBeenOffline", false);
                        editor.apply();

                        // filterFile will be removed
                        if (fileExists(context, FILENAME2)) {
                            File file = context.getFileStreamPath(FILENAME2);
                            file.delete();
                        }

                        File file = context.getFileStreamPath(FILENAME);
                        file.delete();

                        ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
                        ArrayList<String> onlineList = elasticsearchImageOfflineController.loadImageList(context,"online");
                        ArrayList<String> upList = elasticsearchImageOfflineController.loadImageList(context,"upadte");
                        ArrayList<String> deleteList = elasticsearchImageOfflineController.loadImageList(context,"delete");
                        ArrayList<String> List = new ArrayList<String>();
                        List.addAll(onlineList);
                        List.addAll(onlineList);
                        for(String id:deleteList){
                            List.remove(id);
                        }

                        file = context.getFileStreamPath("ImageDeleteList.sav");
                        file.delete();
                        file = context.getFileStreamPath("ImageOnlineList.sav");
                        file.delete();
                        file = context.getFileStreamPath("ImageUploadList.sav");
                        file.delete();

                        for(String id:List){
                            try {
                                file = context.getFileStreamPath(id);
                                file.delete();
                            } catch (Exception e){
                            }
                        }
                        // change to SignInActivity
                        intent = new Intent(MainActivity.this, SignInActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userOfflineUpdate();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (fileExists(context, FILENAME2)) {
                    loadFromFilterFile(context);
                } else {
                    loadList(currentUserName);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawers();
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");

        if (currentUserName.equals("")) {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        } else {
            drawer_name.setText(currentUserName);
            context = getApplicationContext();
            userOfflineUpdate();
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
                context = getApplicationContext();
                final boolean isOnline = checker.isOnline(context);
                if (isOnline) {
                    Toast.makeText(this, "You clicked add_follow", Toast.LENGTH_SHORT).show();
                    followDialogue();
                } else{
                    Toast.makeText(this, "This Device is offline", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.add_block:
                context = getApplicationContext();
                final boolean isOline = checker.isOnline(context);
                if (isOline) {
                    Toast.makeText(this, "You clicked add_block", Toast.LENGTH_SHORT).show();
                    blockDialogue();
                } else{
                    Toast.makeText(this, "This Device is offline", Toast.LENGTH_SHORT).show();
                }
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

        context = getApplicationContext();
        final boolean isOnline = checker.isOnline(context);

        if (isOnline) {
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(query);
            try {
                user = getUserTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
        } else {
            LoadFile load = new LoadFile();
            user = load.loadUser(context);
        }
        MoodEventList moodEventList = user.getMoodEventList();

        if(isOnline){
            ElasticsearchUserController.GetUserTask getUserTask1;
            ArrayList<String> followeeList = user.getFolloweeIDs();
            for (String followee : followeeList) {
                getUserTask1 = new ElasticsearchUserController.GetUserTask();
                getUserTask1.execute(followee);
                try {
                    User user_follow = getUserTask1.get();
                    MoodEventList userFollowMoodList = user_follow.getMoodEventList();
                    userFollowMoodList.sortByDate();
                    moodEventList.addMoodEvent(userFollowMoodList.getMoodEvent(0));
                } catch (Exception e) {
                    //Log.i("Error", "Failed to get the User out of the async object");
                }
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
        RecyclerView.Adapter mAdapter = new MoodEventAdapter(moodEventList, currentUserName,getApplicationContext());
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

    public void blockDialogue() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Block List");
        alertDialog.setMessage("Please enter the name of user who you want block");
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
                        String blockName = input.getText().toString();
                        addBlock(blockName);
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
                    ArrayList<String> blockList = user.getBlockList();

                    if ( followerList.contains(currentUserName)) {
                        Toast.makeText(this, "You already followed this user", Toast.LENGTH_SHORT).show();
                    } else if (blockList.contains(currentUserName)){
                        Toast.makeText(this, "You have been blocked", Toast.LENGTH_SHORT).show();
                    } else {
                        if (pendingList.contains(currentUserName)) {
                            Toast.makeText(this, "You already in pending list", Toast.LENGTH_SHORT).show();
                        } else {
                            pendingList.add(currentUserName);
                            user.setPendingPermissions(pendingList);
                            ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
                            addUserTask.execute(user);

                        }
                    }

                } else {
                    Toast.makeText(this, "The user does not exist", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                //Log.i("Error", "Failed to get the User out of the async object");
            }
        }

    }



    private void addBlock(String blockName) {
        if (blockName.equals(currentUserName)) {
            Toast.makeText(this, "You enter wrong username", Toast.LENGTH_SHORT).show();
        }

        else {
            ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(currentUserName);
            try {
                User user = getUserTask.get();
                ArrayList<String> blockList = user.getBlockList();
                ArrayList<String> followerList = user.getFollowerIDs();
                UserNameList userNameList = new UserNameList();
                ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
                getUserListTask.execute("name");
                try{
                    userNameList = getUserListTask.get();
                } catch (Exception e) {
                    Log.i("Error", "Failed to get the User out of the async object");
                }
                ArrayList<String> unList = userNameList.getUserNameList();
                ArrayList<String> followList = user.getFolloweeIDs();
                ArrayList<String> notificationList = user.getPendingPermission();
                if (blockList.contains(blockName)){
                    Toast.makeText(this, "You already blocked this user", Toast.LENGTH_SHORT).show();
                } else if (!unList.contains(blockName)){
                    Toast.makeText(this, "This user does not exist", Toast.LENGTH_SHORT).show();
                } else if (followList.contains(blockName)){
                    Toast.makeText(this, "You already followed this user", Toast.LENGTH_SHORT).show();
                } else if (notificationList.contains(blockName)){
                    Toast.makeText(this, "This user sent you a notification", Toast.LENGTH_SHORT).show();
                } else {
                    blockList.add(blockName);
                    user.setBlockIDs(blockList);
                    ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
                    addUserTask.execute(user);

                    //TODO add to local file
                    SaveFile savefile = new SaveFile(context,user);
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
        RecyclerView.Adapter mAdapter = new MoodEventAdapter(moodEventList, currentUserName,getApplicationContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * This method checks internet connectivity first, when there is no internet connection and someone has logged in,
     * it clears the filtered file to allow user only see him/herself's moodevent.
     * When the device is online, it uploads the local user and all the local new image file to elastic search,
     * deletes the image from elastic search if user has deleted some online image while offline
     * and clears the pending image to be uploaded.
     */
    private void userOfflineUpdate(){
        context = getApplicationContext();
        boolean isOnline = checker.isOnline(context);

        SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");

        if (!isOnline && !currentUserName.equals("")){
            // filterFile will be removed
            if (fileExists(context, FILENAME2)) {
                File file = context.getFileStreamPath(FILENAME2);
                file.delete();
            }
        }
        if (true) {
            //If has been offline and now is online, when signed in, load the local user and upload the local user
            if (!currentUserName.equals("")) {
                try {
                    LoadFile load = new LoadFile();
                    User user = load.loadUser(context);
                    try {
                        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
                        getUserTask.execute(currentUserName);
                        User user1 = getUserTask.get();
                        if(user1 != null) {
                            ArrayList<String> followeeList = user1.getFolloweeIDs();
                            ArrayList<String> followerList = user1.getFollowerIDs();
                            ArrayList<String> blockList = user1.getBlockList();
                            ArrayList<String> pendingList = user1.getPendingPermission();

                            user.setBlockIDs(blockList);
                            user.setFolloweeIDList(followeeList);
                            user.setFollowerIDList(followerList);
                            user.setPendingPermissions(pendingList);

                            Context context = getApplicationContext();
                            SaveFile s = new SaveFile(context, user);

                        }
                    } catch (Exception e){
                        Log.i("Offline","Cannot reset Friend arraylist");
                    }

                    if (user != null) {
                        ElasticsearchUserController.AddUserTask addUserTask = new ElasticsearchUserController.AddUserTask();
                        addUserTask.execute(user);
                    }

                    // Upload the newly created images and Delete the old base64 online
                    ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();

                    ElasticsearchImageController.AddImageTask addImageTask;
                    ElasticsearchImageController.DeleteImageTask deleteImageTask;

                    ArrayList<String> deleteList = elasticsearchImageOfflineController.loadImageList(context,"delete");
                    for (String Id :deleteList){
                        deleteImageTask = new ElasticsearchImageController.DeleteImageTask();
                        deleteImageTask.execute(Id);
                    }
                    ArrayList<String> upList = elasticsearchImageOfflineController.loadImageList(context,"upload");
                    for (String Id :upList) {
                        String base64 = elasticsearchImageOfflineController.loadBase64(context, Id);
                        ImageForElasticSearch ifes = new ImageForElasticSearch(base64,Id);
                        addImageTask = new ElasticsearchImageController.AddImageTask();
                        addImageTask.execute(ifes);
                        Log.i("upid",Id);
                    }

                    context = getApplicationContext();
                    isOnline = checker.isOnline(context);

                    if(isOnline) {
                        //Clear the local upload,delete,online lists
                        elasticsearchImageOfflineController.prepImageOffline(context, user);
                        Log.d("Finish", "Finish");
                    }

                } catch (Exception e){
                    Log.i("Warning", "Failed to read and upload local file.");
                }
            }
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}
