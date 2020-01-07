package com.zkyy.icecream.dautil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTImage;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.zkyy.icecream.DaUtils;
import com.zkyy.icecream.R;
import com.zkyy.icecream.callback.DaNativeCallBack;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.constan.AdLoc;
import com.zkyy.icecream.net.NetAddress;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream.ttutil
 * @class describe
 * @time 2019-12-26 18:58
 * @change
 * @chang time
 * @class describe
 */
public class TTNativeUtils {
    private static String TAG = TTNativeUtils.class.getSimpleName() + ": ";

    private static Button mCreativeButton;

    /**
     * 穿山甲原生广告
     *
     * @param activity
     * @param codeIndex
     * @param index
     * @param linearLayout
     * @param daNativeCallBack
     */
    public static void csjLoadNative(final Activity activity, final int codeIndex, final int index, final LinearLayout linearLayout, final DaNativeCallBack daNativeCallBack) {
        TTAdNative adNative = TTAdManagerHolder.get().createAdNative(activity);
        //step4:创建广告请求参数AdSlot,注意其中的setNativeAdtype方法，具体参数含义参考文档
        final AdSlot adSlot = new AdSlot.Builder()
                .setCodeId(DaUtils.getAdCode(codeIndex,index))
                .setSupportDeepLink(true)
//                .setImageAcceptedSize(600, 257)
                .setImageAcceptedSize(MyUtils.px2dp(activity,600), MyUtils.px2dp(activity,257))
                .setNativeAdType(AdSlot.TYPE_BANNER) //请求原生广告时候，请务必调用该方法，设置参数为TYPE_BANNER或TYPE_INTERACTION_AD
                .setAdCount(1)
                .build();

        //step5:请求广告，对请求回调的广告作渲染处理
        adNative.loadNativeAd(adSlot, new TTAdNative.NativeAdListener() {
            @Override
            public void onError(int code, String message) {
                LogUtils.d(TAG + "load error : " + code + ", " + message);
                if (index + 1 <= DaUtils.getAdCodes(codeIndex).size()) {
                    DaNativeLoad.loadNativeWay(activity, codeIndex, index + 1, linearLayout, daNativeCallBack);
                } else {
                    daNativeCallBack.onDaNativeError(code, message);
                }
                NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_REQUEST,AdLoc.REQUEST_FAILED);
            }

            @Override
            public void onNativeAdLoad(List<TTNativeAd> ads) {
                if (ads.get(0) == null) {
                    return;
                }
                View bannerView = LayoutInflater.from(activity).inflate(R.layout.native_ad, linearLayout, false);
                if (bannerView == null) {
                    return;
                }
                NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_REQUEST,AdLoc.REQUEST_SUCCEED);
                if (mCreativeButton != null) {
                    //防止内存泄漏
                    mCreativeButton = null;
                }
                linearLayout.removeAllViews();
                linearLayout.addView(bannerView);
                //绑定原生广告的数据
                setAdData(activity, codeIndex,index,linearLayout, bannerView, ads.get(0), daNativeCallBack);
            }
        });
    }

    private static void setAdData(Activity activity, final int codeIndex, final int index, LinearLayout linearLayout, View nativeView, TTNativeAd nativeAd, final DaNativeCallBack daNativeCallBack) {
        ((TextView) nativeView.findViewById(R.id.tv_native_ad_title)).setText(nativeAd.getTitle());
        ((TextView) nativeView.findViewById(R.id.tv_native_ad_desc)).setText(nativeAd.getDescription());
        ImageView imgDislike = nativeView.findViewById(R.id.img_native_dislike);
        bindDislikeAction(activity, linearLayout, nativeAd, imgDislike,daNativeCallBack);
        if (nativeAd.getImageList() != null && !nativeAd.getImageList().isEmpty()) {
            TTImage image = nativeAd.getImageList().get(0);
            if (image != null && image.isValid()) {
                ImageView im = nativeView.findViewById(R.id.iv_native_image);
                Glide.with(activity).load(image.getImageUrl()).into(im);
            }
        }
        TTImage icon = nativeAd.getIcon();
        if (icon != null && icon.isValid()) {
            ImageView im = nativeView.findViewById(R.id.iv_native_icon);
            Glide.with(activity).load(icon.getImageUrl()).into(im);
        }
        mCreativeButton = (Button) nativeView.findViewById(R.id.btn_native_creative);
        //可根据广告类型，为交互区域设置不同提示信息
        switch (nativeAd.getInteractionType()) {
            case TTAdConstant.INTERACTION_TYPE_DOWNLOAD:
                //如果初始化ttAdManager.createAdNative(getApplicationContext())没有传入activity 则需要在此传activity，否则影响使用Dislike逻辑
                nativeAd.setActivityForDownloadApp(activity);
                mCreativeButton.setVisibility(View.VISIBLE);
                nativeAd.setDownloadListener(mDownloadListener); // 注册下载监听器
                break;
            case TTAdConstant.INTERACTION_TYPE_DIAL:
                mCreativeButton.setVisibility(View.VISIBLE);
                mCreativeButton.setText("立即拨打");
                break;
            case TTAdConstant.INTERACTION_TYPE_LANDING_PAGE:
            case TTAdConstant.INTERACTION_TYPE_BROWSER:
                mCreativeButton.setVisibility(View.VISIBLE);
                mCreativeButton.setText("查看详情");
                break;
            default:
                mCreativeButton.setVisibility(View.GONE);
                LogUtils.d(TAG + "交互类型异常");
        }

        //可以被点击的view, 也可以把nativeView放进来意味整个广告区域可被点击
        List<View> clickViewList = new ArrayList<>();
        clickViewList.add(nativeView);

        //触发创意广告的view（点击下载或拨打电话）
        List<View> creativeViewList = new ArrayList<>();
        //如果需要点击图文区域也能进行下载或者拨打电话动作，请将图文区域的view传入
        //creativeViewList.add(nativeView);
        creativeViewList.add(mCreativeButton);

        //重要! 这个涉及到广告计费，必须正确调用。convertView必须使用ViewGroup。
        nativeAd.registerViewForInteraction((ViewGroup) nativeView, clickViewList, creativeViewList, imgDislike, new TTNativeAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, TTNativeAd ad) {
                if (ad != null) {
                    LogUtils.d(TAG + "广告" + ad.getTitle() + "被点击");
                    daNativeCallBack.onDaNativeClicked(view, ad);
                    NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_CLICK,AdLoc.REQUEST_SUCCEED);
                }
            }

            @Override
            public void onAdCreativeClick(View view, TTNativeAd ad) {
                if (ad != null) {
                    LogUtils.d(TAG + "广告" + ad.getTitle() + "被创意按钮被点击");
                    daNativeCallBack.onDaNativeCreativeClick(view, ad);
                }
            }

            @Override
            public void onAdShow(TTNativeAd ad) {
                if (ad != null) {
                    LogUtils.d(TAG + "广告" + ad.getTitle() + "展示");
                    daNativeCallBack.onDaNativeShow(ad);
                    NetAddress.newUploadingSignAd(codeIndex,index, AdLoc.AD_SHOW,AdLoc.REQUEST_SUCCEED);
                }
            }
        });

    }

    //接入网盟的dislike 逻辑，有助于提示广告精准投放度
    private static void bindDislikeAction(Activity activity, final LinearLayout linearLayout, TTNativeAd ad, View dislikeView, final DaNativeCallBack daNativeCallBack) {
        final TTAdDislike ttAdDislike = ad.getDislikeDialog(activity);
        if (ttAdDislike != null) {
            ttAdDislike.setDislikeInteractionCallback(new TTAdDislike.DislikeInteractionCallback() {
                @Override
                public void onSelected(int position, String value) {
                    linearLayout.removeAllViews();
                    daNativeCallBack.onDaNativeSelected(position, value);
                }

                @Override
                public void onCancel() {
                    daNativeCallBack.onDaNativeCancel();
                }
            });
        }
        dislikeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ttAdDislike != null)
                    ttAdDislike.showDislikeDialog();
            }
        });
    }

    private static final TTAppDownloadListener mDownloadListener = new TTAppDownloadListener() {
        @Override
        public void onIdle() {
            if (mCreativeButton != null) {
                mCreativeButton.setText("开始下载");
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
            if (mCreativeButton != null) {
                if (totalBytes <= 0L) {
                    mCreativeButton.setText("下载中 percent: 0");
                } else {
                    mCreativeButton.setText("下载中 percent: " + (currBytes * 100 / totalBytes));
                }
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
            if (mCreativeButton != null) {
                if (totalBytes <= 0L) {
                    mCreativeButton.setText("下载暂停 percent: 0");
                } else {
                    mCreativeButton.setText("下载暂停 percent: " + (currBytes * 100 / totalBytes));
                }
            }
        }

        @Override
        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
            if (mCreativeButton != null) {
                mCreativeButton.setText("重新下载");
            }
        }

        @Override
        public void onInstalled(String fileName, String appName) {
            if (mCreativeButton != null) {
                mCreativeButton.setText("点击打开");
            }
        }

        @Override
        public void onDownloadFinished(long totalBytes, String fileName, String appName) {
            if (mCreativeButton != null) {
                mCreativeButton.setText("点击安装");
            }
        }
    };
}
