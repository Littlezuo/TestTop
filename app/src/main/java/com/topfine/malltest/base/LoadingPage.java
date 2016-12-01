package com.topfine.malltest.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.topfine.malltest.R;
import com.topfine.malltest.utils.UIUtil;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Littlezuo on 2016/9/21.
 * 提供不同的需要联网操作的Fragment的通用页面
 * 联网页面可能出现的四种状态
 * 1.正在加载
 * 2.加载(联网)失败
 * 3.联网成功,但是数据返回为空
 * 4.联网成功,且正确的返回数据
 */
public abstract class LoadingPage extends FrameLayout {
    //联网的四种状态
    private static final int PAGE_STATE_LOADING = 1;
    private static final int PAGE_STATE_ERROR = 2;
    private static final int PAGE_STATE_EMPTY = 3;
    private static final int PAGE_STATE_SUCCESS = 4;
    private final Context mContext;
    private String mUrl = null;

    //当前的状态
    private int currentState = PAGE_STATE_LOADING;

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;
    private ViewGroup.LayoutParams mParams;

    public LoadingPage(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        if (loadingView == null) {
            loadingView = View.inflate(mContext, R.layout.view_page_loading, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(loadingView, params);
        }

        if (errorView == null) {
            errorView = View.inflate(mContext, R.layout.view_page_error, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            errorView.findViewById(R.id.btn_neterror_reload).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentState = PAGE_STATE_LOADING;
                    //                    showSafePage();
                    //                    show(mUrl);
                    mErrorClickListener.onClick(v);
                }
            });
            addView(errorView, params);
        }

        if (emptyView == null) {
            emptyView = View.inflate(mContext, R.layout.view_page_empty, null);
            mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(emptyView, mParams);
        }

        showSafePage();

    }

    public interface ErrorClickListener {
        void onClick(View v);
    }

    private ErrorClickListener mErrorClickListener;

    public void setErrorClickListener(ErrorClickListener errorClickListener) {
        mErrorClickListener = errorClickListener;
    }

    /**
     * 保证View的显示在主线程执行
     */
    private void showSafePage() {
        UIUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPage();//显示具体的某一个页面
            }
        });
    }

    private void showPage() {
        //        LogUtil.e("调用showPage -----" + successView);
        loadingView.setVisibility(currentState == PAGE_STATE_LOADING ? VISIBLE : GONE);
        errorView.setVisibility(currentState == PAGE_STATE_ERROR ? VISIBLE : GONE);
        emptyView.setVisibility(currentState == PAGE_STATE_EMPTY ? VISIBLE : GONE);

        if (successView == null) {
            successView = initView(mContext);
            addView(successView, mParams);
        }

//        LogUtil.e("调用showPage 后-----" + successView);
        successView.setVisibility(currentState == PAGE_STATE_SUCCESS ? VISIBLE : GONE);
    }

    protected abstract View initView(Context context);

    /**
     * 实现联网操作,得到联网状态,修改当前的currentState;
     */
    public void show(String url) {
        mUrl = url;
        if (TextUtils.isEmpty(url)) {
            currentState = PAGE_STATE_SUCCESS;
            showSafePage();
            return;
        }

        OkGo.get(url).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
//                LogUtil.e("网络请求成功 ------");
                processData(s);
            }

            @Override
            public void onCacheSuccess(String s, Call call) {
                super.onCacheSuccess(s, call);
//                LogUtil.e("缓存获取成功");
                processData(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
//                LogUtil.e("请求失败 ------");
                currentState = PAGE_STATE_ERROR;
                showSafePage();
            }
        });
    }

    private void processData(String result) {
        if (TextUtils.isEmpty(result)) {
            currentState = PAGE_STATE_EMPTY;
            showSafePage();
        } else {
            currentState = PAGE_STATE_SUCCESS;
            showSafePage();
            onSuccess(result, successView);
        }
    }

    protected abstract void onSuccess(String result, View successView);

}
