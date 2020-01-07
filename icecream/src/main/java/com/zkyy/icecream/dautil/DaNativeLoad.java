package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.widget.LinearLayout;

import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.callback.DaNativeCallBack;
import com.zkyy.icecream.constan.DaConstan;
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

    public static void loadNative(Activity activity, int posId, LinearLayout linearLayout, DaNativeCallBack daNativeCallBack) {
        LogUtils.d(TAG + "loadNative");
        int codeIndex = DaUtils.getCodeIndex(posId);
        int index = 0;
        if (codeIndex < 0 || codeIndex > DaUtils.getAdNum()) {
            LogUtils.d("请查看请求的广告位是否已配置");
            if (daNativeCallBack != null) {
                daNativeCallBack.onDaNativeError("请查看请求的广告位是否已配置");
            }
        }
        if (activity == null) {
            LogUtils.d(TAG + "activity不能为空");
            if (daNativeCallBack != null) {
                daNativeCallBack.onDaNativeError("activity不能为空");
            }
            return;
        }
        if (posId == 0) {
            LogUtils.d(TAG + "posId不能为0");
            if (daNativeCallBack != null) {
                daNativeCallBack.onDaNativeError("posId不能为0");
            }
            return;
        }
        if (linearLayout == null) {
            LogUtils.d(TAG + "linearLayout容器不能为空");
            if (daNativeCallBack != null) {
                daNativeCallBack.onDaNativeError("linearLayout容器不能为空");
            }
            return;
        }
        if (daNativeCallBack == null) {
            LogUtils.d(TAG + "daSplashCallBack不能为空");
            if (daNativeCallBack != null) {
                daNativeCallBack.onDaNativeError("daSplashCallBack不能为空");
            }
            return;
        }
        loadNativeWay(activity, codeIndex, index, linearLayout, daNativeCallBack);
    }

    protected static void loadNativeWay(Activity activity, int codeIndex, int index, LinearLayout linearLayout, DaNativeCallBack daNativeCallBack) {
        if (index < DaUtils.getAdCodes(codeIndex).size()) {
            switch (DaUtils.getAdvType(codeIndex, index)) {
                case DaConstan.CSJ:
                    TTNativeUtils.csjLoadNative(activity, codeIndex, index, linearLayout, daNativeCallBack);
                    break;
                default:
                    if (daNativeCallBack != null) {
                        daNativeCallBack.onDaNativeError("未知错误");
                    }
                    break;
            }
        } else {
            if (daNativeCallBack != null) {
                daNativeCallBack.onDaNativeError("没有更多广告配置了");
            }
        }
    }
}
