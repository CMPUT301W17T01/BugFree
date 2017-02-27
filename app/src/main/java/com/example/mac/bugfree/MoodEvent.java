package com.example.mac.bugfree;

import android.widget.Toast;

import java.util.Calendar;
import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mac on 2017-02-21.
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
    public static final String angerIcon = "anger.png";
    public static final String confusionIcon = "confusion.png";
    public static final String disgustIcon =  "disgust.png";
    public static final String fearIcon = "fear.png";
    public static final String happyIcon = "happy.png";
    public static final String sadIcon = "sad.png";
    public static final String shameIcon = "shame.png";
    public static final String surpriseIcon = "surprise.png";

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
    private String moodIcon;
    private GregorianCalendar dateOfRecord;
    private String triggerText;
    private String socialSituation;
    private String urlPic;
    //private Loaction location;
    private Integer belongsTo;
    private User user;
    private UserList userList;

    /* To create a mood event, two factors are needed.
        1. A mood state(happy/sad etc.)
        2. A creator ID (unique user ID, integer)
        The constructor will do following:
        1. Initialize with corresponding unique moodState, color and icon.
        2. Add this mood event to corresponding user's moodEvent list.
        Other attributes are added by setters.
     */
    public MoodEvent(String MoodState,Integer belongsTo) throws MoodStateNotAvailableException{
        userList = new UserList();
        //Initialize moodState, color, icon.
        this.setMoodState(MoodState);
        try{this.setColorIcon();
        }catch (MoodStateNotAvailableException e){
        }
        this.setBelongsTo(belongsTo);
        //Add to creator user's MoodEventList.
        user = userList.getUser(belongsTo);
        user.getMoodEventList().addMoodEvent(this);
    }
//Set Color and Icon together
    public void setColorIcon() throws MoodStateNotAvailableException{
        String MoodState = this.moodState;
        if (MoodState.equals("Anger")){
            this.moodColor = this.angerRed;
            this.moodIcon = this.angerIcon;
        }else if (MoodState.equals("Confusion")){
            this.moodColor = this.confusionCyan;
            this.moodIcon = this.confusionIcon;
        }else if (MoodState.equals("Disgust")){
            this.moodColor = this.disgustGray;
            this.moodIcon = this.disgustIcon;
        }else if (MoodState.equals("Fear")){
            this.moodColor = this.fearBlack;
            this.moodIcon = this.fearIcon;
        }else if (MoodState.equals("Happy")){
            this.moodColor = this.happyYellow;
            this.moodIcon = this.happyIcon;
        }else if (MoodState.equals("Sad")){
            this.moodColor = this.sadBlue;
            this.moodIcon = this.sadIcon;
        }else if (MoodState.equals("Shame")){
            this.moodColor = this.shameGreen;
            this.moodIcon = this.shameIcon;
        }else if (MoodState.equals("Surprise")){
            this.moodColor = this.surpriseMagenta;
            this.moodIcon = this.surpriseIcon;
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

    public String getMoodIcon() {
        return moodIcon;
    }

    public Integer getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(Integer belongsTo) {
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
