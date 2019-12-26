package com.zkyy.icecream.dautil;

import android.app.Activity;
import android.widget.FrameLayout;

import com.zkyy.icecream.callback.DaBannerCallBack;
import com.zkyy.icecream.constan.DaAdvertiserType;
import com.zkyy.icecream.ttutil.TTBannerUtils;
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

    public static void loadBanner(Activity activity, DaAdvertiserType daAdvertiserType, String adCode, FrameLayout frameLayout, DaBannerCallBack daBannerCallBack) {
        if (activity == null) {
            LogUtils.d(TAG + "activity不能为空");
            return;
        }
        if (daAdvertiserType == null) {
            LogUtils.d(TAG + "daAdvertiserType不能为空");
            return;
        }
        if (adCode == null) {
            LogUtils.d(TAG + "adCode不能为空");
            return;
        }
        if (frameLayout == null) {
            LogUtils.d(TAG + "frameLayout容器不能为空");
            return;
        }
        if (daBannerCallBack == null) {
            LogUtils.d(TAG + "daBannerCallBack不能为空");
            return;
        }
        switch (daAdvertiserType) {
            case BD:
                break;
            case CSJ:
                TTBannerUtils.csjBannerLoad(activity, adCode, frameLayout, daBannerCallBack);
                break;
            case GDT:
                break;
            case MV:
                break;
        }
    }
}
