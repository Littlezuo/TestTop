package com.topfine.malltest.service;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Littlezuo on 2016/11/23.
 */

public class UpdateApp {
    /**系统下载管理器*/
//    private DownloadManager dm;
    /**系统下载器分配的唯一下载任务id，可以通过这个id查询或者处理下载任务*/
    private long enqueue;
    /**TODO下载地址 需要自己修改,这里随便找了一个*/
    //    private String downloadUrl="http://dakaapp.troila.com/download/daka.apk?v=3.0";
    private String downloadUrl="http://server.jeasonlzy.com/OkHttpUtils/download";

    public static void startDownload(Context context,String downUrl) {
        //获得系统下载器
        DownloadManager systemService = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
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
        systemService.enqueue(request);
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
}
