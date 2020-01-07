package com.xszn.ime;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.xszn.ime.utils.WeakHandler;
import com.zkyy.icecream.callback.DaSplashCallBack;
import com.zkyy.icecream.dautil.DaSplashLoad;
import com.zkyy.icecream.utils.LogUtils;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.xszn.ime
 * @class describe
 * @time 2019-12-25 15:38
 * @change
 * @chang time
 * @class describe
 */
public class SplashActivity extends AppCompatActivity implements WeakHandler.IHandler {

    private static String TAG = SplashActivity.class.getSimpleName() + ": ";

//    private Handler handler = new Handler(Looper.getMainLooper());

    //是否强制跳转到主页面
    private boolean mForceGoMain;

    //开屏广告加载发生超时但是SDK没有及时回调结果的时候，做的一层保护。
    private final WeakHandler mHandler = new WeakHandler(this);
    //开屏广告加载超时时间,建议大于3000,这里为了冷启动第一次加载到广告并且展示,示例设置了3000ms
    private static final int AD_TIME_OUT = 3000;
    private static final int MSG_GO_MAIN = 1;
    //开屏广告是否已经加载
    private boolean mHasLoaded;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mFrameLayout = findViewById(R.id.frame_layout);
        init();
    }

    private void init() {
        mHandler.sendEmptyMessageDelayed(MSG_GO_MAIN, AD_TIME_OUT);
        //801121648
        DaSplashLoad.loadSplash(this, 1001, mFrameLayout, new DaSplashCallBack() {
            @Override
            @MainThread
            public void onDaSplashError(int code, String message) {
                LogUtils.d(TAG + message);
                mHasLoaded = true;
                showToast(message);
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onDaSplashTimeout() {
                mHasLoaded = true;
                showToast("开屏广告加载超时");
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onDaSplashAdLoad(TTSplashAd ad) {
                Log.d(TAG, "开屏广告请求成功");
                mHasLoaded = true;
                mHandler.removeCallbacksAndMessages(null);
            }

            @Override
            @MainThread
            public void onDaToMain() {
                goToMainActivity();
            }

            @Override
            public void onDaError(String message) {
                LogUtils.d(TAG + message);
                goToMainActivity();
            }
        });
    }

    @Override
    public void handleMsg(Message msg) {
        if (msg.what == MSG_GO_MAIN) {
            if (!mHasLoaded) {
                showToast("广告已超时，跳到主页面");
                goToMainActivity();
            }
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void goToMainActivity() {
        MainActivity.launch(SplashActivity.this);
        mFrameLayout.removeAllViews();
    }

    @Override
    protected void onResume() {
        //判断是否该跳转到主页面
        if (mForceGoMain) {
            mHandler.removeCallbacksAndMessages(null);
            goToMainActivity();
        }
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mForceGoMain = true;
    }
}
