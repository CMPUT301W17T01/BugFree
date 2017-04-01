package com.example.mac.bugfree.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.mac.bugfree.util.SaveFile;

import java.util.ArrayList;

/**
 * Created by yipengzhou on 2017/3/28.
 */

public class BlockListActivity extends FriendActivity {
    private ArrayList<String> blockList;
    private ListView blockListView;
    private SaveFile savefile;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_block);
        setSupportActionBar(toolbar);

        blockList = user.getBlockList();

        final ArrayAdapter<User> adapter = new blockListAdapter(this, blockList);
        blockListView = (ListView) findViewById(R.id.block_list_view);
        blockListView.setAdapter(adapter);
    }

    private class blockListAdapter extends ArrayAdapter<User> {
        private Context context;
        public blockListAdapter(Context context, ArrayList blockList) {
            super(context, R.layout.list_block_item, blockList);
            this.context = context;
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
                    ElasticsearchUserController.AddUserTask addUserTask =
                            new ElasticsearchUserController.AddUserTask();
                    addUserTask.execute(user);
                    ArrayAdapter<User> adapter = new blockListAdapter(BlockListActivity.this,
                            blockList);
                    blockListView.setAdapter(adapter);

                    //savefile = new SaveFile(context,user);


                    Toast.makeText(getApplicationContext(), singleblock+
                            " has been removed", Toast.LENGTH_SHORT).show();
                }
            });

            return view;
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
                setResult(RESULT_OK);
                finish();
                break;

            default:
        }
        return true;
    }

}
