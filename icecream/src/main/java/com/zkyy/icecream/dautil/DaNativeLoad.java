package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zkyy.icecream.callback.DaNativeCallBack;
import com.zkyy.icecream.constan.DaAdvertiserType;
import com.zkyy.icecream.ttutil.TTNativeUtils;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.dautils
 * @class describe
 * @time 2019-12-26 10:59
 * @change
 * @chang time
 * @class describe
 */
public class DaNativeLoad {

    private static String TAG = DaNativeLoad.class.getSimpleName() + ": ";

    private static Button mCreativeButton;

    public static void loadNative(Activity activity, DaAdvertiserType daAdvertiserType, String adCode, LinearLayout linearLayout, DaNativeCallBack daNativeCallBack) {
        switch (daAdvertiserType) {
            case BD:
                break;
            case CSJ:
                TTNativeUtils.csjLoadNative(activity, adCode, linearLayout, daNativeCallBack);
                break;
            case GDT:
                break;
            case MV:
                break;
        }
    }
}
