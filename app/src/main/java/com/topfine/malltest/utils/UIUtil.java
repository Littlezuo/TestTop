package com.topfine.malltest.utils;

import android.content.res.TypedArray;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.topfine.malltest.base.MyApplication;


/**
 * Created by Littlezuo on 2016/9/19.
 */
public class UIUtil {


    public static Handler getHandler() {
        return MyApplication.handler;
    }

    public static int getColor(int colorId) {
        return MyApplication.getContext().getResources().getColor(colorId);
    }

    public static View getXmlView(int viewId) {
        return View.inflate(MyApplication.getContext(),viewId,null);
    }

    public static String[] getStringArray(int arrayId) {
        return MyApplication.getContext().getResources().getStringArray(arrayId);
    }

    public static int dp2px(int dp) {
        float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    public static int px2dp(int px) {
        float density = MyApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density + 0.5);
    }

    //将方法中要执行的操作在主线程中执行
    public static void runOnUiThread(Runnable runnable) {
        //先判断当前线程是不是主线程
        if(isMainThread()) {
            runnable.run();
        }else {
            //通过发送消息,使得如下的操作在主线程中执行
            getHandler().post(runnable);
        }

    }

    /**
     * 判断当前线程是否是主线程
     * @return
     */
    private static boolean isMainThread() {
        int tid = android.os.Process.myTid(); //获取当前线程的ID
        return tid == MyApplication.mainThreadId;
    }

    public static void toast(String message) {
        Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static int getBackground(){
        TypedArray array = MyApplication.getContext().getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorBackground,
                android.R.attr.textColorPrimary,
        });
        int backgroundColor = array.getColor(0, 0xFF00FF);
        int textColor = array.getColor(1, 0xFF00FF);
        array.recycle();
        return backgroundColor;
    }

    public static int getWindWidth(){
        return MyApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }


}
