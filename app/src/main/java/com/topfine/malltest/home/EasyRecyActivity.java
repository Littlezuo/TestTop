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
import com.orhanobut.logger.Logger;
import com.topfine.malltest.R;
import com.topfine.malltest.base.BaseActivity;
import com.topfine.malltest.base.net.Convert;
import com.topfine.malltest.constant.Constants;
import com.topfine.malltest.home.adapter.ShopViewHolder;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class EasyRecyActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter adapter;
    private int pageIndex = 0;
    private List<ShopBean.DataBean.ListBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_recy);

        long time1=Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10));
        Logger.e("time --" + time1);

        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new ShopViewHolder(parent);
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

        onRefresh();
    }

    @Override
    public void onLoadMore() {
        pageIndex++;
        loadMoreData(pageIndex);
        //        loadMoreData(pageIndex);
    }

    @Override
    public void onRefresh() {

        refreshData(0);
    }

    public void refreshData(int pageIndex) {
        OkGo.get(Constants.GUESS_MAN)
                .params("pageIndex", pageIndex)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShopBean shopBean = Convert.fromJson(s, ShopBean.class);
                        mList = shopBean.data.list;
                        List allData = adapter.getAllData();
                        adapter.clear();
                        mList.addAll(allData);
                        adapter.addAll(mList);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logger.e("onError" + response + "------" + e.toString());
                        //                        adapter.pauseMore();
                        if (adapter.getAllData() == null || adapter.getAllData().size() == 0) {
                            recyclerView.showEmpty();
                            return;
                        } else {
                            recyclerView.showRecycler();
                        }
                        //                        recyclerView.showError();
                        //                        recyclerView.setRefreshing(false);
                    }
                });
    }

    public void loadMoreData(int pageIndex) {
        OkGo.get(Constants.GUESS_MAN)
                .params("pageIndex", pageIndex)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShopBean shopBean = Convert.fromJson(s, ShopBean.class);
                        mList = shopBean.data.list;
                        adapter.addAll(mList);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logger.e("onError" + response + "------" + e.toString());
                        //                        adapter.
                        adapter.pauseMore();
                    }
                });
    }

}
