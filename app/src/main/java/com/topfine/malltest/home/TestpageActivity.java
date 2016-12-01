package com.topfine.malltest.home;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.topfine.malltest.R;
import com.topfine.malltest.base.BaseLoadingPage;

public class TestpageActivity extends Activity {

    private BaseLoadingPage mBaseLoadingPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_testpage);
        Logger.e("onCreate");
        initBasePage();

        setContentView(mBaseLoadingPage);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                mBaseLoadingPage.showSafePage(BaseLoadingPage.PAGE_STATE_SUCCESS);
            }
        }).start();

    }

    private void initBasePage() {
        mBaseLoadingPage = new BaseLoadingPage(TestpageActivity.this) {
            @Override
            protected View initView(Context context) {
                View view = View.inflate(context, R.layout.activity_testpage, null);
                return view;
            }
        };

    }




}
