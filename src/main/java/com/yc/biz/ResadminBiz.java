package com.yc.biz;

import com.yc.bean.Resadmin;
import org.springframework.stereotype.Service;

public interface ResadminBiz {
    public Resadmin login(String raname, String rapwd);
}
