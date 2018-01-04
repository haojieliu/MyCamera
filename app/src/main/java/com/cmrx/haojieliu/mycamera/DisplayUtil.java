package com.cmrx.haojieliu.mycamera;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import java.lang.reflect.Method;

/**
 * Created by admin on 2017/12/12.
 *
 * @Email liuhj@cm-rx.com
 */

public class DisplayUtil {
    private static final String TAG = "Liu_DisplayUtil";

    /**
     * dp转px
     *
     * @param context  上下文
     * @param dipValue dp值
     * @return px
     */
    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dp
     * @param context
     * @param pxValue
     * @return dp
     */
    public static int px2dip(Context context, int pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽高，单位为px
     * @param context
     * @return
     */
    public static Point getScreenMetrics(Context context){
        DisplayMetrics dm =context.getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        return new Point(w_screen, h_screen);
    }

    /**
     * 获取屏幕长宽比
     * @param context
     * @return
     */
    public static float getScreenRate(Context context){
        Point P = getScreenMetrics(context);
        float H = P.y;
        float W = P.x;
        return (H/W);
    }

    /**
     * 屏幕参数
     * @param context
     * @return
     */
    public  int getRealHeight(Context context){
        Display defaultDisplay = ((MainActivity) context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        try {
            Class c=Class.forName("android.view.Display");
            Method getRealMetrics = c.getMethod("getRealMetrics", DisplayMetrics.class);
            getRealMetrics.invoke(defaultDisplay,displayMetrics);
            defaultDisplay.getHeight();
            return displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
