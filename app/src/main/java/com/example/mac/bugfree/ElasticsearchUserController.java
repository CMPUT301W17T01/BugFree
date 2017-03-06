package com.example.mac.bugfree;

import android.os.AsyncTask;
import android.support.v7.widget.PopupMenu;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.Bulk;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.IndicesExists;

/**
 * Created by mac on 2017-03-05.
 */

public class ElasticsearchUserController {
    private static JestDroidClient client;


    public static class AddUserTask extends AsyncTask<User, Void, Void> {

        @Override
        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index("CMPUT301W17T01").type("user").id(Integer.toString(user.getUsrID())).build();


                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        // tweets.setId(result.getId)
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

    // TODO we need a function which gets user from elastic search
//    public static class GetUserTask extends AsyncTask<String, Void, User> {
//        @Override
//        protected User doInBackground(String... params) {
//            verifySettings();
//
//            // ??????
//            User user = new User();
//
//            // TODO Build the query
//            Search search = new Search.Builder(params[0])
//                    .addIndex("CMPUT301W17T01")
//                    .addType("user")
//                    .build();
//
//            try {
//                // TODO get the result of the query
//                SearchResult result = client.execute(search);
//                if(result.isSucceeded()) {
//                    user = result.getSourceAsObject(User.class);
//                } else {
//                    Log.i("Error", "The search query failed to find any user matched");
//                }
//            } catch (Exception e) {
//                Log.i("Error", "Somthing went wrong when we tried to communicate with the elasticsearch server!");
//            }
//            return user;
//        }
//    }

    public static class GetUserTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... params) {
            verifySettings();

            User user = new User();

            Get get = new Get.Builder("CMPUT301W17T01", params[0]).type("user").build();

            try{
                JestResult result = client.execute(get);
                user = result.getSourceAsObject(User.class);
            } catch (Exception e) {
                Log.i("Error", "Somthing went wrong when we tried to communicate with the elasticsearch server!");
            }
            return user;
        }
    }

    public static void createIndex() {
        verifySettings();
        try {
            boolean indexExists = client.execute(new IndicesExists.Builder("CMPUT301W17T01").build()).isSucceeded();
            if (!indexExists) {
                client.execute(new CreateIndex.Builder("CMPUT301W17T01").build());
            }
        } catch (Exception e) {
            Log.i("Error", "Somthing went wrong when we tried to communicate with the elasticsearch server!");
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
