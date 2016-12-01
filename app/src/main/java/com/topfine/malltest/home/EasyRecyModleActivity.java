package com.topfine.malltest.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.topfine.malltest.R;
import com.topfine.malltest.base.BaseActivity;
import com.topfine.malltest.home.adapter.TopShopViewHolder;
import com.topfine.malltest.home.server.MyHttpCallBack;
import com.topfine.malltest.home.server.TestService;
import com.topfine.malltest.home.viewmodle.TopShopViewModle;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class EasyRecyModleActivity extends BaseActivity
        implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter adapter;
    private int pageIndex = 0;
    private List<ShopBean.DataBean.ListBean> mList;
    private TopShopViewModle mViewModle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_easy_recy);
        mViewModle = new TopShopViewModle();
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
//                return new ShopViewHolder(parent);
                return new TopShopViewHolder(parent);

            }
        });
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        recyclerView.setRefreshListener(this);

        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                //                refreshData(0);
                adapter.resumeMore();
            }
        });

//        TestService.getTestData(mViewModle, easyRecyHttpCallback);
        TestService.request(mViewModle ,easyRecyHttpCallback);
    }

    @Override
    public void onLoadMore() {
        mViewModle.isLoadMore = true;
        mViewModle.pageIndex++;
//        TestService.requestServer(mViewModle, easyRecyHttpCallback);
        TestService.request(mViewModle, easyRecyHttpCallback);
        //        loadMoreData(pageIndex);
        OkGo.get("url").execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {

            }


        });
    }


    @Override
    public void onRefresh() {
        mViewModle.pageIndex = 300;
//        TestService.requestServer(mViewModle, easyRecyHttpCallback);
        TestService.request(mViewModle, easyRecyHttpCallback);
    }


    MyHttpCallBack easyRecyHttpCallback = new MyHttpCallBack() {
        @Override
        protected void onSuccess() {


            if (!mViewModle.isLoadMore) {
                adapter.clear();
            }
            adapter.addAll(mViewModle.mListModles);
            //            adapter.addAll();
        }

        @Override
        protected void onFailure() {
            super.onFailure();
            if (adapter.getAllData() == null || adapter.getAllData().size() == 0) {
                recyclerView.showEmpty();
            } else {
                adapter.pauseMore();
            }
        }
    };
}
