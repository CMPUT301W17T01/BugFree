package com.example.mac.bugfree;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

/**
 * Created by mac on 2017-03-07.
 */

public class ElasticsearchMoodEventListController {

    private static JestDroidClient client;


    public static class AddUserTask extends AsyncTask<MoodEventList, Void, Void> {

        @Override
        protected Void doInBackground(MoodEventList... moodEventLists) {
            verifySettings();

            for (MoodEventList moodEventList : moodEventLists) {
                MoodEvent moodEvent = moodEventList.getMoodEvent(0);
                Index index = new Index.Builder(moodEventList).index("cmput301w17t01").type("moodeventlist").id("filter").build();


                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        //Log.d("In AsyncTask ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the user.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the user");
                }

            }
            return null;
        }
    }

    public static class GetUserTask extends AsyncTask<String, Void, MoodEventList> {
        @Override
        protected MoodEventList doInBackground(String... params) {
            verifySettings();

            MoodEventList moodEventList = new MoodEventList();

            //Log.d("parma[0]", params[0]);
            // set id to the current user name
            Get get = new Get.Builder("cmput301w17t01", params[0]).type("moodeventlist").build();

            try{
                JestResult result = client.execute(get);
                moodEventList = result.getSourceAsObject(MoodEventList.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return moodEventList;
        }
    }


    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}
