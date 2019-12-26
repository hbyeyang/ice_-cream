package com.zkyy.icecream.net;

import android.os.Handler;

import com.google.gson.Gson;
import com.zkyy.icecream.utils.LogUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author yeyang
 * @name AdDemo
 * @class name：com.lt.bc.mb.qmks.net
 * @class describe
 * @time 2018/9/25 上午10:44
 * @change
 * @chang time
 * @class describe
 */
public abstract class GsonObjectCallback<T> implements Callback {

    private Handler handler = OkHttp3Utils.getInstance().getHandler();


    //主线程处理
    public abstract void onUi(T t);

    //主线程处理
    public abstract void onFailedUi(Call call, IOException e);

    //请求失败
    @Override
    public void onFailure(final Call call, final IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                onFailedUi(call, e);
            }
        });
    }

    //请求json 并直接返回泛型的对象 主线程处理
    @Override
    public void onResponse(final Call call, Response response) throws IOException {
        String json = response.body().string();
        LogUtils.d("response--->>> " + json);
        try {
            Class clz = this.getClass();
            ParameterizedType type = (ParameterizedType) clz.getGenericSuperclass();
            Type[] types = type.getActualTypeArguments();
            Class<T> cls = (Class<T>) types[0];
            Gson gson = new Gson();
            final T t = gson.fromJson(json, cls);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onUi(t);
                }
            });
        } catch (Exception ex){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    onFailedUi(call, null);
                }
            });
        }
    }

}
