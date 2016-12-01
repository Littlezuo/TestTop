package com.topfine.malltest.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.topfine.malltest.R;
import com.topfine.malltest.utils.UIUtil;

/**
 * Created by Littlezuo on 2016/9/21.
 * 提供不同的需要联网操作的Fragment的通用页面
 * 联网页面可能出现的四种状态
 * 1.正在加载
 * 2.加载(联网)失败
 * 3.联网成功,但是数据返回为空
 * 4.联网成功,且正确的返回数据
 */
public abstract class BaseLoadingPage extends FrameLayout {
    //联网的四种状态
    public static final int PAGE_STATE_LOADING = 1;
    public static final int PAGE_STATE_ERROR = 2;
    public static final int PAGE_STATE_EMPTY = 3;
    public static final int PAGE_STATE_SUCCESS = 4;
    public static final int PAGE_STATE_NOLOGIN = 5;
    private final Context mContext;


    //当前的状态
    private int currentState = PAGE_STATE_LOADING;

    private View loadingView;
    private View errorView;
    private View emptyView;
    private View successView;
    private View noLoginView;
    private ViewGroup.LayoutParams mParams;
//    mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    public BaseLoadingPage(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BaseLoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
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

        if (noLoginView == null) {
            noLoginView = View.inflate(mContext, R.layout.view_page_empty, null);
            mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            addView(noLoginView, mParams);
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

    public void showPage(int curState) {
        currentState = curState;
        showPage();
    }

    public void showSafePage(int curState) {
        currentState = curState;
        showSafePage();
    }

    protected abstract View initView(Context context);



}
