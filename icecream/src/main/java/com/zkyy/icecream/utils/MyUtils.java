package com.zkyy.icecream.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.UUID;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.utils
 * @class describe
 * @time 2019-12-26 12:20
 * @change
 * @chang time
 * @class describe
 */
public class MyUtils {

    /**
     * 生成token
     * @return
     */
    public static String GetGUID()
    {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取DisplayMetrics
     *
     * @param context
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
//        return getDisplayMetrics(context).widthPixels;
        return getScreenWidthVersion(context);
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
//        return getDisplayMetrics(context).heightPixels;
        return getScreenHeightVersion(context);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context Context
     * @return 屏幕宽度（px）
     */
    private static int getScreenWidthVersion(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度
     *
     * @param context Context
     * @return 屏幕高度（px）
     */
    private static int getScreenHeightVersion(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }
}
