package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.widget.FrameLayout;

import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.callback.DaBannerCallBack;
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
public class DaBannerLoad {

    private static String TAG = DaBannerLoad.class.getSimpleName() + ": ";

    public static void loadBanner(Activity activity, int posId, FrameLayout frameLayout, DaBannerCallBack daBannerCallBack) {
        LogUtils.d(TAG + "loadBanner");
        int codeIndex = DaUtils.getCodeIndex(posId);
        int index = 0;
        if (codeIndex < 0 || codeIndex > DaUtils.getAdNum()) {
            LogUtils.d("请查看请求的广告位是否已配置");
            if (daBannerCallBack != null) {
                daBannerCallBack.onDaBannerError("请查看请求的广告位是否已配置");
            }
        }
        if (activity == null) {
            LogUtils.d(TAG + "activity不能为空");
            if (daBannerCallBack != null) {
                daBannerCallBack.onDaBannerError("activity不能为空");
            }
            return;
        }
        if (posId == 0) {
            LogUtils.d(TAG + "posId不能为0");
            if (daBannerCallBack != null) {
                daBannerCallBack.onDaBannerError("posId不能为0");
            }
            return;
        }
        if (frameLayout == null) {
            LogUtils.d(TAG + "frameLayout容器不能为空");
            if (daBannerCallBack != null) {
                daBannerCallBack.onDaBannerError("frameLayout容器不能为空");
            }
            return;
        }
        if (daBannerCallBack == null) {
            LogUtils.d(TAG + "daSplashCallBack不能为空");
            if (daBannerCallBack != null) {
                daBannerCallBack.onDaBannerError("daSplashCallBack不能为空");
            }
            return;
        }
        loadBannerWay(activity, codeIndex, index, frameLayout, daBannerCallBack);
    }

    protected static void loadBannerWay(Activity activity, int codeIndex, int index, FrameLayout frameLayout, DaBannerCallBack daBannerCallBack) {
        if (index < DaUtils.getAdCodes(codeIndex).size()) {
            switch (DaUtils.getAdvType(codeIndex, index)) {
                case DaConstan.CSJ:
                    TTBannerUtils.csjBannerLoad(activity, codeIndex, index, frameLayout, daBannerCallBack);
                    break;
                default:
                    if (daBannerCallBack != null) {
                        daBannerCallBack.onDaBannerError("未知错误");
                    }
                    break;
            }
        } else {
            if (daBannerCallBack != null) {
                daBannerCallBack.onDaBannerError("没有更多广告配置了");
            }
        }
    }
}
