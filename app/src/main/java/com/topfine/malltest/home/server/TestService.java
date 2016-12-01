package com.topfine.malltest.home.server;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;
import com.orhanobut.logger.Logger;
import com.topfine.malltest.base.net.Convert;
import com.topfine.malltest.base.net.basemodel.LzyResponse;
import com.topfine.malltest.base.net.callback.JsonCallback;
import com.topfine.malltest.constant.Constants;
import com.topfine.malltest.home.ShopBean;
import com.topfine.malltest.home.module.TopShopModle;
import com.topfine.malltest.home.viewmodle.ShopViewModle;
import com.topfine.malltest.home.viewmodle.TopShopViewModle;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Littlezuo on 2016/11/25.
 */

public class TestService {
    public static void getTestData(final ShopViewModle shopViewModle, final MyHttpCallBack easyRecyHttpCallback) {

        OkGo.get(Constants.GUESS_MAN)
                .params("pageIndex", shopViewModle.pageIndex)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShopBean shopBean = Convert.fromJson(s, ShopBean.class);
                        shopViewModle.mListBeanList = shopBean.data.list;
                        easyRecyHttpCallback.onSuccess();


                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        easyRecyHttpCallback.onFailure();
                    }
                });

        //        adapter.addAll();
    }

    public static void requestServer(final ShopViewModle viewModle, final MyHttpCallBack easyRecyHttpCallback) {
        OkGo.get(Constants.GUESS_MAN)
                .params("pageIndex", viewModle.pageIndex)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        ShopBean shopBean = Convert.fromJson(s, ShopBean.class);

                        viewModle.mListBeanList = shopBean.data.list;
                        easyRecyHttpCallback.onSuccess();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        easyRecyHttpCallback.onFailure();
                    }
                });
    }


    public static void request(final TopShopViewModle viewModle, final MyHttpCallBack easyRecyHttpCallback) {
        //        OkGo.get(Constants.GUESS_MAN)
        //                .params("pageIndex", viewModle.pageIndex)
        //                .execute(new StringCallback() {
        //                    @Override
        //                    public void onSuccess(String s, Call call, Response response) {
        //                        ShopBean shopBean = Convert.fromJson(s, ShopBean.class);
        //
        //                        viewModle.mListBeanList = shopBean.data.list;
        //                        //                        FrameworkSupport.mOnAppLoadMoreListener.onSuccess();
        //                    }
        //
        //                    @Override
        //                    public void onError(Call call, Response response, Exception e) {
        //                        super.onError(call, response, e);
        //                        FrameworkSupport.mOnAppLoadMoreListener.onFailure();
        //                    }
        //                });

        OkGo.get(Constants.GUESS_MAN)
                .params("pageIndex", viewModle.pageIndex)
                .execute(new JsonCallback<LzyResponse<TopShopModle>>() {

                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        Logger.e("getBaseUrl ==" + request.getBaseUrl() + "urlParamsMap" +request.getParams().urlParamsMap);
//                        request.getBaseUrl()
                    }


                    @Override
                    public void onSuccess(LzyResponse<TopShopModle> topShopModleLzyResponse, Call call, Response response) {
                        viewModle.mListModles = topShopModleLzyResponse.data.list;
                        easyRecyHttpCallback.onSuccess();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Logger.e("e.getMessage = " + e.getMessage() + "  e.toString =" + e.toString() + "  call" + call.request().toString());
                        easyRecyHttpCallback.onFailure();
                    }

                });

    }
}
