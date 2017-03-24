package com.example.mac.bugfree.module;

/**
 * Created by yipengzhou on 2017/3/23.
 */

public class Image {

    /**
     * I haven't added any return value and parameter of function, you have to add or change after
     * AND maybe some function is public
     */

    private String imageBase64;

    // Choose one from these two constructors
    public Image(android.media.Image initialImage) {
    }

    public Image(String pathOfImage) {

    }

    private void checkImageSize() {
        // TODO: check the image size if too large, resize it then transfer into base64
        // TODO: if not too large, transfer into base64
        // TODO: you can transfer image into byteArray first then check size
    }

    private void changeImageIntoByteArray() {
        // TODO: maybe you need to change image into ByteArray
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
