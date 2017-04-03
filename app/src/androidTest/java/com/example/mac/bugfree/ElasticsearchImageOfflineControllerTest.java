package com.example.mac.bugfree;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.controller.ElasticsearchImageOfflineController;

import org.junit.Rule;

import java.util.ArrayList;

/**
 * Created by Zhi Li on 2017/4/2.
 */

public class ElasticsearchImageOfflineControllerTest extends ActivityInstrumentationTestCase2 {

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule =
//            new ActivityTestRule<>(MainActivity.class);
//
//    public ElasticsearchImageOfflineControllerTest(ActivityTestRule<MainActivity> mActivityRule) {
//        this.mActivityRule = mActivityRule;
//    }

    private ArrayList<String> uploadList ;
    private ArrayList<String> deleteList ;
    private ArrayList<String> onlineList ;

    public ElasticsearchImageOfflineControllerTest() {
        super(MainActivity.class);
        uploadList = new ArrayList<String>();
        deleteList = new ArrayList<String>();
        onlineList = new ArrayList<String>();

        uploadList.add("uptest1");
        uploadList.add("uptest2");
        uploadList.add("uptest3");

        deleteList.add("deletetest1");
        deleteList.add("deletetest2");
        deleteList.add("deletetest3");

        onlineList.add("onlinetest1");
        onlineList.add("onlinetest2");
        onlineList.add("onlinetest3");

    }

    public void testSaveLoad(){
        Context context = this.getInstrumentation().getTargetContext().getApplicationContext();
        ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
//        elasticsearchImageOfflineController.prepImageOffline(context,);
    }
}
