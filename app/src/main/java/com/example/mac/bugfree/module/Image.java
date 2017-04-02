package com.example.mac.bugfree.module;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.example.mac.bugfree.util.SaveFile;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * This class stores all image related info. It convert a image into
 * byte array and check the size of the image. If the size is too
 * bit, then resize it and convert it into base 64 format. It can also
 * convert a base64 format image back to image.
 * @author Yipeng Zhou
 */

public class Image {


    private Bitmap bm;
    private byte[] imageByteArray;
    private String imageBase64;
    private int imageByteCount;

    public Image(Bitmap bitmap) {
        bm = bitmap;
        checkImageSize(bm);
    }

    public Image(String pathOfImage) {
        bm= BitmapFactory.decodeFile(pathOfImage);
        checkImageSize(bm);
    }

    private void checkImageSize(Bitmap bm) {
        imageByteArray = changeImageIntoByteArray(bm);
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
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private void imageToBase64(Bitmap bm){
        imageBase64 = Base64.encodeToString(changeImageIntoByteArray(bm), Base64.NO_WRAP);
    }

    public Bitmap base64ToImage() {
        byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);

        return decodedByte;
    }

    private void resizeImage(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
    }

    public String getImageBase64() {
        return imageBase64;
    }

}
