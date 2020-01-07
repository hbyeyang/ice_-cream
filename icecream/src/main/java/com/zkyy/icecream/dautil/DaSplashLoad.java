package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.widget.FrameLayout;

import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.callback.DaSplashCallBack;
import com.zkyy.icecream.constan.DaConstan;
import com.zkyy.icecream.utils.LogUtils;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.dautils
 * @class describe
 * @time 2019-12-26 10:58
 * @change
 * @chang time
 * @class describe
 */
public class DaSplashLoad {
    private static String TAG = DaSplashLoad.class.getSimpleName() + ": ";


    public static void loadSplash(Activity activity, int posId, FrameLayout frameLayout, DaSplashCallBack daSplashCallBack) {
        LogUtils.d(TAG + "loadSplash");
        int codeIndex = DaUtils.getCodeIndex(posId);
        int index = 0;
        if (codeIndex < 0 || codeIndex > DaUtils.getAdNum()) {
            LogUtils.d("请查看请求的广告位是否已配置");
            if (daSplashCallBack != null) {
                daSplashCallBack.onDaError("请查看请求的广告位是否已配置");
            }
        }
        if (activity == null) {
            LogUtils.d(TAG + "activity不能为空");
            if (daSplashCallBack != null) {
                daSplashCallBack.onDaError("activity不能为空");
            }
            return;
        }
        if (posId == 0) {
            LogUtils.d(TAG + "posId不能为0");
            if (daSplashCallBack != null) {
                daSplashCallBack.onDaError("posId不能为0");
            }
            return;
        }
        if (frameLayout == null) {
            LogUtils.d(TAG + "frameLayout容器不能为空");
            if (daSplashCallBack != null) {
                daSplashCallBack.onDaError("frameLayout容器不能为空");
            }
            return;
        }
        if (daSplashCallBack == null) {
            LogUtils.d(TAG + "daSplashCallBack不能为空");
            if (daSplashCallBack != null) {
                daSplashCallBack.onDaError("daSplashCallBack不能为空");
            }
            return;
        }

        loadSplashWay(activity, codeIndex, index, frameLayout, daSplashCallBack);
    }

    protected static void loadSplashWay(Activity activity, int codeIndex, int index, FrameLayout frameLayout, DaSplashCallBack daSplashCallBack) {
        if (index < DaUtils.getAdCodes(codeIndex).size()) {
            switch (DaUtils.getAdvType(codeIndex, index)) {
                case DaConstan.CSJ:
                    TTSplashUtils.loadCsjSplash(activity, codeIndex, index, frameLayout, daSplashCallBack);
                    break;
                default:
                    if (daSplashCallBack != null) {
                        daSplashCallBack.onDaError("未知错误");
                    }
                    break;
            }
        } else {
            if (daSplashCallBack != null) {
                daSplashCallBack.onDaError("没有更多广告配置了");
            }
        }
    }
}
