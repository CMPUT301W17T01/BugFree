package com.example.mac.bugfree.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.bugfree.R;
import com.example.mac.bugfree.controller.ElasticsearchUserController;
import com.example.mac.bugfree.controller.ElasticsearchUserListController;
import com.example.mac.bugfree.module.MoodEvent;
import com.example.mac.bugfree.module.MoodEventList;
import com.example.mac.bugfree.module.User;
import com.example.mac.bugfree.module.UserNameList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * This activity provides statistic information to the user.
 * It shows the number of each mood states of the user or
 * the total. It also shows two graphs for user or total.
 *
 * some resource: https://github.com/PhilJay/MPAndroidChart
 * @author Mengyang Chen & Yipeng Zhou
 */

public class StatActivity extends AppCompatActivity {
    private String current_user;
    private User user = new User();
    private ArrayList<Integer> total_list = new ArrayList<Integer>();
    BarChart barChart1;
    BarChart barChart2;

    /** Called when the activity is first created.
     * It creates 16 text views and 2 graph views for showing
     * the numbers and graphs.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_block);
        setSupportActionBar(toolbar);

        TextView stat_anger = (TextView)findViewById(R.id.stat_anger);
        TextView stat_confusion = (TextView)findViewById(R.id.stat_confusion);
        TextView stat_disgust = (TextView)findViewById(R.id.stat_disgust);
        TextView stat_fear = (TextView)findViewById(R.id.stat_fear);
        TextView stat_happy = (TextView)findViewById(R.id.stat_happy);
        TextView stat_sad = (TextView)findViewById(R.id.stat_sad);
        TextView stat_shame = (TextView)findViewById(R.id.stat_shame);
        TextView stat_surprise = (TextView)findViewById(R.id.stat_surprise);

        TextView stat_anger_others = (TextView)findViewById(R.id.stat_anger_others);
        TextView stat_confusion_others = (TextView)findViewById(R.id.stat_confusion_others);
        TextView stat_disgust_others = (TextView)findViewById(R.id.stat_disgust_others);
        TextView stat_fear_others = (TextView)findViewById(R.id.stat_fear_others);
        TextView stat_happy_others = (TextView)findViewById(R.id.stat_happy_others);
        TextView stat_sad_others = (TextView)findViewById(R.id.stat_sad_others);
        TextView stat_shame_others = (TextView)findViewById(R.id.stat_shame_others);
        TextView stat_surprise_others = (TextView)findViewById(R.id.stat_surprise_others);

        barChart1 = (BarChart) findViewById(R.id.bargraph1);
        barChart2 = (BarChart) findViewById(R.id.bargraph2);

        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
        ArrayList<BarEntry> barEntries2 = new ArrayList<>();

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        current_user = pref.getString("currentUser", "");
        String query = current_user;
        ElasticsearchUserController.GetUserTask getUserTask =
                new ElasticsearchUserController.GetUserTask();
        getUserTask.execute(query);

        try{
            user = getUserTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        MoodEventList moodEventList = user.getMoodEventList();
        int angers = get_anger(moodEventList);
        int confusions = get_confusion(moodEventList);
        int disgusts = get_disgust(moodEventList);
        int fears = get_fear(moodEventList);
        int happies = get_happy(moodEventList);
        int sads = get_sad(moodEventList);
        int shames = get_shame(moodEventList);
        int surprises = get_surprise(moodEventList);

        stat_anger.setText(String.valueOf(angers));
        stat_confusion.setText(String.valueOf(confusions));
        stat_disgust.setText(String.valueOf(disgusts));
        stat_fear.setText(String.valueOf(fears));
        stat_happy.setText(String.valueOf(happies));
        stat_sad.setText(String.valueOf(sads));
        stat_shame.setText(String.valueOf(shames));
        stat_surprise.setText(String.valueOf(surprises));

        UserNameList userNameList = new UserNameList();
        ElasticsearchUserListController.GetUserListTask getUserListTask = new ElasticsearchUserListController.GetUserListTask();
        getUserListTask.execute("name");
        try{
            userNameList = getUserListTask.get();
        } catch (Exception e) {
            Log.i("Error", "Failed to get the User out of the async object");
        }
        ArrayList unlist = userNameList.getUserNameList();
        totalCount(unlist);
        stat_anger_others.setText(String.valueOf(total_list.get(0)));
        stat_confusion_others.setText(String.valueOf(total_list.get(1)));
        stat_disgust_others.setText(String.valueOf(total_list.get(2)));
        stat_fear_others.setText(String.valueOf(total_list.get(3)));
        stat_happy_others.setText(String.valueOf(total_list.get(4)));
        stat_sad_others.setText(String.valueOf(total_list.get(5)));
        stat_shame_others.setText(String.valueOf(total_list.get(6)));
        stat_surprise_others.setText(String.valueOf(total_list.get(7)));

        final String[] theMoods = new String[] {"Anger", "Conf", "Disgust", "Fear",
                "Happy", "Sad", "Shame", "Surp"};

        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return theMoods[(int) value];
            }
            public int getDecimalDigits() {  return 0; }
        };

        XAxis xAxis1 = barChart1.getXAxis();
        XAxis xAxis2 = barChart2.getXAxis();
        xAxis1.setGranularity(1f);
        xAxis1.setValueFormatter(formatter);
        xAxis2.setGranularity(1f);
        xAxis2.setValueFormatter(formatter);

        barEntries1.add(new BarEntry(0f,angers));
        barEntries1.add(new BarEntry(1f,confusions));
        barEntries1.add(new BarEntry(2f,disgusts));
        barEntries1.add(new BarEntry(3f, fears));
        barEntries1.add(new BarEntry(4f,happies));
        barEntries1.add(new BarEntry(5f,sads));
        barEntries1.add(new BarEntry(6f,shames));
        barEntries1.add(new BarEntry(7f,surprises));
        BarDataSet barDataSet1 = new BarDataSet(barEntries1,"Moods");

        BarData theData1 = new BarData(barDataSet1);
        theData1.setBarWidth(0.6f);
        barChart1.setData(theData1);
        barChart1.setFitBars(true);
        barChart1.invalidate();

        barEntries2.add(new BarEntry(0f, total_list.get(0)));
        barEntries2.add(new BarEntry(1f, total_list.get(1)));
        barEntries2.add(new BarEntry(2f, total_list.get(2)));
        barEntries2.add(new BarEntry(3f, total_list.get(3)));
        barEntries2.add(new BarEntry(4f, total_list.get(4)));
        barEntries2.add(new BarEntry(5f, total_list.get(5)));
        barEntries2.add(new BarEntry(6f, total_list.get(6)));
        barEntries2.add(new BarEntry(7f, total_list.get(7)));
        BarDataSet barDataSet2 = new BarDataSet(barEntries2,"Moods");

        BarData theData2 = new BarData(barDataSet2);
        theData2.setBarWidth(0.6f);
        barChart2.setData(theData2);
        barChart2.setFitBars(true);
        barChart2.invalidate();

    }

    /**
     * Call when the activity is first created.
     * Create a home button on the action bar of the activity
     * which allows user to click on it and return to the
     * main activity.
     * @param menu: menu
     * @return true
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homebtn, menu);
        return true;
    }

    /**
     * Set the action of the home button.
     * @param item: item
     * @return true
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeBtn:
                setResult(RESULT_OK);
                finish();
                break;

            default:
        }
        return true;
    }


    /**
     *
     * get the count for current user or other selected users for each mood events type
     *
     * @param moodEventList the moodevent list
     * @return count
     */

    private int get_anger(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Anger")){
                count++;
            }
        }
        return count;
    }
    private int get_confusion(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Confusion") ) {
                count++;
            }
        }
        return count;
    }
    private int get_disgust(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Disgust")){
                count++;
            }
        }
        return count;
    }
    private int get_fear(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Fear")) {
                count++;
            }
        }
        return count;
    }
    private int get_happy(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Happy")){
                count++;
            }
        }
        return count;
    }
    private int get_sad(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Sad")) {
                count++;
            }
        }
        return count;
    }
    private int get_shame(MoodEventList moodEventList){
        int x, count=0;

        for(x=0;x<moodEventList.getCount();x++){
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Shame")){
                count++;
            }
        }
        return count;
    }
    private int get_surprise(MoodEventList moodEventList) {
        int x, count = 0;

        for (x = 0; x < moodEventList.getCount(); x++) {
            MoodEvent moodEvent = moodEventList.getMoodEvent(x);
            if (moodEvent.getMoodState().equals("Surprise")) {
                count++;
            }
        }
        return count;
    }

    /**
     * Calculate the total count for each mood events
     * store them as an integer array lis
     *
     * @param unlist username list
     */
    private void totalCount(ArrayList unlist){
        int i;
        int angers_total=0, confusions_total=0,disgusts_total=0,fears_total=0,
                happies_total=0, sads_total=0, shames_total=0, surprises_total=0;
        for (i=0;i<unlist.size();i++) {
            String total_user = unlist.get(i).toString();
            ElasticsearchUserController.GetUserTask getUserTask =
                    new ElasticsearchUserController.GetUserTask();
            getUserTask.execute(total_user);

            try{
                user = getUserTask.get();
            } catch (Exception e) {
                Log.i("Error", "Failed to get the User out of the async object");
            }
            MoodEventList total_moodEventList = user.getMoodEventList();
            angers_total+=get_anger(total_moodEventList);
            confusions_total+=get_confusion(total_moodEventList);
            disgusts_total+=get_disgust(total_moodEventList);
            fears_total+=get_fear(total_moodEventList);
            happies_total+=get_happy(total_moodEventList);
            sads_total+=get_sad(total_moodEventList);
            shames_total+=get_shame(total_moodEventList);
            surprises_total+=get_surprise(total_moodEventList);
        }
        total_list.add(angers_total);
        total_list.add(confusions_total);
        total_list.add(disgusts_total);
        total_list.add(fears_total);
        total_list.add(happies_total);
        total_list.add(sads_total);
        total_list.add(shames_total);
        total_list.add(surprises_total);

    }


}
