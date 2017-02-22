package com.example.mac.bugfree;

import java.util.Calendar;

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
    public static final String angerIcon = "";
    public static final String confusionIcon = "";
    public static final String disgustIcon =  "";
    public static final String fearIcon = "";
    public static final String happyIcon = "";
    public static final String sadIcon = "";
    public static final String shameIcon = "";
    public static final String surpriseIcon = "";

    //Fixed strings for emotion states
    public static final String angerStr = "Anger";
    public static final String confusionStr = "Confusion";
    public static final String disgustStr =  "Disgust";
    public static final String fearStr = "Fear";
    public static final String happyStr = "Happy";
    public static final String sadStr = "Sad";
    public static final String shameStr = "Shame";
    public static final String surpriseStr = "Surprise";

    //Other details
    private String moodState;
    private String moodColor;
    private String moodIcon;
    private Calendar dateOfRecord;
    private String triggerText;
    private String socialSituation;
    private String urlPic;
    //private Loaction location;
    private String belongsTo;

    public MoodEvent() {

    }

    public String getMoodState() {
        return moodState;
    }

    public void setMoodState(String moodState) {
        this.moodState = moodState;
    }

    public String getMoodColor() {
        return moodColor;
    }

    public void setMoodColor(String moodColor) {
        this.moodColor = moodColor;
    }

    public String getMoodIcon() {
        return moodIcon;
    }

    public void setMoodIcon(String moodIcon) {
        this.moodIcon = moodIcon;
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

    public void setUrlPic(String urlPic) {
        this.urlPic = urlPic;
    }

    public String getSocialSituation() {
        return socialSituation;
    }

    public void setSocialSituation(String socialSituation) {
        this.socialSituation = socialSituation;
    }

    public String getTriggerText() {
        return triggerText;
    }

    public void setTriggerText(String triggerText) {
        this.triggerText = triggerText;
    }

    public Calendar getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(Calendar dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }
}
