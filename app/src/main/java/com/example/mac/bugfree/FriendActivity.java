package com.example.mac.bugfree;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
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

import java.util.ArrayList;

public class FriendActivity extends AppCompatActivity {

    //UserList userlist = new UserList();
    //int currentUserID = userlist.getCurrentUserID();
    //User currentUser = userlist.getUser(currentUserID);


//    ArrayList followList = currentUser.getFolloweeIDs();
//    ArrayList followerList = currentUser.getFollowerIDs();
//    ArrayList notificationList = currentUser.getPendingPermission();
    ArrayList<String> followList;
    ArrayList<String> followerList;
    ArrayList<String> notificationList;
    ListView followListView;
    ListView followerListView;
    ListView notificationListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_friend);
        setSupportActionBar(toolbar);


        User user = new User("0John");
        String query = user.getUsr();
        ElasticsearchUserController.GetUserTask getUserTask = new ElasticsearchUserController.GetUserTask();
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

                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
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
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(13);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homebtn, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeBtn:
                Intent intent = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            default:
        }
        return true;
    }


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

    private class NotificationListAdapter extends ArrayAdapter<User> {
        public NotificationListAdapter(Context context, ArrayList notificationList) {
            super(context, R.layout.list_notification_item, notificationList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_notification_item, parent, false);

            final String singleNotification = notificationList.get(position).toString();
            TextView notificationName = (TextView) view.findViewById(R.id.notificationID);
            notificationName.setText(singleNotification);
            Button acceptBtn = (Button) view.findViewById(R.id.acceptBtn);
            Button declineBtn = (Button) view.findViewById(R.id.declineBtn);
            acceptBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), singleNotification+
                            " has been accepted", Toast.LENGTH_SHORT).show();
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
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