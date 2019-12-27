package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zkyy.icecream.callback.DaNativeCallBack;
import com.zkyy.icecream.constan.DaAdvertiserType;
import com.zkyy.icecream.ttutil.TTNativeUtils;
import com.zkyy.icecream.utils.LogUtils;

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
        if (activity == null && linearLayout != null) {
            LogUtils.d(TAG + "activity不能为空");
            linearLayout.setVisibility(View.GONE);
            return;
        }
        if (daAdvertiserType == null && linearLayout != null) {
            LogUtils.d(TAG + "daAdvertiserType不能为空");
            linearLayout.setVisibility(View.GONE);
            return;
        }
        if (adCode == null && linearLayout != null) {
            LogUtils.d(TAG + "adCode不能为空");
            linearLayout.setVisibility(View.GONE);
            return;
        }
        if (linearLayout == null) {
            LogUtils.d(TAG + "linearLayout容器不能为空");
            return;
        }
        if (daNativeCallBack == null && linearLayout != null) {
            LogUtils.d(TAG + "daBannerCallBack不能为空");
            linearLayout.setVisibility(View.GONE);
            return;
        }
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
