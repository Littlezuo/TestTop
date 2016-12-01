package com.topfine.malltest.home.server;

import android.widget.Toast;

import com.topfine.malltest.base.MyApplication;

/**
 * Created by Littlezuo on 2016/11/25.
 */

public abstract class MyHttpCallBack {
    protected abstract void onSuccess();
    protected void onFailure(){
        Toast.makeText(MyApplication.getContext(), "请求失败", Toast.LENGTH_SHORT).show();
    }
}
