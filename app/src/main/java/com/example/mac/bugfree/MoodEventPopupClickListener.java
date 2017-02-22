package com.example.mac.bugfree;

import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by mac on 2017-02-21.
 */

public class MoodEventPopupClickListener implements PopupMenu.OnMenuItemClickListener {

    private int position;
    public MoodEventPopupClickListener(int position) {
        this.position = position;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){

            case R.id.edit_card:
                // content here
                break;

            case R.id.delete_card:
                // content here
                break;

            default:

        }
        return true;
    }
}
