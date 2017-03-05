package com.example.mac.bugfree;

import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2017-02-21.
 */

public class MoodEventAdapter extends RecyclerView.Adapter<MoodEventAdapter.ViewHolder> {

    private MoodEventList mmoodEventArrayList = new MoodEventList();

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView usernameText;
        ImageView picImage;
        TextView reasonText;
        TextView dateText;
        ImageView eventHandleImage;

        ViewHolder(View view) {
            super(view);
            iconImage = (ImageView) view.findViewById(R.id.mood_event_icon);
            usernameText = (TextView) view.findViewById(R.id.mood_event_username);
            picImage = (ImageView) view.findViewById(R.id.mood_event_pic);
            reasonText = (TextView) view.findViewById(R.id.mood_event_reason);
            dateText = (TextView) view.findViewById(R.id.mood_event_date);
            eventHandleImage = (ImageView) view.findViewById(R.id.event_handle);
        }
    }

    // Provide a suitable constructor
    public MoodEventAdapter(MoodEventList moodEventArrayList) {
        this.mmoodEventArrayList = moodEventArrayList;
        Log.d("Size of ArrayList", Integer.toString(mmoodEventArrayList.getCount()));
    }

    // Create new views (invoked by the layout manager)

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mood_event_item, parent, false);

        final ViewHolder vh = new ViewHolder(v);
        vh.usernameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                Toast.makeText(v.getContext(), "You clicked it", Toast.LENGTH_SHORT).show();
            }
        });

        vh.eventHandleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                showPopupMenu(vh.eventHandleImage, position);
            }
        });

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String username = mmoodEventArrayList.get(position).getname();

        // For Test
        MoodEvent moodEvent = mmoodEventArrayList.getMoodEvent(position);
        Log.d("Test", moodEvent.getMoodState());
        Log.d("Test 2", Boolean.toString(moodEvent.getMoodState().equals("Anger")));
        Log.d("position", Integer.toString(position));
        Log.d("iconId", Integer.toString(moodEvent.getMoodIcon()));
        //holder.iconImage.setImageResource(R.drawable.drawer_id);
        holder.iconImage.setImageResource(moodEvent.getMoodIcon());
        holder.usernameText.setText("Username");
        holder.picImage.setImageResource(R.drawable.picture_text);
        holder.reasonText.setText("Reason");
        holder.dateText.setText("Date");
        holder.eventHandleImage.setImageResource(R.drawable.point);


    }

    // Return the size of your list

    @Override
    public int getItemCount() {
        return mmoodEventArrayList.getCount();
    }

    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.mood_event_popup_menu_user, popup.getMenu());
        popup.setOnMenuItemClickListener(new MoodEventPopupClickListener(position, mmoodEventArrayList.getMoodEvent(position)));
        popup.show();
    }
}
