package com.topfine.malltest.home.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.topfine.malltest.R;
import com.topfine.malltest.home.module.TopShopModle;

/**
 * Created by Littlezuo on 2016/11/26.
 */

public class TopShopViewHolder extends BaseViewHolder<TopShopModle.ListModle> {

    private TextView mTvGoodName;
    private TextView mTvGoodPrice;
    public TopShopViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_shop_good);
        mTvGoodName= $(R.id.good_name);
        mTvGoodPrice= $(R.id.good_price);
    }

    @Override
    public void setData(TopShopModle.ListModle data) {
        super.setData(data);
        mTvGoodName.setText(data.product_name);
        mTvGoodPrice.setText(data.market_price);
    }

}
