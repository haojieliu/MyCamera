package com.cmrx.haojieliu.mycamera;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2017/12/12.
 *
 * @Email liuhj@cm-rx.com
 */

public class FileUtil {
    private static final String TAG = "Liu_FileUtil";
    private static final String parentPath_File = Environment.getExternalStorageDirectory().getAbsolutePath();

    public void initPath() {

    }

    public static void saveBitmap(Bitmap bitmap) {
        String picName = parentPath_File + "/" + System.currentTimeMillis() + ".jpg";
        try {
            FileOutputStream outputStream = new FileOutputStream(picName);
            BufferedOutputStream outputBufferStream = new BufferedOutputStream(outputStream);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputBufferStream);
            outputBufferStream.flush();
            outputBufferStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
