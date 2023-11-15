package com.yc.biz;

import com.yc.bean.Resuser;

public interface ResuserBiz {
    public Resuser findByName(String name);
    public Resuser findByName(String name,String password);
    public Resuser findById(Integer userid);
}
