package com.topfine.malltest.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.topfine.malltest.R;
import com.topfine.malltest.utils.NetUtil;
import com.topfine.malltest.utils.ToastUtils;


/**
 * Created by Littlezuo on 2016/11/22.
 */

public class ConnectionChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isNet = NetUtil.isConnected(context);

        if(!isNet) {
            ToastUtils.showShortToast(context,context.getString(R.string.your_network_has_disconnected));
        }
        boolean isMobile = NetUtil.isMobile(context);
        if(isMobile) {
            ToastUtils.showShortToast(context,context.getString(R.string.your_mobile_has_disconnected));
        }

    }
}
