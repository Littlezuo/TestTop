package com.topfine.malltest.home.viewmodle;

import com.topfine.malltest.home.module.TopShopModle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Littlezuo on 2016/11/26.
 */

public class TopShopViewModle implements Serializable{
    public List<TopShopModle.ListModle> mListModles;
    public int pageIndex;
    public boolean isLoadMore;
    public int isEmpty;
}
