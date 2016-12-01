package com.topfine.malltest.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.orhanobut.logger.Logger;
import com.topfine.malltest.R;
import com.topfine.malltest.base.BaseActivity;
import com.topfine.malltest.base.net.basemodel.ImageItem;
import com.topfine.malltest.base.net.basemodel.ImagePicker;
import com.topfine.malltest.constant.UrlConfig;
import com.topfine.malltest.service.download.DownloadApk;
import com.topfine.malltest.utils.sign.Md5;
import com.topfine.malltest.utils.sign.SignUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.update).setOnClickListener(this);
        findViewById(R.id.down).setOnClickListener(this);
        findViewById(R.id.basepage).setOnClickListener(this);
        findViewById(R.id.easyRecy).setOnClickListener(this);
        findViewById(R.id.easyRecyModle).setOnClickListener(this);
        findViewById(R.id.muti_reboundRecy).setOnClickListener(this);
        findViewById(R.id.reboundRecy).setOnClickListener(this);

        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("password", "123456");
        //        long time1=Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10));
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        Logger.e("timestamp ==", timestamp);
        Log.e("kkkk", timestamp);
        params.put("timestamp", timestamp);
        params.put("user_name", "topfineuser我的");

        //        JSONObject jsonObject = new JSONObject(params);
        String sign = null;
        String paramsJson = SignUtils.createLinkJson(params);
        String stringJson = SignUtils.createLinkString(params);

        Logger.e("stringJson " + stringJson);
        String needSign = stringJson + "&key=8bd1cc4e6787078ea00126217969c163";
        //        needSign = "{\"password\":\"123456\" + \"timestamp\":\"1480469916\",\"user_name\":\"topfineuser\"}" + "8bd1cc4e6787078ea00126217969c163";
        Logger.e("needSign=" + needSign);
        try {
            sign = Md5.encodeByMd5(needSign).toUpperCase();
            Logger.e("sign=" + sign);

        } catch (Exception e) {
            e.printStackTrace();
        }
        InputStream open = null;
        try {
            open = getAssets().open("brand_sort.png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpParams httpParams = new HttpParams();
        httpParams.put("PARAMS", paramsJson);
        httpParams.put("SIG", sign);
//        File file = new File("");
//        httpParams.put("FILE",file);

        Logger.e("httpParams" + httpParams.toString());

        OkGo.post(UrlConfig.TestLogin)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Logger.e("result == " + s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logger.e("e == " + e.getMessage());
                    }
                });

    }

    private ArrayList<ImageItem> imageItems;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (imageItems != null && imageItems.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < imageItems.size(); i++) {
                        if (i == imageItems.size() - 1) sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path);
                        else sb.append("图片").append(i + 1).append(" ： ").append(imageItems.get(i).path).append("\n");
                    }
//                    tvImages.setText(sb.toString());
                } else {
//                    tvImages.setText("--");
                }
            } else {
                Toast.makeText(this, "没有选择图片", Toast.LENGTH_SHORT).show();
//                tvImages.setText("--");
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.down:

                //                UpdateApp.startDownload(this,"http://server.jeasonlzy.com/OkHttpUtils/download");
                //                Logger.e("下载");

                //3.如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                //                if (DownLoadUtils.getInstance(getApplicationContext()).canDownload()) {
                //                    //                    DownloadApk.downloadApk(getApplicationContext(), "http://www.huiqu.co/public/download/apk/huiqu.apk", "Hobbees更新", "Hobbees");
                DownloadApk.downloadApk(getApplicationContext(), "http://server.jeasonlzy.com/OkHttpUtils/download", "okhttptitle", "apkName");
                Log.e("kkkk", "1");
                //
                break;
            case R.id.update:
                final String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                Logger.e(absolutePath);

                OkGo.get("http://server.jeasonlzy.com/OkHttpUtils/download") //"http://127.0.0.1:8080/test.apk"
                        .tag(this)//
                        //                .headers("header1", "headerValue1")//
                        //                .params("param1", "paramValue1")//
                        .execute(new FileCallback(absolutePath, "testDown.apk") {
                            @Override
                            public void onSuccess(File file, Call call, Response response) {
                                openFile(file);
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }

                            @Override
                            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                                Logger.e("currentSize" + currentSize);
                            }
                        });
                break;
            case R.id.basepage:
                Intent intent = new Intent(this, TestpageActivity.class);
                startActivity(intent);
                break;
            case R.id.easyRecy:
                startActivity(new Intent(this, EasyRecyActivity.class));
                break;
            case R.id.easyRecyModle:
                startActivity(new Intent(this, EasyRecyModleActivity.class));
                break;
            case R.id.muti_reboundRecy:
                startActivity(new Intent(this, MutiReboundActivity.class));
                break;
            case R.id.reboundRecy:
                startActivity(new Intent(this, ReboundActivity.class));
                break;
            case R.id.selectImage:

                break;
        }
    }

    private void openFile(File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
