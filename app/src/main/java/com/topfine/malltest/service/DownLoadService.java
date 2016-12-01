package com.topfine.malltest.service;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;

import rx.functions.Action1;

/**
 * Created by Littlezuo on 2016/11/23.
 */

public class DownLoadService extends Service {
    /**广播接受者*/
    private BroadcastReceiver receiver;
    /**系统下载管理器*/
    private DownloadManager dm;
    /**系统下载器分配的唯一下载任务id，可以通过这个id查询或者处理下载任务*/
    private long enqueue;
    /**TODO下载地址 需要自己修改,这里随便找了一个*/
//    private String downloadUrl="http://dakaapp.troila.com/download/daka.apk?v=3.0";
    private String downloadUrl="http://server.jeasonlzy.com/OkHttpUtils/download";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                install(context);
                //销毁当前的Service
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        //下载需要写SD卡权限, targetSdkVersion>=23 需要动态申请权限
        RxPermissions.getInstance(this)
                // 申请权限
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean granted) {
                        if(granted){
                            //请求成功
                            startDownload(downloadUrl);
                        }else{
                            // 请求失败回收当前服务
                            stopSelf();

                        }
                    }
                });
        return Service.START_STICKY;
    }

    /**
     * 通过隐式意图调用系统安装程序安装APK
     */
    public static void install(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(
                new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "myApp.apk")),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    @Override
    public void onDestroy() {
        //服务销毁的时候 反注册广播
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload(String downUrl) {
        //获得系统下载器
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //设置下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downUrl));
        //设置下载文件的类型
        request.setMimeType("application/vnd.android.package-archive");
        //设置下载存放的文件夹和文件名字
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "myApp.apk");
        //设置下载时或者下载完成时，通知栏是否显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("下载新版本");
        //执行下载，并返回任务唯一id
        enqueue = dm.enqueue(request);
    }
}
