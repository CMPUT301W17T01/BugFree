package com.example.mac.bugfree;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    UserList userlist = new UserList();
    User user1 = new User();
    User currentUser = userlist.getUser(0);
    ArrayList followList = currentUser.getFolloweeIDs();
    ArrayList followerList = currentUser.getFollowerIDs();
    ArrayList notificationList = currentUser.getPendingPermission();
    ListView followListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        ArrayAdapter<User> adapter= new FollowListAdapter(this, followList);

        followListView = (ListView) findViewById(R.id.followList);

        followListView.setAdapter(adapter);


        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));
                    tv.setTextSize(13);
                }

                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#3399ff"));
                tv.setTextSize(13);

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

    }


    private class FollowListAdapter extends ArrayAdapter<User> {
        public FollowListAdapter(Context context, ArrayList followList) {
            super(context, R.layout.list_friend_item, followList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_friend_item, parent, false);

            String singleFollowee = getItem(position).toString();
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

            String singleFollower = getItem(position).toString();
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

            String singleNotification = getItem(position).toString();
            TextView notificationName = (TextView) view.findViewById(R.id.notificationID);
            notificationName.setText(singleNotification);
            Button acceptBtn = (Button) findViewById(R.id.acceptBtn);
            Button declineBtn = (Button) findViewById(R.id.declineBtn);
            acceptBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"xxx has been accepted", Toast.LENGTH_SHORT).show();
                }
            });

            declineBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"xxx has been declined", Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }

    protected void onStart(){
        super.onStart();

        ArrayAdapter<User> adapter= new FollowListAdapter(this, followList);
        followListView = (ListView) findViewById(R.id.followList);

        followListView.setAdapter(adapter);
    }

}