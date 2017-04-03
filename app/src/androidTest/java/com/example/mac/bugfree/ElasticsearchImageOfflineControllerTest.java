package com.example.mac.bugfree;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.mac.bugfree.activity.MainActivity;
import com.example.mac.bugfree.controller.ElasticsearchImageController;
import com.example.mac.bugfree.controller.ElasticsearchImageOfflineController;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.MoodEventList;
import com.google.gson.Gson;

import org.junit.Rule;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Zhi Li on 2017/4/2.
 */

public class ElasticsearchImageOfflineControllerTest extends ActivityInstrumentationTestCase2 {

    private static final String IMAGEONLINE = "ImageOnlineList.sav";
    private static final String IMAGEUPLOADLIST = "ImageUploadList.sav";
    private static final String IMAGEDELETELIST = "ImageDeleteList.sav";

    private ArrayList<String> uploadList ;
    private ArrayList<String> deleteList ;
    private ArrayList<String> onlineList ;

    public ElasticsearchImageOfflineControllerTest() {
        super(MainActivity.class);

    }

    public void testSaveList(){
        try {
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

            Context context = this.getInstrumentation().getTargetContext().getApplicationContext();

            saveFile(context);

            ArrayList<String> uploadList1 = new ArrayList<String>();
            ArrayList<String> deleteList1 =  new ArrayList<String>();
            ArrayList<String> onlineList1 =  new ArrayList<String>();

            ElasticsearchImageOfflineController elasticsearchImageOfflineController = new ElasticsearchImageOfflineController();
            uploadList1 = elasticsearchImageOfflineController.loadImageList(context,"upload");
            deleteList1 = elasticsearchImageOfflineController.loadImageList(context,"delete");
            onlineList1 = elasticsearchImageOfflineController.loadImageList(context,"online");

            for(String Id :uploadList1){
                Log.i("uploadList1",Id);
            }
            for(String Id :deleteList1){
                Log.i("deleteList1",Id);
            }
            for(String Id :onlineList1){
                Log.i("onlineList1",Id);
            }
            assertTrue(uploadList1.equals(uploadList));
            assertTrue(deleteList1.equals(deleteList));
            assertTrue(onlineList1.equals(onlineList));
            clearFile(context);

        } catch (Exception e){
            Log.i("imageofflinecontroller","File not saved");
        }
    }
    private void saveFile(Context context){
        try {
            FileOutputStream fos0 = context.openFileOutput(IMAGEONLINE, Context.MODE_PRIVATE);
            BufferedWriter out0 = new BufferedWriter(new OutputStreamWriter(fos0));

//            ArrayList<String> alreadyUp = new ArrayList<String>();

            Gson gson0 = new Gson();
            gson0.toJson(onlineList, out0);
            out0.flush();
            fos0.close();

            // Arraylist, [strings of [image unique id]]] to be uploaded to the ElasticSearch
            FileOutputStream fos1 = context.openFileOutput(IMAGEUPLOADLIST, Context.MODE_PRIVATE);
            BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(fos1));

//            ArrayList<String> upload = new ArrayList<String>();
            Gson gson1 = new Gson();
            gson1.toJson(uploadList, out1);
            out1.flush();
            fos1.close();

            // Arraylist, [strings of [image unique id]]] to be deleted from ElasticSearch
            FileOutputStream fos2 = context.openFileOutput(IMAGEDELETELIST, Context.MODE_PRIVATE);
            BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(fos2));

//            ArrayList<String> delete = new ArrayList<String>();
            Gson gson2 = new Gson();
            gson2.toJson(deleteList, out2);
            out2.flush();
            fos2.close();
        } catch(Exception e){
            Log.i("imageofflinecontroller","File not saved");
        }
    }

    private void clearFile(Context context){
        try {
            FileOutputStream fos0 = context.openFileOutput(IMAGEONLINE, Context.MODE_PRIVATE);
            BufferedWriter out0 = new BufferedWriter(new OutputStreamWriter(fos0));

            ArrayList<String> alreadyUp = new ArrayList<String>();

            Gson gson0 = new Gson();
            gson0.toJson(alreadyUp, out0);
            out0.flush();
            fos0.close();

            // Arraylist, [strings of [image unique id]]] to be uploaded to the ElasticSearch
            FileOutputStream fos1 = context.openFileOutput(IMAGEUPLOADLIST, Context.MODE_PRIVATE);
            BufferedWriter out1 = new BufferedWriter(new OutputStreamWriter(fos1));

            ArrayList<String> upload = new ArrayList<String>();
            Gson gson1 = new Gson();
            gson1.toJson(upload, out1);
            out1.flush();
            fos1.close();

            // Arraylist, [strings of [image unique id]]] to be deleted from ElasticSearch
            FileOutputStream fos2 = context.openFileOutput(IMAGEDELETELIST, Context.MODE_PRIVATE);
            BufferedWriter out2 = new BufferedWriter(new OutputStreamWriter(fos2));

            ArrayList<String> delete = new ArrayList<String>();
            Gson gson2 = new Gson();
            gson2.toJson(delete, out2);
            out2.flush();
            fos2.close();
        } catch(Exception e){
            Log.i("imageofflinecontroller","File not saved");
        }
    }
}
