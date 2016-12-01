package com.topfine.malltest.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.topfine.malltest.R;

import java.util.ArrayList;
import java.util.List;



/**
 * 作为Fragment等的填充视图
 */
public abstract class BasePage extends FrameLayout implements View.OnClickListener {
    /**
     * 记载数据的结果
     */

    public static final int PAGE_LOADING = 0;//正在加载
    public static final int PAGE_ERROR = 1;//加载失败
    public static final int PAGE_EMPTY = 2;//加载为空
    public static final int PAGE_SUCCESS = 3;//加载成功
    public static final int PAGE_ERROR_LOGIN = 4;//需要登录
    public static final int PAGE_JUSTLOADING = 5;//需要登录


    private int mCurState;

    private int mPreState = -1;
//    private int mCurSupportState;

    /**
     *
     */
    private View mPageJustLoading;
    private View mPageLoading;
    private View mPageError;
    private View mPageEmpty;
    private View mPageLoginError;

    private View mPageSuccess;
    private List<View> mViewList;
    private LayoutInflater mInflater;


    public BasePage(Context context) {
        this(context, null);
    }

    public BasePage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BasePage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCurState = PAGE_LOADING;//默认为正在加载
//        mCurSupportState = getCurSupportState();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initView();//初始化视图
    }

//    protected abstract int getCurSupportState();

    /**
     * 初始化视图
     */
    private void initView() {
        mViewList = new ArrayList<>();

        //实例化没有网络的页面
        if (mPageError == null) {
            mPageError = mInflater.inflate(R.layout.view_page_error, null);
            addView(mPageError);
            mViewList.add(mPageError);
            LinearLayout llDataError = (LinearLayout) mPageError.findViewById(R.id.page_data_error);
            llDataError.setOnClickListener(this);
        }

        //实例化没有网络的页面
        if (mPageLoginError == null) {
            mPageLoginError = getErroeNoLoginView();
            addView(mPageLoginError);
            mViewList.add(mPageLoginError);
            LinearLayout llloginError = (LinearLayout) mPageLoginError.findViewById(R.id.page_login_error);
            llloginError.setOnClickListener(this);
        }
        //实例化网络数据为空
        if (mPageEmpty == null) {
            mPageEmpty = getEmptyView();
            addView(mPageEmpty);
            mViewList.add(mPageEmpty);
        }
        //实例化成功展示数据的页面
        if (mPageSuccess == null) {
            int successLayoutId = getSuccessLayoutId();
            mPageSuccess = mInflater.inflate(successLayoutId, null);
            addView(mPageSuccess);
            mViewList.add(mPageSuccess);
        }
        //实例化正在加载的页面
        if (mPageLoading == null) {
            mPageLoading = mInflater.inflate(R.layout.view_page_loading, null);
            addView(mPageLoading);
            mViewList.add(mPageLoading);
        }

        //实例化正在加载的页面
        if (mPageJustLoading == null) {
            mPageJustLoading = mInflater.inflate(R.layout.view_page_justloading, null);
            mPageJustLoading.bringToFront();
            addView(mPageJustLoading);
            mViewList.add(mPageJustLoading);
        }

        showPageByState();
    }

    /**
     * 空视图View
     *
     * @return
     */
    protected View getEmptyView() {
        return mInflater.inflate(getEmptyRes(), null);
    }

    /**
     * 空视图布局文件资源id
     *
     * @return
     */
    protected int getEmptyRes() {
        return R.layout.view_page_empty;
    }

    protected void setEmptyIconAndContent(int resId, int textId) {
//        ImageView img = (ImageView) findViewById(R.id.img_empty);
//        TextView content = (TextView) findViewById(R.id.tv_empty);
//        img.setImageResource(resId);
//        content.setText(getResources().getText(textId));
    }

    /**
     * @return
     */
    protected View getErroeNoLoginView() {
        return mInflater.inflate(getErroeNoLogin(), null);
    }

    /**
     * @return
     */
    protected int getErroeNoLogin() {
        return R.layout.view_page_error_nologin;
    }

//    protected abstract void setSwipRefreshLayout(SwipeRefreshLayout swipeRefreshLayout);
//
//    protected abstract void setUltimateRecyclerView(UltimateRecyclerView ultimateRecyclerView);
//
//    protected abstract int getRefreshViewid();

    /**
     *
     */
    private void showPageByState() {
        if (mCurState != mPreState) {

            mPageLoading.setVisibility(mCurState == PAGE_LOADING ? View.VISIBLE : View.GONE);
            mPageError.setVisibility(mCurState == PAGE_ERROR ? View.VISIBLE : View.GONE);
            mPageEmpty.setVisibility(mCurState == PAGE_EMPTY ? View.VISIBLE : View.GONE);
            mPageSuccess.setVisibility((mCurState == PAGE_SUCCESS) || (mCurState == PAGE_JUSTLOADING) || (mCurState == PAGE_LOADING) ? View.VISIBLE : View.GONE);
            mPageLoginError.setVisibility(mCurState == PAGE_ERROR_LOGIN ? View.VISIBLE : View.GONE);
            mPageJustLoading.setVisibility(mCurState == PAGE_JUSTLOADING ? View.VISIBLE : View.GONE);
        }
    }

    public void showCurrentPageByState(int curState) {
        this.mPreState = this.mCurState;
        this.mCurState = curState;
        showPageByState();
    }

    public int getCurrentState() {
        return mCurState;
    }

    /**
     * 返回Layout的布局id
     *
     * @return
     */
    protected abstract int getSuccessLayoutId();

    /**
     * 加载数据
     */
    protected abstract void reErrorRefreshData();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.page_data_error:
                reErrorRefreshData();
                break;
            case R.id.page_login_error:
//                Utility.startActivity(v.getContext(), LoginActivity.class);
                break;
            default:
                break;
        }
    }
}
