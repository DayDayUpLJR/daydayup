package com.yc.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resuser;
import com.yc.bean.CartItem;

import java.util.Set;

public interface ResorderBiz {
    public int order(Resorder resorder, Set<CartItem> cartItemSet, Resuser resuser);
}
