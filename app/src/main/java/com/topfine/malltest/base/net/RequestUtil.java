package com.topfine.malltest.base.net;


import android.content.Context;
import android.text.TextUtils;

import com.topfine.malltest.constant.AppConstants;
import com.topfine.malltest.utils.Installation;
import com.topfine.malltest.utils.SpUtil;
import com.topfine.malltest.utils.StrUtils;
import com.topfine.malltest.utils.Utils;
import com.topfine.malltest.utils.sign.SignUtils;

import java.util.HashMap;
import java.util.Map;



/**
 * 模块:
 * 功能:
 * 创建人: wuyunan
 * 时间: 16/7/29
 */

public class RequestUtil {

    /**
     * app id
     */
    private static final String KEY_APP_ID = "appid";
    /**
     * app版本
     */
    private static final String KEY_APP_VERSION = "appv";
    private static final String KEY_APP_MODE = "appm";
    private static final String KEY_APP_CHANNEL = "appch";
    private static final String KEY_DEVICE_ID = "did";
    private static final String KEY_DEVICE_BRAND = "dbr";
    private static final String KEY_DEVICE_MODEL = "dmd";
    private static final String KEY_DEVICE_OS = "dos";
    private static final String KEY_DEVICE_SCREEN = "dscr";
    private static final String KEY_DEVICE_NET = "dnet";
    private static final String KEY_AREA = "area";
    private static final String KEY_LONGITUDE = "lng";
    private static final String KEY_LATITUDE = "lat";
    private static final String TOKEN = "token";
    private static final String KEY_TS = "ts";
    public static final String KEY_BODY = "body";

    /**
     * 配置post参数,Md5签名
     *
     * @return
     */
    public static Map<String, String> getSignedParamMap( Context context, String appMode) {


        Map<String, String> needSignParam = new HashMap<>();

        needSignParam.put(KEY_APP_ID, AppConstants.APPID.JSB_AND);   //
        needSignParam.put(KEY_APP_VERSION, StrUtils.getVersion(context));//版本
//        needSignParam.put(KEY_APP_MODE, appMode);
        needSignParam.put(KEY_APP_CHANNEL, Utils.getMarket(context));
        needSignParam.put(KEY_DEVICE_ID, Installation.id(context));
        needSignParam.put(KEY_DEVICE_BRAND, android.os.Build.BRAND); //SONY
        needSignParam.put(KEY_DEVICE_MODEL, android.os.Build.DEVICE); //dmd D6616
        needSignParam.put(KEY_DEVICE_OS, AppConstants.OS.ANDROID);

        String token = SpUtil.getToken(context);
        if (!TextUtils.isEmpty(token)) {
            needSignParam.put(TOKEN, token);
        }
        needSignParam.put(KEY_TS, String.valueOf(System.currentTimeMillis()));  //当前时间的毫秒值
        return SignUtils.buildRequestPara(needSignParam);
    }
}
