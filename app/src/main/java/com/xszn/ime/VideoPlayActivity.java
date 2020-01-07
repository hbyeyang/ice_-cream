package com.xszn.ime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.zkyy.icecream.callback.DaVideoPlayCallBack;
import com.zkyy.icecream.dautil.DaVideoLoad;
import com.zkyy.icecream.utils.LogUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.xszn.ime
 * @class describe
 * @time 2019-12-25 15:30
 * @change
 * @chang time
 * @class describe
 */
public class VideoPlayActivity extends AppCompatActivity {

    private static String TAG = VideoPlayActivity.class.getSimpleName() + '：';

    public static void launch(Context context) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    //918834024 小石
    //901121365 测试
    private void init() {
        DaVideoLoad.play(this, 1050, new DaVideoPlayCallBack() {
            @Override
            public void isDaVideoReady() {
                LogUtils.d(TAG);
            }

            @Override
            public void onDaVideoError() {
                LogUtils.d(TAG);
            }

            @Override
            public void onDaVideoError(String s) {
                LogUtils.d(TAG + s);
            }

            @Override
            public void onDaVideoShow() {
                LogUtils.d(TAG);
            }

            @Override
            public void onDaPlayComplete() {
                LogUtils.d(TAG);
            }

            @Override
            public void onDaVideoClose() {
                LogUtils.d(TAG);
            }
        });
    }
}
