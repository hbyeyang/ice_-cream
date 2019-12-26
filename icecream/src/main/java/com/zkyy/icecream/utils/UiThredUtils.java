package com.zkyy.icecream.utils;

import android.os.Looper;

/**
 * @author yeyang
 * @name Letu_KBooks_App_Android
 * @class name：com.lt.ad.library.util
 * @class describe
 * @time 2018/9/30 下午2:53
 * @change
 * @chang time
 * @class describe
 */
public class UiThredUtils {
    /**
     * 判断是否在当前主线程
     *
     * @return
     */
    public static boolean isOnMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
