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
 * Created by mac on 2017-03-06.
 * @author Ray Chen
 */

public class ElasticsearchTotalNumController {
    private static JestDroidClient client;


    public static class AddNumTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            verifySettings();

            for (String string : strings) {
                Index index = new Index.Builder(string).index("cmput301w17t01").type("string").id("0").build();


                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.d("In AsyncTask ID", result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the Integer.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the Integer");
                }

            }
            return null;
        }
    }

    public static class GetNumTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            verifySettings();

            String total_num = "";

            Get get = new Get.Builder("cmput301w17t01", "0").type("string").build();

            try{
                JestResult result = client.execute(get);
                total_num = result.getSourceAsObject(String.class);
            } catch (Exception e) {
                Log.i("Error", "Somthing went wrong when we tried to communicate with the elasticsearch server!");
            }
            return total_num;
        }
    }

    public static void createIndex() {
        verifySettings();
        try {
            boolean indexExists = client.execute(new IndicesExists.Builder("cmput301w17t01").build()).isSucceeded();
            if (indexExists) {
                client.execute(new DeleteIndex.Builder("cmput301w17t01").build());
            }
            client.execute(new CreateIndex.Builder("cmput301w17t01").build());
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
