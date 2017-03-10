package com.example.mac.bugfree;

import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class stores all attributes about a MoodEvent.
 * It will add itself to the corresponding user's MoodEvent List at construct.
 * A moodState type have it's own fixed color and Icon.
 * MoodState is a must, belongs to is also a must( for retrieval purpose.)
 * All other attributes are optional depends on the user's need.
 * @author Zhi Li
 */

public class MoodEvent {
    //Fixed colors
    public static final int angerRed = -65536;
    public static final int confusionCyan = -16711681;
    public static final int disgustGray =  -7829368;
    public static final int fearBlack = -16777216;
    public static final int happyYellow = -256;
    public static final int sadBlue = -16776961;
    public static final int shameGreen = -16711936;
    public static final int surpriseMagenta = -65281;

    //Fixed icons
    public static final int angerIcon = R.drawable.anger;
    public static final int confusionIcon = R.drawable.confusion;
    public static final int disgustIcon =  R.drawable.disgust;
    public static final int fearIcon = R.drawable.fear;
    public static final int happyIcon = R.drawable.happy;
    public static final int sadIcon = R.drawable.sad;
    public static final int shameIcon = R.drawable.shame;
    public static final int surpriseIcon = R.drawable.surprise;

    //Fixed strings for emotion states
    public static final String angerStr = "Anger";
    public static final String confusionStr = "Confusion";
    public static final String disgustStr =  "Disgust";
    public static final String fearStr = "Fear";
    public static final String happyStr = "Happy";
    public static final String sadStr = "Sad";
    public static final String shameStr = "Shame";
    public static final String surpriseStr = "Surprise";

    //Fixed type of social situations
    public static final String alone ="Alone";
    public static final String withOne = "With one other person";
    public static final String moreThanTwo = "Two to several people";
    public static final String crowd = "With a crowd";

    //Other details
    private String moodState;
    private Integer moodColor;
    private Integer moodIcon;
    private GregorianCalendar dateOfRecord;
    private String triggerText;
    private String socialSituation;
    private String urlPic;
    //private Location location;
    private String belongsTo;
    //private User user;
    //private UserList userList;


    /* To create a mood event, two factors are needed.
        1. A mood state(happy/sad etc.)
        2. A creator ID (unique user ID, integer)
        The constructor will do following:
        1. Initialize with corresponding unique moodState, color and icon.
        2. Add this mood event to corresponding user's moodEvent list.
        Other attributes are added by setters.
     */
    public MoodEvent(String MoodState,String belongsTo) throws MoodStateNotAvailableException {
    //public MoodEvent(String MoodState,Integer belongsTo){
        //userList = new UserList();
        //Initialize moodState, color, icon.
        this.setMoodState(MoodState);
        try{
            this.setColorIcon();
        } catch (MoodStateNotAvailableException e){
            e.printStackTrace();
        }

        this.setBelongsTo(belongsTo);
        //Add to creator user's MoodEventList.
        //user = userList.getUser(belongsTo);
        //user.getMoodEventList().addMoodEvent(this);
    }

    //Set Color and Icon together
    //public void setColorIcon() throws MoodStateNotAvailableException{
    public void setColorIcon() throws MoodStateNotAvailableException{
        String MoodState = this.moodState;
        if (MoodState.equals("Anger")){
            this.moodColor = angerRed;
            this.moodIcon = angerIcon;
        }else if (MoodState.equals("Confusion")){
            this.moodColor = confusionCyan;
            this.moodIcon = confusionIcon;
        }else if (MoodState.equals("Disgust")){
            this.moodColor = disgustGray;
            this.moodIcon = disgustIcon;
        }else if (MoodState.equals("Fear")){
            this.moodColor = fearBlack;
            this.moodIcon = fearIcon;
        }else if (MoodState.equals("Happy")){
            this.moodColor = happyYellow;
            this.moodIcon = happyIcon;
        }else if (MoodState.equals("Sad")){
            this.moodColor = sadBlue;
            this.moodIcon = sadIcon;
        }else if (MoodState.equals("Shame")){
            this.moodColor = shameGreen;
            this.moodIcon = shameIcon;
        }else if (MoodState.equals("Surprise")){
            this.moodColor = surpriseMagenta;
            this.moodIcon = surpriseIcon;
        }else{
            throw new MoodStateNotAvailableException();
        }
    }

    public String getMoodState() {
        return moodState;
    }

    public void setMoodState(String moodState) {
        this.moodState = moodState;
    }

    public Integer getMoodColor() {
        return moodColor;
    }

    public Integer getMoodIcon() {
        return moodIcon;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public String getUrlPic() {
        return urlPic;
    }

    public void setUrlPic(String urlPic) throws ImageTooBigException {
        this.urlPic = urlPic;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) throws InvalidSSException{
        if (socialSituation.equals(alone)||socialSituation.equals(withOne)
                ||socialSituation.equals(moreThanTwo)||socialSituation.equals(crowd)){
            this.socialSituation = socialSituation;
        }else{
            throw new InvalidSSException();
        }
    }

    public String getTriggerText() {
        return triggerText;
    }

    public void setTriggerText(String triggerText) throws TriggerTooLongException {
        if (triggerText.length() >20 ||triggerText.split("\\s+").length >3){
            //\\W+ means all non-word (space, symbol like @ etc.)\\s+ means one or more space
            //Refer to http://stackoverflow.com/questions/13225175/java-string-split-with-a-regex
            //TODO:separate by space or symbol?
            throw new TriggerTooLongException();
        }else{this.triggerText = triggerText;}
    }
    // Reference https://www.mkyong.com/java/java-date-and-calendar-examples/
    // At 2017-02-26 15:00
    public GregorianCalendar getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(GregorianCalendar dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }
}
