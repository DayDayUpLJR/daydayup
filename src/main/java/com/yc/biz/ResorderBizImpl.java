package com.yc.biz;

import com.yc.bean.Resorder;
import com.yc.bean.Resorderitem;
import com.yc.bean.Resuser;
import com.yc.dao.ResorderitemMapper;
import com.yc.dao.ResorderMapper;

import com.zaxxer.hikari.util.IsolationLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.yc.bean.CartItem;
import javax.swing.*;
import java.util.Set;

@Service
@Transactional(
        propagation = Propagation.REQUIRED,
        isolation = Isolation.DEFAULT,
        timeout = 2000,
        readOnly = false,
        rollbackFor = RuntimeException.class
)
public class ResorderBizImpl implements ResorderBiz{

    @Autowired
    private ResorderMapper resorderMapper;
    @Autowired
    private ResorderitemMapper resorderitemMapper;
    @Override
    public int order(Resorder resorder, Set<CartItem> cartItems, Resuser resuser) {
        resorder.setUserid(resuser.getUserid()); //订单用户的编号
        this.resorderMapper.insert(resorder); //订单信息会自动生成订单id
        for (CartItem cartItem : cartItems) {
            Resorderitem roi = new Resorderitem();
            roi.setRoid(resorder.getRoid()); //订单号， 不管循环多少次都一样
            roi.setFid(cartItem.getFood().getFid());//获取id
            roi.setDealprice(cartItem.getFood().getRealprice()); //成交价
            roi.setNum(cartItem.getNum()); //商品的数量
            this.resorderitemMapper.insert(roi);
        }
        return 0;
    }



}
