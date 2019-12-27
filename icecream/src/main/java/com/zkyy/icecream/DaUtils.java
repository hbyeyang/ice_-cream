package com.zkyy.icecream;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.blankj.utilcode.util.Utils;
import com.zkyy.icecream.bean.AdConfigBean;
import com.zkyy.icecream.config.TTAdManagerHolder;
import com.zkyy.icecream.constan.AdLoc;
import com.zkyy.icecream.constan.PhoneConstan;
import com.zkyy.icecream.net.GsonObjectCallback;
import com.zkyy.icecream.net.NetAddress;
import com.zkyy.icecream.net.OkHttp3Utils;
import com.zkyy.icecream.utils.HttpParameters;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.PhoneInfoUtils;
import com.zkyy.icecream.utils.SPAdUtils;
import com.zkyy.icecream.utils.ThreadPool;
import com.zkyy.icecream.utils.UiThredUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.zkyy.icecream
 * @class describe
 * @time 2019-12-25 16:29
 * @change
 * @chang time
 * @class describe
 */
public class DaUtils {

    public static boolean allow = BuildConfig.DEBUG;
    /**
     * false 为线上地址，true 为测试地址
     */
    public static boolean netadress = BuildConfig.DEBUG;


    private static DaUtils mInstans;

    public Application mContext;
    /**
     * 时候获取到广告配置标示
     */
    public volatile boolean isInit = false;
    /**
     * 广告配置
     */
    private AdConfigBean mAdConfigBean;

    /**
     * 渠道（下游，每个应用唯一）
     */
    private String mAchannel;
    /**
     * 推广渠道
     */
    private String mChannel;
//    private String mPackageName;

    /**
     * 获取渠道（下游）
     * @return
     */
    public String getAchannel() {
        return mAchannel;
    }

    /**
     * 推广渠道
     * @return
     */
    public String getChannel() {
        return mChannel;
    }

    /**
     * 应用包名
     * @return
     */
//    public String getPackageName() {
//        return mPackageName;
//    }

    /**
     *
     * @param boo   true    打印日志    false   不打印日志
     * @param flag  false 为线上地址，true 为测试地址
     */
    public static void setLoggable(Boolean boo,Boolean flag) {
        allow = boo;
        netadress = flag;
    }

    private DaUtils() {
        try {
            final Application app = Utils.getApp();
            mContext = app;
            mAdConfigBean = (AdConfigBean) SPAdUtils.getObject(app, AdLoc.AD_CONFIG);
            if (mAdConfigBean != null) {
                isInit = true;
            }
        } catch (Exception ex) {

        }
    }

    public static DaUtils getInstance() {
        if (mInstans == null) {
            //加同步安全
            synchronized (DaUtils.class) {
                if (mInstans == null) {
                    mInstans = new DaUtils();
                }
            }
        }
        return mInstans;
    }


    /**
     * 获取广告配置
     * @param application
     * @param achannel  请联系商务或者开发者获取渠道
     * @param channel   应用推广的渠道，需要先配置才能生效
     */
    public void getAdUtils(Application application,String achannel,String channel) {
        mContext = application;
        mAchannel = achannel;
        mChannel = channel;
        Utils.init(application);
        LogUtils.d("TTAdManagerHolder初始化");
        TTAdManagerHolder.init(mContext,"5001121");
        if (mAdConfigBean == null) {
            mAdConfigBean = (AdConfigBean) SPAdUtils.getObject(application, AdLoc.AD_CONFIG);
            if(mAdConfigBean != null) {
                isInit = true;
            }
        }
        if (!PhoneInfoUtils.getNetWorkType(application).equals("unknow")) {
            //判断本地有没有数据，没有就取网上进行配置，有的话也请求网上，下次生效
            getAdConfig(application);
            uploadAdError(application);
        }
    }

    /**
     * 单开个线程上传广告错误日志
     *
     * @param application
     */
    public void uploadAdError(final Application application) {
        final String mAdErrLog = SPAdUtils.getString(getInstance().mContext, AdLoc.AD_ERR_CACHE_LOG, "");
        if (!TextUtils.isEmpty(mAdErrLog)) {
            ThreadPool.run(new Runnable() {
                @Override
                public void run() {
                    LogUtils.d("uploadAdError------>>>> " + mAdErrLog);
                    final HashMap<String, String> params = HttpParameters.getParams(application, 1);
                    params.put(PhoneConstan.ADATA, mAdErrLog);
                    params.put(PhoneConstan.ISINDEX, "0");
                    NetAddress.uploadingAd(params);
                }
            });
        }
    }

    /**
     * 获取配置信息
     *
     * @param application
     */
    private void getAdConfig(final Application application) {
        final HashMap<String, String> mParmas = HttpParameters.getParams(application, 1);
        LogUtils.d("parmas:" + mParmas.toString());
        LogUtils.d("url:" + NetAddress.BASE_URL);
        OkHttp3Utils.doPost(NetAddress.BASE_URL, mParmas, new GsonObjectCallback<AdConfigBean>() {
            @Override
            public void onUi(AdConfigBean adConfigBean) {
                LogUtils.d("当前线程" + UiThredUtils.isOnMainThread());
                if (adConfigBean.getRet() != null && adConfigBean.getRet().equals("succ")) {
                    LogUtils.d(adConfigBean.toString());
                    if (adConfigBean != null) {
                        mAdConfigBean = adConfigBean;
                        isInit = true;
                        if (adConfigBean.getData() != null && !TextUtils.isEmpty(adConfigBean.getData().getPassport())) {
                            SPAdUtils.saveString(application, AdLoc.PASS_PORT, adConfigBean.getData().getPassport());
                        }
                        SPAdUtils.saveObject(application, AdLoc.AD_CONFIG, adConfigBean);
                    }
                } else {
                    if (mAdConfigBean != null) {
                        isInit = true;
                    } else {
                        LogUtils.d("获取AD网络配置失败");
                        isInit = false;
                    }
                }
            }

            @Override
            public void onFailedUi(Call call, IOException e) {
                LogUtils.d("当前线程" + UiThredUtils.isOnMainThread());
                if (mAdConfigBean != null) {
                    isInit = true;
                } else {
                    isInit = false;
                }
            }
        });
    }



//    public void initVideoSdk(Activity activity){
//        if (getInstance().mAdConfigBean != null) {
//            isInit = true;
//            LTVideoAdUtils.initAdSdk(getAdids(27),mContext, activity);
//        }
//    }

    /**
     * 设置调换视频播放顺序
     */
    public void setAdConfigBean() {
        AdConfigBean.AdidsEntity adidsEntity = null;
        if (getAds() != null && getAds().size() > 27) {
            List<AdConfigBean.AdidsEntity> adids = getAdids(27);
            if (adids != null && adids.size() > 1) {
                adidsEntity = adids.get(0);
                LogUtils.d("adIds:" + adidsEntity.getStype());
                LogUtils.d("adIds:" + adids.toString());
                adids.remove(0);
                LogUtils.d("adIds:" + adids.toString());
                adids.add(adidsEntity);
                LogUtils.d("adIds:" + adids.toString());
                setAdids(27, adids);
            }
        }
    }

    /**
     * 获取配置里面有多少个广告位
     *
     * @return
     */
    private static List<AdConfigBean.RulesEntity> getAds() {
        if (getInstance().mAdConfigBean != null) {
            if (getInstance().mAdConfigBean.getData() != null) {
                if (getInstance().mAdConfigBean.getData().getRules() != null && getInstance().mAdConfigBean.getData().getRules().size() > 0) {
                    return getInstance().mAdConfigBean.getData().getRules();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取指定索引位置的多个广告位
     *
     * @param position
     * @return
     */
    private static AdConfigBean.RulesEntity getAd(int position) {
        if (getInstance().mAdConfigBean != null) {
            if (getInstance().mAdConfigBean.getData() != null) {
                if (getInstance().mAdConfigBean.getData().getRules() != null && getInstance().mAdConfigBean.getData().getRules().size() > 0) {
                    return getInstance().mAdConfigBean.getData().getRules().get(position);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取广告配置总数
     *
     * @return
     */
    public static int getAdNum() {
        if (getInstance().mAdConfigBean != null) {
            if (getInstance().mAdConfigBean.getData() != null) {
                if (getInstance().mAdConfigBean.getData().getRules() != null && getInstance().mAdConfigBean.getData().getRules().size() > 0) {
                    return getInstance().mAdConfigBean.getData().getRules().size();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 获取广告位集合（广告位包含几个广告位代码）
     *
     * @param position
     * @return
     */
    public static List<AdConfigBean.AdidsEntity> getAdids(int position) {
        final AdConfigBean.RulesEntity entity = getAd(position);
        if (entity != null && entity.getAdids() != null) {
            return entity.getAdids();
        } else {
            return null;
        }
    }

    /**
     * 替换某个广告位里得配置e
     *
     * @param position
     * @param adidsEntities
     */
    public static void setAdids(int position, List<AdConfigBean.AdidsEntity> adidsEntities) {
        if (getInstance().mAdConfigBean != null) {
            getInstance().mAdConfigBean.getData().getRules().get(position).setAdids(adidsEntities);
        }
    }

    /**
     * 获取广告类型
     *
     * @param position
     * @param index
     * @return
     */
    public static int getStype(int position, int index) {
        if (getAdids(position) != null && getAdids(position).size() > 0) {
            if (index < getAdids(position).size()) {
                if (getAdids(position).get(index) != null) {
                    if (getAdids(position).get(index).getStype() != -1) {
                        return getAdids(position).get(index).getStype();
                    } else {
                        return -1;
                    }
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 获取指定索引包含的广告
     *
     * @param position
     * @return
     */
    public static int getAdidsSize(int position) {
        if (getAdids(position) != null && getAdids(position).size() > 0) {
            if (getAdids(position) != null && getAdids(position).size() > 0) {
                return getAdids(position).size();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    /**
     * 获取APPID
     *
     * @param position
     * @param index
     * @return
     */
    public static String getAppid(int position, int index) {
        if (getAdids(position) != null && getAdids(position).size() > 0) {
            if (index < getAdids(position).size()) {
                if (getAdids(position).get(index) != null) {
                    if (!TextUtils.isEmpty(getAdids(position).get(index).getAppid())) {
                        return getAdids(position).get(index).getAppid();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取广告类型
     *
     * @param position
     * @param index
     * @return
     */
    public static int getAdType(int position, int index) {
        if (getAdids(position) != null && getAdids(position).size() > 0) {
            if (index < getAdids(position).size()) {
                if (getAdids(position).get(index) != null) {
                    return getAdids(position).get(index).getStype();
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /**
     * 获取广告ID
     *
     * @param position
     * @param index
     * @return
     */
    public static String getAdid(int position, int index) {
        if (getAdids(position) != null && getAdids(position).size() > 0) {
            if (index < getAdids(position).size()) {
                if (getAdids(position).get(index) != null) {
                    if (!TextUtils.isEmpty(getAdids(position).get(index).getAdid())) {
                        return getAdids(position).get(index).getAdid();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getAppSecret(int position, int index) {
        if (getAdids(position) != null && getAdids(position).size() > 0) {
            if (index < getAdids(position).size()) {
                if (getAdids(position).get(index) != null) {
                    if (!TextUtils.isEmpty(getAdids(position).get(index).getSecret())) {
                        return getAdids(position).get(index).getSecret();
                    } else {
                        return "";
                    }
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    /**
     * 获取起始时间
     *
     * @param position
     * @return
     */
    public static String getBhour(int position) {
        if (getAd(position) != null) {
            if (!getAd(position).getBhour().equals("00:00")) {
                return getAd(position).getBhour();
            } else {
                return "00:00";
            }
        } else {
            return "00:00";
        }
    }

    /**
     * 获取截止时间
     *
     * @param position
     * @return
     */
    public static String getEhour(int position) {
        if (getAd(position) != null) {
            if (!getAd(position).getEhour().equals("00:00")) {
                return getAd(position).getEhour();
            } else {
                return "00:00";
            }
        } else {
            return "00:00";
        }
    }

    /**
     * 获取广告刷新时间，目前只有广点通有效
     *
     * @param position
     * @return
     */
    public static int getFnum(int position) {
        if (getAd(position) != null) {
            return getAd(position).getFnum();
        } else {
            return 0;
        }
    }

    /**
     * 返回应用名称
     * @param context
     * @return
     */
    /**
     * 获取应用程序名称
     */
    public static synchronized String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
