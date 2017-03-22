package com.example.mac.bugfree.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mac.bugfree.module.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Update;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

/**
 * Created by mac on 2017-03-21.
 */

public class ElasticsearchUserListController {
    private static JestDroidClient client;


    /**
     * The function which add user to elastic search
     */
    public static class AddUserListTask extends AsyncTask<ArrayList<String>, Void, Void> {

        @Override
        protected Void doInBackground(ArrayList<String>... arrayLists) {
            verifySettings();

            for (ArrayList<String> user : arrayLists) {
                Index index = new Index.Builder(user).index("cmput301w17t01").type("userlist").id("1").build();


                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
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

    /**
     * The function which get user from elastic search
     */
    public static class GetUserTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            verifySettings();

            ArrayList<String> userList = new ArrayList<String>();
            Get get = new Get.Builder("cmput301w17t01", params[0]).type("userlist").build();

            try{
                JestResult result = client.execute(get);
                userList = result.getSourceAsObject(ArrayList.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return userList;
        }
    }


    /**
     * Verify settings.
     */
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
