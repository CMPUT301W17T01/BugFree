package com.example.mac.bugfree;


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
public class MoodEvent {
    /**
     * The constant angerRed.
     */
//Fixed colors
    public static final int angerRed = -65536;
    /**
     * The constant confusionCyan.
     */
    public static final int confusionCyan = -16711681;
    /**
     * The constant disgustGray.
     */
    public static final int disgustGray =  -7829368;
    /**
     * The constant fearBlack.
     */
    public static final int fearBlack = -16777216;
    /**
     * The constant happyYellow.
     */
    public static final int happyYellow = -256;
    /**
     * The constant sadBlue.
     */
    public static final int sadBlue = -16776961;
    /**
     * The constant shameGreen.
     */
    public static final int shameGreen = -16711936;
    /**
     * The constant surpriseMagenta.
     */
    public static final int surpriseMagenta = -65281;

    /**
     * The constant angerIcon.
     */
//Fixed icons
    public static final int angerIcon = R.drawable.anger;
    /**
     * The constant confusionIcon.
     */
    public static final int confusionIcon = R.drawable.confusion;
    /**
     * The constant disgustIcon.
     */
    public static final int disgustIcon =  R.drawable.disgust;
    /**
     * The constant fearIcon.
     */
    public static final int fearIcon = R.drawable.fear;
    /**
     * The constant happyIcon.
     */
    public static final int happyIcon = R.drawable.happy;
    /**
     * The constant sadIcon.
     */
    public static final int sadIcon = R.drawable.sad;
    /**
     * The constant shameIcon.
     */
    public static final int shameIcon = R.drawable.shame;
    /**
     * The constant surpriseIcon.
     */
    public static final int surpriseIcon = R.drawable.surprise;

    /**
     * The constant angerStr.
     */
//Fixed strings for emotion states
    public static final String angerStr = "Anger";
    /**
     * The constant confusionStr.
     */
    public static final String confusionStr = "Confusion";
    /**
     * The constant disgustStr.
     */
    public static final String disgustStr =  "Disgust";
    /**
     * The constant fearStr.
     */
    public static final String fearStr = "Fear";
    /**
     * The constant happyStr.
     */
    public static final String happyStr = "Happy";
    /**
     * The constant sadStr.
     */
    public static final String sadStr = "Sad";
    /**
     * The constant shameStr.
     */
    public static final String shameStr = "Shame";
    /**
     * The constant surpriseStr.
     */
    public static final String surpriseStr = "Surprise";

    /**
     * The constant alone.
     */
//Fixed type of social situations
    public static final String alone ="Alone";
    /**
     * The constant withOne.
     */
    public static final String withOne = "With one other person";
    /**
     * The constant moreThanTwo.
     */
    public static final String moreThanTwo = "Two to several people";
    /**
     * The constant crowd.
     */
    public static final String crowd = "With a crowd";

    //Other details
    private String moodState;
    private Integer moodColor;
    private Integer moodIcon;
    private GregorianCalendar dateOfRecord;
    private GregorianCalendar realtime;
    private String triggerText;
    private String socialSituation;
    private String urlPic;
    //private Location location;
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
    public MoodEvent(String MoodState,String belongsTo) throws MoodStateNotAvailableException {
        //Initialize moodState, color, icon.
        this.setMoodState(MoodState);
        try{
            this.setColorIcon();
        } catch (MoodStateNotAvailableException e){
            e.printStackTrace();
        }

        this.setBelongsTo(belongsTo);

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
     * Gets url pic.
     *
     * @return the url pic
     */
    public String getUrlPic() {
        return urlPic;
    }

    /**
     * Sets url pic.
     *
     * @param urlPic the url pic
     * @throws ImageTooBigException the image too big exception
     */
    public void setUrlPic(String urlPic) throws ImageTooBigException {
        this.urlPic = urlPic;
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
