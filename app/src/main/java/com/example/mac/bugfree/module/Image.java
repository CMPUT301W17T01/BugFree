package com.example.mac.bugfree.module;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 *
 * Created by yipengzhou on 2017/3/23.
 */

public class Image {

    /**
     * I haven't added any return value and parameter of function, you have to add or change after
     * AND maybe some function is public
     */

    private Bitmap bm;
    private byte[] imageByteArray = changeImageIntoByteArray(bm);
    private String imageBase64;
    private int imageByteCount;

    // Choose one from these two constructors
//    public Image(android.media.Image initialImage) {
//    }

    public Image(String pathOfImage) {
        bm= BitmapFactory.decodeFile(pathOfImage);
    }

    private void checkImageSize(Bitmap bm) {
        // TODO: check the image size if too large, resize it then transfer into base64
        // TODO: if not too large, transfer into base64
        // TODO: you can transfer image into byteArray first then check size
        changeImageIntoByteArray(bm);
        imageByteCount = imageByteArray.length;
        if (imageByteCount >= 65536) {
            resizeImage(bm);
            checkImageSize(bm);
        }
        else {
            imageToBase64(bm);
        }
    }

    private byte[] changeImageIntoByteArray(Bitmap bitmap) {
        // TODO: maybe you need to change image into ByteArray
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private String imageToBase64(Bitmap bm){
        // TODO: change image or any other type of image into Base64
        imageBase64 = Base64.encodeToString(changeImageIntoByteArray(bm), Base64.NO_WRAP);
        return imageBase64;
    }

    private Bitmap base64ToImage() {
        // TODO: change base64 String into image and store it
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);
        return decodedByte;
    }

    private void resizeImage(Bitmap bitmap) {
        // TODO: resize the image
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
    }

    private void storeImage() {
        // TODO: store the image which you have transferred from base64
    }

}
