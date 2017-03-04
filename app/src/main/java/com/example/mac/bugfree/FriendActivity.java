package com.example.mac.bugfree;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FriendActivity extends AppCompatActivity {

    UserList userlist;
    User currentUser = userlist.getUser(0);
    List followList = currentUser.getFolloweeIDs();
    List followerList = currentUser.getFollowerIDs();
    List notificationList = currentUser.getPendingPermission();
    ListView followListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        followListView = (ListView) findViewById(R.id.followList);

        ArrayAdapter<User> adapter= new FollowListAdapter();
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
        public FollowListAdapter() {
            super(FriendActivity.this, R.layout.list_friend_item, followList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_friend_item, parent, false);

            TextView friendName = (TextView) view.findViewById(R.id.friendID);
            friendName.setText("MengyangChen");
            return view;
        }
    }

    private class FollowerListAdapter extends ArrayAdapter<User> {
        public FollowerListAdapter() {
            super(FriendActivity.this, R.layout.list_friend_item, followerList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_friend_item, parent, false);
            TextView friendName = (TextView) view.findViewById(R.id.friendID);
            friendName.setText("MengyangChen");
            return view;
        }
    }

    private class NotificationListAdapter extends ArrayAdapter<User> {
        public NotificationListAdapter() {
            super(FriendActivity.this, R.layout.list_notification_item, notificationList);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_notification_item, parent, false);
            TextView friendName = (TextView) view.findViewById(R.id.notificationName);
            friendName.setText("MengyangChen");
            return view;
        }
    }

}