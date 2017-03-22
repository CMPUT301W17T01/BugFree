package com.example.mac.bugfree.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.module.User;

import java.util.ArrayList;

/**
 * This is the Friend Activity of this porject.
 * In this activity, user can view their follows, followers and notifications
 * Also, user can choose to accept or decline other user's friend application.
 * It use elastic search to get the informations of the user and get their
 * friend lists. If the user clicked accept button in the notifications,
 * the applier will be added to the follower list of the current user and
 * the data will be uploaded.
 *<pre>
 *     pre-formatted text: <br>
 *         File Explorer -> data -> data -> com.example.mac.BugFree -> files -> filter.sav
 *</pre>
 *
 * @Author Yipeng Zhou
 */


public class FriendActivity extends AppCompatActivity {

    private ArrayList<String> followList;
    private ArrayList<String> followerList;
    private ArrayList<String> notificationList;
    private ArrayList<String> anotherfollowList;
    private ListView followListView;
    private ListView followerListView;
    private ListView notificationListView;

    private String currentUserName;
    private User user = new User();

    public ListView getNotificationList(){
        return notificationListView;
    }

    public ListView getFollowerListView(){
        return followerListView;
    }

    /** Called when the activity is first created.
     * Create a tab view for the follow, follower and notifications
     * and list the informations in the tab view.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_friend);
        setSupportActionBar(toolbar);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        currentUserName = pref.getString("currentUser", "");

        String query = currentUserName;
        ElasticsearchUserController.GetUserTask getUserTask =
                new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }

        followerList = user.getFollowerIDs();
        followList = user.getFolloweeIDs();
        notificationList = user.getPendingPermission();


        final ArrayAdapter<User> adapter1= new FollowListAdapter(this, followList);
        final ArrayAdapter<User> adapter2 = new FollowerListAdapter(this, followerList);
        final ArrayAdapter<User> adapter3 = new NotificationListAdapter(this, notificationList);




        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                TextView tv = (TextView) tabHost.getCurrentTabView().
                        findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setTextSize(13);

                switch (tabHost.getCurrentTab()){
                    case 0:
                        followListView = (ListView) findViewById(R.id.followList);
                        followListView.setAdapter(adapter1);
                    case 1:
                        followerListView = (ListView) findViewById(R.id.followerList);
                        followerListView.setAdapter(adapter2);
                    case 2:
                        notificationListView = (ListView) findViewById(R.id.notificationList);
                        notificationListView.setAdapter(adapter3);
                }

            }
        });

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.Follow);
        tabSpec.setIndicator("Follow");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.Follower);
        tabSpec.setIndicator("Follower");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.Notification);
        tabSpec.setIndicator("Notification");
        tabHost.addTab(tabSpec);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
                    .findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(13);
        }

    }

    /**
     * Call when the activity is first created.
     * Create a home button on the action bar of the activity
     * which allows user to click on it and return to the
     * main activity.
     * @param menu
     * @return
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homebtn, menu);
        return true;
    }

    /**
     * Set the action of the home button.
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeBtn:
                setResult(RESULT_OK);
                finish();
                break;

            default:
        }
        return true;
    }

    /**
     * This is the FollowList adapter for the activity.
     * It adapts the list view whenever the view has been changed,
     * i.e. the user added a new follow.
     */

    private class FollowListAdapter extends ArrayAdapter<User> {
        public FollowListAdapter(Context context, ArrayList followList) {
            super(context, R.layout.list_friend_item, followList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_friend_item, parent, false);

            String singleFollowee = followList.get(position).toString();
            TextView friendName = (TextView) view.findViewById(R.id.friendID);
            friendName.setText(singleFollowee);

        return view;
        }
    }

    /**
     * This is the FollowerList adapter for the activity.
     * It adapts the list view whenever the view has been changed,
     * i.e. a new follower has been added in to the follower list.
     */

    private class FollowerListAdapter extends ArrayAdapter<User> {
        public FollowerListAdapter(Context context, ArrayList followerList) {
            super(context, R.layout.list_friend_item, followerList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_friend_item, parent, false);

            String singleFollower = followerList.get(position).toString();
            TextView friendName = (TextView) view.findViewById(R.id.friendID);
            friendName.setText(singleFollower);
            return view;
        }
    }

    /**
     * This is the NotificationList adapter for the activity.
     * It adapts the list view whenever the view has been changed,
     * i.e. a new notification has been added into the list. And It
     * also includes two buttons which are acceptBtn and declineBtn,
     * If the user click on the accept button in the list view item,
     * a new follower will be added in to the follower list of the
     * current user, and current user will be added to the sender's
     * follow list. If the user choose to click on the decline button
     * on the list item, the application will be deleted from the
     * pending permission in the elastic search.
     */

    private class NotificationListAdapter extends ArrayAdapter<User> {
        public NotificationListAdapter(Context context, ArrayList notificationList) {
            super(context, R.layout.list_notification_item, notificationList);
        }
        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_notification_item, parent, false);

            final String singleNotification = notificationList.get(position).toString();
            final TextView notificationName = (TextView) view.findViewById(R.id.notificationID);
            notificationName.setText(singleNotification);
            Button acceptBtn = (Button) view.findViewById(R.id.acceptBtn);
            Button declineBtn = (Button) view.findViewById(R.id.declineBtn);
            acceptBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    followerList.add(singleNotification);
                    notificationList.remove(position);
                    ArrayAdapter<User> adapter = new NotificationListAdapter(FriendActivity.this,
                            notificationList);
                    notificationListView.setAdapter(adapter);

                    ElasticsearchUserController.AddUserTask addUserTask =
                            new ElasticsearchUserController.AddUserTask();
                    addUserTask.execute(user);

                    User anotherUser = new User(singleNotification);

                    ElasticsearchUserController.GetUserTask getUserTask =
                            new ElasticsearchUserController.GetUserTask();
                    getUserTask.execute(singleNotification);
                    try{
                        anotherUser = getUserTask.get();
                    } catch (Exception e) {
                        Log.i("Error", "Failed to get the User out of the async object");
                    }

                    anotherfollowList = anotherUser.getFolloweeIDs();
                    anotherfollowList.add(currentUserName);


                    ElasticsearchUserController.AddUserTask addUserTask2 =
                            new ElasticsearchUserController.AddUserTask();
                    addUserTask2.execute(anotherUser);


                    Toast.makeText(getApplicationContext(), singleNotification +
                            " has been accepted", Toast.LENGTH_SHORT).show();
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    notificationList.remove(position);
                    ArrayAdapter<User> adapter = new NotificationListAdapter(FriendActivity.this,
                            notificationList);
                    notificationListView.setAdapter(adapter);

                    ElasticsearchUserController.AddUserTask addUserTask =
                            new ElasticsearchUserController.AddUserTask();
                    addUserTask.execute(user);

                    Toast.makeText(getApplicationContext(), singleNotification+
                            " has been declined", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }

    protected void onStart(){
        super.onStart();
    }

}