/*
 * Copyright (c) 上海牛创科技有限公司
 *
 */

package com.topfine.malltest.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * 模块:
 * 功能:
 * 创建者: wuyunan
 * 创建于: 16/7/11.
 * 版本:
 */
public class Utils {
    private static final String TAG = "Utils";


    /**
     * 获取市场名称
     *
     * @param context
     * @return
     */
    public static String getMarket(Context context) {
//        String market = PackerNg.getMarket(context);
//        if (TextUtils.isEmpty(market)) {
//            market = "dev";
//        }
        return getMetaValue(context, "UMENG_CHANNEL");
    }

    // 获取ApiKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {
//            Log.e(TAG, "error " + e.getMessage());
        }
        return apiKey;
    }



}
