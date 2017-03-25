package com.example.mac.bugfree.module;


import com.example.mac.bugfree.exception.MoodStateNotAvailableException;
import com.example.mac.bugfree.R;
import com.example.mac.bugfree.exception.ImageTooBigException;
import com.example.mac.bugfree.util.StateConstants;

import org.osmdroid.util.GeoPoint;

import java.util.GregorianCalendar;

/**
 * This class stores all attributes about a MoodEvent.
 * It will add itself to the corresponding user's MoodEvent List at construct.
 * A moodState type have it's own fixed color and Icon.
 * MoodState is a must, belongs to is also a must( for retrieval purpose.)
 * All other attributes are optional depends on the user's need.
 *
 * @author Zhi Li
 */
public class MoodEvent implements StateConstants {

    private String moodState;
    private Integer moodColor;
    private Integer moodIcon;
    private GregorianCalendar dateOfRecord;
    private GregorianCalendar realtime;
    private String triggerText;
    private String socialSituation;
    private String picId;
    private GeoPoint location;
    private String belongsTo;

    /**
     * To create a mood event, two factors are needed.
     * 1. A mood state(happy/sad etc.)
     * 2. A creator ID (unique user ID, String)
     * The constructor will do following:
     * 1. Initialize with corresponding unique moodState, color and icon.
     * 2. Add this mood event to corresponding user's moodEvent list.
     * Other attributes are added by setters.
     *
     * @param MoodState String of mood state
     * @param belongsTo String, User name
     * @throws MoodStateNotAvailableException the mood state not available exception
     */
    public MoodEvent(String MoodState,String belongsTo) throws MoodStateNotAvailableException  {
        //Initialize moodState, color, icon.
        this.setMoodState(MoodState);
        try{
            this.setColorIcon();
        } catch (MoodStateNotAvailableException e){
            e.printStackTrace();
        }

        this.setBelongsTo(belongsTo);
        this.location = null;
    }

    /**
     * Sets color icon.
     *
     * @throws MoodStateNotAvailableException the mood state not available exception
     */
//Set Color and Icon together
    public void setColorIcon() throws MoodStateNotAvailableException{
        String MoodState = this.moodState;
        if (MoodState.equals("Anger")){
            this.moodColor = StateConstants.angerRed;
            this.moodIcon = StateConstants.angerIcon;
        }else if (MoodState.equals("Confusion")){
            this.moodColor = StateConstants.confusionCyan;
            this.moodIcon = StateConstants.confusionIcon;
        }else if (MoodState.equals("Disgust")){
            this.moodColor = StateConstants.disgustGray;
            this.moodIcon = StateConstants.disgustIcon;
        }else if (MoodState.equals("Fear")){
            this.moodColor = StateConstants.fearBlack;
            this.moodIcon = StateConstants.fearIcon;
        }else if (MoodState.equals("Happy")){
            this.moodColor = StateConstants.happyYellow;
            this.moodIcon = StateConstants.happyIcon;
        }else if (MoodState.equals("Sad")){
            this.moodColor = StateConstants.sadBlue;
            this.moodIcon = StateConstants.sadIcon;
        }else if (MoodState.equals("Shame")){
            this.moodColor = StateConstants.shameGreen;
            this.moodIcon = StateConstants.shameIcon;
        }else if (MoodState.equals("Surprise")){
            this.moodColor = StateConstants.surpriseMagenta;
            this.moodIcon = StateConstants.surpriseIcon;
        }else{
            throw new MoodStateNotAvailableException();
        }
    }

    /**
     * Gets mood state.
     *
     * @return the mood state
     */
    public String getMoodState() {
        return moodState;
    }

    /**
     * Sets mood state.
     *
     * @param moodState the mood state
     */
    public void setMoodState(String moodState) {
        this.moodState = moodState;
    }

    /**
     * Gets mood color.
     *
     * @return the mood color
     */
    public Integer getMoodColor() {
        return moodColor;
    }

    /**
     * Gets mood icon.
     *
     * @return the mood icon
     */
    public Integer getMoodIcon() {
        return moodIcon;
    }

    /**
     * Gets belongs to.
     *
     * @return the belongs to
     */
    public String getBelongsTo() {
        return belongsTo;
    }

    /**
     * Sets belongs to.
     *
     * @param belongsTo the belongs to
     */
    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    /**
     * Gets Id pic.
     *
     * @return the id pic
     */
    public String getPicId() {
        return picId;
    }

    /**
     * Sets pic Id.
     *
     * @param picId the pic Id
     */
    public void setPicId(String picId){
        this.picId = picId;
    }

    /**
     * Gets social situation.
     *
     * @return the social situation
     */
    public String getSocialSituation() {
        return socialSituation;
    }

    /**
     * Sets social situation.
     *
     * @param socialSituation the social situation
     */
    public void setSocialSituation(String socialSituation) {
            this.socialSituation = socialSituation;

    }

    /**
     * Gets trigger text.
     *
     * @return the trigger text
     */
    public String getTriggerText() {
        return triggerText;
    }

    /**
     * Sets trigger text.
     *
     * @param triggerText the trigger text
     */
    public void setTriggerText(String triggerText) {
        this.triggerText = triggerText;
    }

    /**
     * Gets date of record.
     *
     * @return the date of record
     */
// Reference https://www.mkyong.com/java/java-date-and-calendar-examples/
    // At 2017-02-26 15:00
    public GregorianCalendar getDateOfRecord() {
        return dateOfRecord;
    }

    /**
     * Sets date of record.
     *
     * @param dateOfRecord the date of record
     */
    public void setDateOfRecord(GregorianCalendar dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    /**
     * Gets realtime.
     *
     * @return the realtime
     */
    public GregorianCalendar getRealtime() {
        return realtime;
    }

    /**
     * Sets realtime.
     *
     * @param realtime the realtime
     */
    public void setRealtime(GregorianCalendar realtime) {
        this.realtime = realtime;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    @Override
    /**
     * This method check if the object passed in is equals to this moodEvent object.
     * @param obj Object (MoodEvent object expected)
     * @returns boolean
     */
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof MoodEvent)) return false;
        MoodEvent other= (MoodEvent) obj;

        if (this.getRealtime().equals(other.getRealtime())
                && this.getBelongsTo().equals(other.getBelongsTo())) {
            return true;
        }
        return false;
    }
}
