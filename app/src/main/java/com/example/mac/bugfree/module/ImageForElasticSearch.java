package com.example.mac.bugfree.module;

/**
 * Created by mac on 2017-03-25.
 */

public class ImageForElasticSearch {

    private String imageBase64;

    public ImageForElasticSearch(String base64) {
        this.imageBase64 = base64;
    }

    public ImageForElasticSearch(){

    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

}
