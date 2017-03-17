package com.example.mac.bugfree.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by mac on 2017-02-25.
 * An ArrayList Stores a series of moodEvents.
 * A moodEvent can be add to, found from, delete from a MoodEventList(MEL)
 * All moodEvents in current MEL can be sort by date using the sortByDate method.
 * @author Xinlei Chen
 */

public class MoodEventList {

    public ArrayList<MoodEvent> moodEventArrayList;


    public MoodEventList(ArrayList<MoodEvent> moodEventList) {
        this.moodEventArrayList = moodEventList;
    }

    public MoodEventList() {
        this.moodEventArrayList = new ArrayList<MoodEvent>();
    }

    public void addMoodEvent(MoodEvent moodEvent) {

        if (this.hasMoodEvent(moodEvent)) {
            throw new IllegalArgumentException("Already have same MoodEvent in list");
        }

        this.moodEventArrayList.add(moodEvent);
    }

    public void addMoodEventList(MoodEventList moodEventList) {
        this.moodEventArrayList.addAll(moodEventList.transferMoodEventListToArray());
    }

    /**
     * Change the MoodEventList into ArrayList<MoodEvent>
     * @return ArrayList<MoodEvent>
     */
    public ArrayList<MoodEvent> transferMoodEventListToArray(){
        return this.moodEventArrayList;
    }

    public boolean hasMoodEvent(MoodEvent moodEvent) {
        return this.moodEventArrayList.contains(moodEvent);
    }

    /**
     * delete the moodEvent
     * @param moodEvent
     */
    public void deleteMoodEvent(MoodEvent moodEvent) {
        this.moodEventArrayList.remove(moodEvent);
    }

    public MoodEvent getMoodEvent(int index) {
        return this.moodEventArrayList.get(index);
    }

    public int getCount() {
        return this.moodEventArrayList.size();
    }

    /**
     * sort the moodEventList from newest to oldest
     */
    public void sortByDate() {
        Collections.sort(moodEventArrayList, new Comparator<MoodEvent>() {
            @Override
            public int compare(MoodEvent o1, MoodEvent o2) {
                return o2.getDateOfRecord().compareTo(o1.getDateOfRecord());
            }
        });
    }
}
