package com.topfine.malltest.base;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;

import com.topfine.malltest.base.net.OKHttpFactory;
import com.topfine.malltest.constant.UrlConfig;
import com.topfine.malltest.event.ConnectionChangeReceiver;

/**
 * Created by Littlezuo on 2016/9/27.
 */
public class MyApplication extends Application{
    private static Context mContext;
    public static Handler handler;
    public static Thread mainThread;//主线程
    public static int mainThreadId;
    ConnectionChangeReceiver mNetworkStateReceiver;
    private ConnectionChangeReceiver mConnectionChangeReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        UrlConfig.setProEnvironment(UrlConfig.PRD);
//        OkGo.init(this);


        mContext = MyApplication.this;
        handler = new Handler();
        mainThread = Thread.currentThread();//获取实例化Application的线程,就是主线程
        mainThreadId = android.os.Process.myTid();
        OKHttpFactory.init(this);
        //注册网络状态监听广播
        mConnectionChangeReceiver = new ConnectionChangeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectionChangeReceiver,filter);

    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(mConnectionChangeReceiver);
        super.onTerminate();
    }
}
