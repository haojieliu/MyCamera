package com.cmrx.haojieliu.mycamera;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by admin on 2017/12/12.
 *
 * @Email liuhj@cm-rx.com
 */

public class ImageUtil {
    /**
     * 旋转Bitmap
     * @param bitmap
     * @param rotateDegree
     * @return
     */
    public static Bitmap getRotateBitmap(Bitmap bitmap, float rotateDegree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return rotateBitmap;
    }
}
