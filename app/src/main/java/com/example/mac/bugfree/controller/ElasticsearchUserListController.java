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
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
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
     * The function which add username to elastic search
     */
    public static class AddUserListTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... userNames) {
            verifySettings();

            for (String userName : userNames) {
                Index index = new Index.Builder(userName).index("cmput301w17t01").type("username").build();
                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    Log.d("In AsyncTask ID", result.getId());
                    if (result.isSucceeded()) {
                    } else {
                        Log.i("Error", "ElasticSearch was not able to add the userList.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the userName");
                }

            }
            return null;
        }
    }

    /**
     * The function which get username from elastic search
     */
    public static class GetUserListTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            verifySettings();

            ArrayList<String> userList = new ArrayList<>();
//            Get get = new Get.Builder("cmput301w17t01", params[0]).type("username").build();

            Search search = new Search.Builder(params[0])
                    .addIndex("cmput301w17t01")
                    .addType("username")
                    .build();

            try{
//                JestResult result = client.execute(get);
                SearchResult result = client.execute(search);
                if(result.isSucceeded()) {
                    List<String> nameList = result.getSourceAsObjectList(String.class);
                    userList.addAll(nameList);
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticSearch server!");
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
