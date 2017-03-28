package com.example.mac.bugfree.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mac.bugfree.module.Image;
import com.example.mac.bugfree.module.ImageForElasticSearch;
import com.example.mac.bugfree.module.User;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Get;
import io.searchbox.core.Index;

/**
 * Created by mac on 2017-03-25.
 */

public class ElasticsearchImageController {
    private static JestDroidClient client;

    /**
     * The function which add image to elastic search
     */
    public static class AddImageTask extends AsyncTask<ImageForElasticSearch, Void, String> {

        @Override
        protected String doInBackground(ImageForElasticSearch... images) {
            verifySettings();

            String uniqueID = "";

            for (ImageForElasticSearch image : images) {
                Index index = new Index.Builder(image).index("cmput301w17t01").type("image").build();

                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        uniqueID = result.getId();
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the image.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the image");
                }

            }
            return uniqueID;
        }
    }


    /**
     * The function which add image to elastic search when create image offline
     */
    public static class AddImageOfflineTask extends AsyncTask<ImageForElasticSearch, Void, Void> {

        @Override
        protected Void doInBackground(ImageForElasticSearch... images) {
            verifySettings();


            for (ImageForElasticSearch image : images) {
                Index index = new Index.Builder(image).index("cmput301w17t01").type("image").id(image.getUniqueId()).build();

                try {
                    // where is the client
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the image.");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the image");
                }

            }
            return null;
        }
    }





    /**
     * The function which delete image from elastic search
     */
    public static class DeleteImageTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            verifySettings();

            Delete delete = new Delete.Builder(params[0]).index("cmput301w17t01").type("image").build();
            try {
                // where is the client
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                } else {
                    Log.i("Error", "Elasticsearch was not able to add the image.");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the image");
            }
            return null;
        }
    }

    /**
     * The function which get image from elastic search
     */
    public static class GetImageTask extends AsyncTask<String, Void, ImageForElasticSearch> {
        @Override
        protected ImageForElasticSearch doInBackground(String... params) {
            verifySettings();

            ImageForElasticSearch imageForElasticSearch = new ImageForElasticSearch();
            Get get = new Get.Builder("cmput301w17t01", params[0]).type("image").build();

            try{
                JestResult result = client.execute(get);
                imageForElasticSearch = result.getSourceAsObject(ImageForElasticSearch.class);
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return imageForElasticSearch;
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
