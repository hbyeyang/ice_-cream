package com.xszn.ime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zkyy.icecream.utils.LogUtils;
import com.zkyy.icecream.utils.MyUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yeyang
 * @name weather_demo
 * @class name：com.xszn.ime
 * @class describe
 * @time 2019-12-29 16:16
 * @change
 * @chang time
 * @class describe
 */
public class HomeActivity extends AppCompatActivity {

    private static String TAG = HomeActivity.class.getSimpleName() + ": ";

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_title_1)
    TextView mTvTitle1;
    @BindView(R.id.ll_main)
    LinearLayout mLlMain;
    @BindView(R.id.tv_title_2)
    TextView mTvTitle2;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.ll_title_main)
    LinearLayout mLlTitleMain;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;

    private int[] location1 = new int[2];
    private int[] location2 = new int[2];

    private boolean isCanScroll = false;

    public static void launch(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        titleListener();

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollView.scrollTo(0, 0);
                isCanScroll = false;
                mTvTitle2.setVisibility(View.VISIBLE);
            }
        });

        mRefresh.setEnableAutoLoadMore(true);
    }

    private void titleListener() {
        ViewTreeObserver vto = mLlMain.getViewTreeObserver();
        mTvTitle1.getLocationInWindow(location1);
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {
//                final int height = MyUtils.px2dp(HomeActivity.this, mLlMain.getHeight());
                if (mNestedScrollView != null) {


//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!isCanScroll) {
                        mNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                            @Override
                            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                            LogUtils.d(TAG + "hight: " + height);
                                mTvTitle2.getLocationInWindow(location2);
                                LogUtils.d(TAG + "location1[1]: " + location1[1]);
                                LogUtils.d(TAG + "location2[1]: " + location2[1]);

                                if (location2[1] <= (location1[1] + MyUtils.dp2px(HomeActivity.this, 100))) {
                                    LogUtils.d(TAG + "禁止滚动");
                                    mLlTitleMain.setVisibility(View.VISIBLE);
//                                    mNestedScrollView.setNestedScrollingEnabled(false);
                                    mNestedScrollView.setFillViewport(false);
                                    mRefresh.setEnableRefresh(true);
                                    mTvTitle2.setVisibility(View.GONE);
                                    isCanScroll = true;
                                } else {
                                    mLlTitleMain.setVisibility(View.GONE);
                                    mRefresh.setEnableRefresh(false);
                                }
                            }
                        });
                    }
//                    } else {
//
//                    }
                }
            }
        });

//        mNestedScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (isCanScroll) {
//                    LogUtils.d(TAG + isCanScroll);
//                    return  isCanScroll;
//                } else {
//                    LogUtils.d(TAG + isCanScroll);
//                    return  isCanScroll;
//                }
//            }
//        });
    }
}
