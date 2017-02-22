package com.example.mac.bugfree;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

    private ArrayList<MoodEvent> mmoodEventArrayList = new ArrayList<>();

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
    public MoodEventAdapter(ArrayList<MoodEvent> moodEventArrayList) {
        mmoodEventArrayList = moodEventArrayList;
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

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //String username = mmoodEventArrayList.get(position).getname();

        // For Test
        holder.iconImage.setImageResource(R.drawable.drawer_id);
        holder.usernameText.setText("Username");
        holder.picImage.setImageResource(R.drawable.picture_text);
        holder.reasonText.setText("Reason");
        holder.dateText.setText("Date");
    }

    // Return the size of your list

    @Override
    public int getItemCount() {
        return mmoodEventArrayList.size();
    }
}
