package com.example.mac.bugfree.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.module.User;

import java.util.ArrayList;

/**
 * Created by yipengzhou on 2017/3/28.
 */

public class BlockListActivity extends FriendActivity {
    private ArrayList<String> blockList;
    private ListView blockListView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);

        blockList = user.getBlockList();

        final ArrayAdapter<User> adapter = new blockListAdapter(this, blockList);
        blockListView = (ListView) findViewById(R.id.notificationList);
        blockListView.setAdapter(adapter);
    }

    private class blockListAdapter extends ArrayAdapter<User> {
        public blockListAdapter(Context context, ArrayList blockList) {
            super(context, R.layout.list_block_item, blockList);
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.list_block_item, parent, false);
            final String singleblock = blockList.get(position).toString();
            final TextView blockName = (TextView) view.findViewById(R.id.blockID);
            blockName.setText(singleblock);
            Button removeBtn = (Button) view.findViewById(R.id.removeBtn);
            removeBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    blockList.remove(position);
                    ArrayAdapter<User> adapter = new blockListAdapter(BlockListActivity.this,
                            blockList);
                    blockListView.setAdapter(adapter);
                    ElasticsearchUserController.AddUserTask addUserTask =
                            new ElasticsearchUserController.AddUserTask();
                    addUserTask.execute(user);

                    Toast.makeText(getApplicationContext(), singleblock+
                            " has been removed", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
        }
    }

}
