package com.xszn.ime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zkyy.icecream.callback.DaBannerCallBack;
import com.zkyy.icecream.callback.DaNativeCallBack;
import com.zkyy.icecream.constan.DaAdvertiserType;
import com.zkyy.icecream.dautil.DaBannerLoad;
import com.zkyy.icecream.dautil.DaNativeLoad;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.MyUtils;

import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName() + "：";
    private FrameLayout mBannerTopFrameLayout;
    private FrameLayout mBannerBottomFrameLayout;
    private LinearLayout mLinearLayout;
    private TextView mTvBackGroud;
    private TextView mTvWH;
    //    private static final String[] permissionsGroup = new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//    final RxPermissions rxPermissions = new RxPermissions(this);

    public static void launch(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBannerTopFrameLayout = findViewById(R.id.banner_top_frame_layout);
        mBannerBottomFrameLayout = findViewById(R.id.banner_bottom_frame_layout);
        mLinearLayout = findViewById(R.id.linearLayout);
        mTvBackGroud = findViewById(R.id.tv_backgroud);
        mTvWH = findViewById(R.id.tv_w_h);
        showPermission();

        initSdk();
        init();
    }

    private void initSdk() {
//        DaInit.init(WeatherApplication.getContext(),"5018834");
    }

    @SuppressLint({"CheckResult", "WrongConstant"})
    private void init() {

        mTvBackGroud.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        mTvWH.setText(TAG + "w: " + MyUtils.getScreenWidth(this) + "____h: " + MyUtils.getScreenHeight(this)
                + "___控件w: " + mTvBackGroud.getMeasuredWidth() + "____h: " + mTvBackGroud.getMeasuredHeight());
        findViewById(R.id.tv_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                VideoPlayActivity.launch(MainActivity.this);
//                HomeActivity.launch(MainActivity.this);
                WeatherActivity.launch(MainActivity.this);
            }
        });

        //901121895
        //901121987
        DaBannerLoad.loadBanner(this, DaAdvertiserType.CSJ, "901121895", mBannerTopFrameLayout, new DaBannerCallBack() {

            @Override
            public void onDaBannerError(int code, String message) {

            }

            @Override
            public void onDaBannerClicked(View view, int type) {

            }

            @Override
            public void onDaBannerShow(View view, int type) {

            }

            @Override
            public void onDaBannerSelected(int position, String value) {

            }

            @Override
            public void onDaBannerCancel() {

            }
        });

        DaBannerLoad.loadBanner(this, DaAdvertiserType.CSJ, "901121987", mBannerBottomFrameLayout, new DaBannerCallBack() {

            @Override
            public void onDaBannerError(int code, String message) {

            }

            @Override
            public void onDaBannerClicked(View view, int type) {

            }

            @Override
            public void onDaBannerShow(View view, int type) {

            }

            @Override
            public void onDaBannerSelected(int position, String value) {

            }

            @Override
            public void onDaBannerCancel() {

            }
        });

        DaNativeLoad.loadNative(this, DaAdvertiserType.CSJ, "901121423", mLinearLayout, new DaNativeCallBack() {
            @Override
            public void onDaNativeError(int code, String message) {

            }

            @Override
            public void onDaNativeClicked(View view, TTNativeAd ad) {

            }

            @Override
            public void onDaNativeCreativeClick(View view, TTNativeAd ad) {

            }

            @Override
            public void onDaNativeShow(TTNativeAd ad) {

            }

            @Override
            public void onDaNativeSelected(int position, String value) {

            }

            @Override
            public void onDaNativeCancel() {

            }
        });
    }

    @SuppressLint("WrongConstant")
    private void showPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(permissions -> {
                    // Storage permission are allowed.
                    LogUtils.d(TAG + "allowed");
                })
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    LogUtils.d(TAG + "not allowed");
                    showDialog();
                })
                .start();
    }

    private void showDialog() {
        //警告
//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText("Won't be able to recover this file!")
//                .setConfirmText("Yes,delete it!")
//                .show();

        //取消按钮及事件绑定
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No,cancel plx!")
                .setConfirmText("Yes,delete it!")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

        //对话框
//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText("Won't be able to recover this file!")
//                .setConfirmText("Yes,delete it!")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        sDialog
//                                .setTitleText("Deleted!")
//                                .setContentText("Your imaginary file has been deleted!")
//                                .setConfirmText("OK")
//                                .setConfirmClickListener(null)
//                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                    }
//                })
//                .show();
    }
}
