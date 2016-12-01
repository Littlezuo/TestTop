package com.topfine.malltest.home.viewmodle;

import com.topfine.malltest.home.ShopBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Littlezuo on 2016/11/25.
 */

public class ShopViewModle extends BaseViewModle {
    public int pageIndex;
    public boolean isLoadMore;
    public int isEmpty;
    public List<ShopBean.DataBean.ListBean> mListBeanList = new ArrayList<>();

}
