package com.example.mac.bugfree.module;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by yipengzhou on 2017/3/23.
 */

public class Image {

    /**
     * I haven't added any return value and parameter of function, you have to add or change after
     * AND maybe some function is public
     */

    private Bitmap src;
    private byte[] imageByteArray = changeImageIntoByteArray(src);
    private String imageBase64 = Base64.encodeToString(imageByteArray,
            Base64.NO_WRAP);

    // Choose one from these two constructors
//    public Image(android.media.Image initialImage) {
//    }

    public Image(String pathOfImage) {
        src= BitmapFactory.decodeFile(pathOfImage);
        changeImageIntoByteArray(src);
    }

    private void checkImageSize() {
        // TODO: check the image size if too large, resize it then transfer into base64
        // TODO: if not too large, transfer into base64
        // TODO: you can transfer image into byteArray first then check size
    }

    private byte[] changeImageIntoByteArray(Bitmap bitmap) {
        // TODO: maybe you need to change image into ByteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void imageToBase64(){
        // TODO: change image or any other type of image into Base64
    }

    private void base64ToImage() {
        // TODO: change base64 String into image and store it
    }

    private void resizeImage() {
        // TODO: resize the image
    }

    private void storeImage() {
        // TODO: store the image which you have transferred from base64
    }

}
