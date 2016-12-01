package com.topfine.malltest.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.topfine.malltest.event.ConnectionChangeReceiver;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Littlezuo on 2016/11/17.
 */

public class BaseActivity extends FragmentActivity {

    private ConnectionChangeReceiver mConnectionChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View setcontentView() {

        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeContentState(int state) {
        if(state == 0) {

        }
    }







    //启动一个新的activity
    public void goToActivity(Class activity,Bundle bundle){
        Intent intent = new Intent(this,activity);
        if(bundle != null){
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
    }

    //结束当前activity的显示
    public void closeCurrentActivity(){
        AppManager.getInstance().removeCurrent();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
//        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(mConnectionChangeReceiver);
        super.onDestroy();
    }
}
