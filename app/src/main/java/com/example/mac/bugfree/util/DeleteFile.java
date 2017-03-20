package com.example.mac.bugfree.util;

/**
 * Created by Zhi Li on 2017/3/8.
 */
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * TODO not ready to use yet!!!!!!!!!!!!!!
 */
public class DeleteFile extends Activity{
    private static final String FILENAME = "file.sav";
    public DeleteFile() {
    }
    public boolean DeleteUserFile(Context context){
        boolean deleted = deleteFile(FILENAME);
//        File file = new File(FILENAME);
//        boolean deleted = file.delete();
//        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));
//        fos.close();
//        boolean deleted = context.deleteFile(FILENAME);
        Log.v("log_tag","deleted: " + deleted);
        return deleted;
    }
}
