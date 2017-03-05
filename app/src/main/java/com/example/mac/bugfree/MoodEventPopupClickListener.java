package com.example.mac.bugfree;

import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by mac on 2017-02-21.
 */

public class MoodEventPopupClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;
    private MoodEvent moodEvent;
    private MoodEventAdapter moodEventAdapter;

    public MoodEventPopupClickListener(int position, MoodEvent moodEvent) {
        this.position = position;
        this.moodEvent = moodEvent;
        this.moodEventAdapter = moodEventAdapter;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.edit_card:
                Integer userId1 = this.moodEvent.getBelongsTo();
                break;

            case R.id.delete_card:
                // content here
                Integer userId2 = this.moodEvent.getBelongsTo();
                // get user list from gson file
                break;

            default:

        }
        return true;
    }
}
