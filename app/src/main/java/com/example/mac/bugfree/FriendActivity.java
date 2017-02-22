package com.example.mac.bugfree;

import android.content.Intent;
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

    List<User> Friends = new ArrayList<>();

    List<User> Notifications = new ArrayList<>();

    ListView friendListView;
    ListView notificationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.Follower);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.Follow);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.Notification);
        tabSpec.setIndicator("List");
        tabHost.addTab(tabSpec);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.homebtn, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.homeBtn:
                Intent intent = new Intent(FriendActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return true;
    }

    private class FriendListAdapter extends ArrayAdapter<User>{
        public FriendListAdapter(){
            super(FriendActivity.this, R.layout.list_friend_item, Friends);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_friend_item, parent, false);
            User currentfriend = Friends.get(position);
            TextView friendName = (TextView) view.findViewById(R.id.friendName);
            friendName.setText(currentfriend.getUsr());

            return view;
        }

    }

    private class NotificationAdapter extends ArrayAdapter<User>{
        public NotificationAdapter(){
            super(FriendActivity.this, R.layout.list_notification_item, Notifications);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_notification_item, parent, false);

            return view;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        ArrayAdapter<User> adapter1= new FriendListAdapter();
        friendListView.setAdapter(adapter1);

        ArrayAdapter<User> adapter2= new NotificationAdapter();
        notificationListView.setAdapter(adapter2);
    }
}
