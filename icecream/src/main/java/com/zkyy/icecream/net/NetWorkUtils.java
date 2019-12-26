package com.zkyy.icecream.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author yeyang
 * @name AdDemo
 * @class name：com.lt.bc.mb.qmks.net
 * @class describe
 * @time 2018/9/25 上午10:43
 * @change
 * @chang time
 * @class describe
 */
public class NetWorkUtils {
    //判断网络是否连接
    public static boolean isNetWorkAvailable(Context context) {
        //网络连接管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //网络信息
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null) {
            return true;
        }
        return false;
    }
}
