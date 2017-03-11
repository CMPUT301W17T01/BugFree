package com.example.mac.bugfree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by mac on 2017-02-21.
 */

public class MoodEventAdapter extends RecyclerView.Adapter<MoodEventAdapter.ViewHolder> {

    private MoodEventList mmoodEventArrayList = new MoodEventList();
    private String currentUser = "";

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        TextView usernameText;
        ImageView picImage;
        TextView reasonText;
        TextView dateText;
        ImageView eventHandleImage;
        TextView colorText;

        ViewHolder(View view) {
            super(view);
            iconImage = (ImageView) view.findViewById(R.id.mood_event_icon);
            usernameText = (TextView) view.findViewById(R.id.mood_event_username);
            picImage = (ImageView) view.findViewById(R.id.mood_event_pic);
            reasonText = (TextView) view.findViewById(R.id.mood_event_reason);
            dateText = (TextView) view.findViewById(R.id.mood_event_date);
            eventHandleImage = (ImageView) view.findViewById(R.id.event_handle);
            colorText = (TextView) view.findViewById(R.id.mood_color_text);
        }
    }

    // Provide a suitable constructor
    public MoodEventAdapter(MoodEventList moodEventArrayList, String currentUser) {
        this.mmoodEventArrayList = moodEventArrayList;
        this.currentUser = currentUser;
        //Log.d("Size of ArrayList", Integer.toString(mmoodEventArrayList.getCount()));
    }

    // Create new views (invoked by the layout manager)

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mood_event_item, parent, false);

        final ViewHolder vh = new ViewHolder(v);
        vh.usernameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = vh.getAdapterPosition();
                //Toast.makeText(v.getContext(), "You clicked it", Toast.LENGTH_SHORT).show();
                MoodEvent moodEvent = mmoodEventArrayList.getMoodEvent(position);

                SharedPreferences.Editor editor = v.getContext().getSharedPreferences("viewMoodEvent",Context.MODE_PRIVATE).edit();
                Gson gson = new Gson();
                String json = gson.toJson(moodEvent);
                editor.putString("moodevent",json);
                editor.apply();

                Intent intent = new Intent(v.getContext(), ViewMoodActivity.class);
                v.getContext().startActivity(intent);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final MoodEvent moodEvent = mmoodEventArrayList.getMoodEvent(position);
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm");
        holder.iconImage.setImageResource(moodEvent.getMoodIcon());
        //holder.usernameText.setText("Username");
        holder.usernameText.setText(moodEvent.getBelongsTo());
        holder.picImage.setImageResource(R.drawable.picture_text);
        holder.reasonText.setText(moodEvent.getTriggerText());
        holder.dateText.setText("Date");
        holder.eventHandleImage.setImageResource(R.drawable.point);
        holder.colorText.setText(" @" + moodEvent.getMoodState());
        holder.colorText.setTextColor(moodEvent.getMoodColor());
        //Log.d("Text onBind", moodEvent.getMoodState());
        String time = format.format(moodEvent.getDateOfRecord().getTime());
//        Calendar ss = moodEvent.getDateOfRecord();
//        ss.add(Calendar.MONTH, 1);
//        String time = format.format(ss.getTime());
        holder.dateText.setText(time);

        // write code about authority
        // if the moodEvent is not belong to user, it will not show PopupMenu
        if (!moodEvent.getBelongsTo().equals(currentUser)) {
            holder.eventHandleImage.setVisibility(View.INVISIBLE);
        }


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
        popup.setOnMenuItemClickListener(new MoodEventPopupClickListener(position, mmoodEventArrayList.getMoodEvent(position), view.getContext()));
        popup.show();
    }
}
