package com.topfine.malltest.home.adapter;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.topfine.malltest.R;
import com.topfine.malltest.home.ShopBean;

/**
 * Created by Littlezuo on 2016/11/25.
 */

public class ShopViewHolder extends BaseViewHolder<ShopBean.DataBean.ListBean>{
    private TextView mTvGoodName;
    private TextView mTvGoodPrice;
    public ShopViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_shop_good);
        mTvGoodName= $(R.id.good_name);
        mTvGoodPrice= $(R.id.good_price);
    }

    @Override
    public void setData(ShopBean.DataBean.ListBean data) {
        super.setData(data);
        mTvGoodName.setText(data.product_name);
        mTvGoodPrice.setText(data.market_price);
    }
}
