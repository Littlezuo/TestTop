package com.topfine.malltest.base.net;

import android.app.Application;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.topfine.malltest.base.MyApplication;
import com.topfine.malltest.constant.AppConstants;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Littlezuo on 2016/11/20.
 */
public class OKHttpFactory {
    public static void init(Application application) {

        // 进行Md5签名
        HttpParams commonParams = new HttpParams();
        Map<String, String> deviceInfoMap = RequestUtil.getSignedParamMap(MyApplication.getContext(), AppConstants.APPMODE.NATIVE);

        //loop a Map
        for (Map.Entry<String, String> entry : deviceInfoMap.entrySet()) {
            commonParams.put(entry.getKey(), entry.getValue());
        }
        //-----------------------------------------------------------------------------------//
        //初始化
        OkGo.init(application);
        HttpParams commonParams1 = new HttpParams();
        commonParams1.put("KEY", "6126f77c9c360d8e");
//        commonParams1.put("SIG", "这里是需要提交的json格式数据");
        commonParams1.put("VER", "1.0");

        HashMap<String, String> params = new HashMap<>();
        params.put("dev", "ios");
        params.put("os", "ios 10.1.1");
        params.put("uuid", "123456");
        params.put("1other", "xxxx");
        params.put("uvther", "xxxx");
        params.put("11","xxxxx");
        params.put("V11","xxxxx");
        JSONObject info = new JSONObject(params);
//        Logger.e(info.toString());
        commonParams1.put("INFO", info.toString());
//        Logger.e(commonParams1.toString());

        try {
            OkGo.getInstance()

                    // 打开该调试开关,打印级别INFO,并不是异常,是为了显眼,不需要就不要加入该行
                    // 最后的true表示是否打印okgo的内部异常，一般打开方便调试错误
                    .debug("OkGo", Level.WARNING, true)

                    //如果使用默认的 60秒,以下三行也不需要传
                    .setConnectTimeout(5000)  //全局的连接超时时间
                    .setReadTimeOut(5000)     //全局的读取超时时间
                    .setWriteTimeOut(5000)    //全局的写入超时时间

                    //可以全局统一设置缓存模式,默认是不使用缓存,可以不传,具体其他模式看 github 介绍 https://github.com/jeasonlzy/
                    .setCacheMode(CacheMode.NO_CACHE)

                    //可以全局统一设置缓存时间,默认永不过期,具体使用方法看 github 介绍
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)

                    //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
                    .setRetryCount(3)
                    .setCertificates()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();

                            Request.Builder requestBuilder = request.newBuilder();
                            FormBody.Builder postData = new FormBody.Builder();

                            if (request.body() instanceof FormBody) {
                                FormBody oidFormBody = (FormBody) request.body();
                                //                    RequestBody body = request.body();
                                Map<String, String> oldBody = new HashMap<>();
                                for (int i = 0; i < oidFormBody.size(); i++) {
                                    String key = oidFormBody.encodedName(i);
                                    Log.e("kkkk", key);

                                    String value = oidFormBody.encodedValue(i);

                                    oldBody.put(key, value);

                                }

                                // 进行Md5签名
                                Map<String, String> deviceInfoMap = RequestUtil.getSignedParamMap(MyApplication.getContext(), AppConstants.APPMODE.NATIVE);

                                //loop a Map
                                for (Map.Entry<String, String> entry : deviceInfoMap.entrySet()) {
                                    postData.add(entry.getKey(), entry.getValue());
                                }

                                requestBuilder.method(request.method(), postData.build());
                            }

                            //requestBuilder.post(postData.build());
                            request = requestBuilder.build();

                            return chain.proceed(request);
                            //                            return null;
                        }
                    })
                    //                                        .addCommonHeaders(headers)  //设置全局公共头
                    .addCommonParams(commonParams1)    //设置全局公共参数
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
