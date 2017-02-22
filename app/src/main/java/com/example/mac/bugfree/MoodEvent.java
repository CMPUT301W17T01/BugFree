package com.example.mac.bugfree;

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
    public static final String angerIcon = "Anger.png";
    public static final String confusionIcon = "Confusion.png";
    public static final String disgustIcon =  "Disgust.png";
    public static final String fearIcon = "Fear.png";
    public static final String happyIcon = "Happy.png";
    public static final String sadIcon = "Sad.png";
    public static final String shameIcon = "Shame.png";
    public static final String surpriseIcon = "Surprise.png";

    //Fixed strings for emotion states
    public static final String angerStr = "Anger";
    public static final String confusionStr = "Confusion";
    public static final String disgustStr =  "Disgust";
    public static final String fearStr = "Fear";
    public static final String happyStr = "Happy";
    public static final String sadStr = "Sad";
    public static final String shameStr = "Shame";
    public static final String surpriseStr = "Surprise";

//    //Fixed type of social situations
//    public static final String alone ="Alone";
//    public static final String withOne = "With one other person";
//    public static final String moreThanTwo = "Two to several people";
//    public static final String crowd = "With a crowd";

    //Other details
    private String moodState;
    private Integer moodColor;
    private String moodIcon;
    private Calendar dateOfRecord;
    private String triggerText;
    private String socialSituation;
    private String urlPic;
    //private Loaction location;
    private String belongsTo;

    public MoodEvent(String MoodState) {
        setMoodState(MoodState);
        setColorIcon();
    }
//Set Color and Icon together
    public void setColorIcon() {
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
//        if (socialSituation.equals(alone)){
//
//        }else if(socialSituation.equals(withOne)){
//
//        }else if(socialSituation.equals(moreThanTwo)){
//
//        }else if(socialSituation.equals(crowd)){
//
//        }
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
