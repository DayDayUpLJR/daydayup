package com.yc.biz;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.Resfood;

import java.util.List;

public interface ResfoodBiz {
//    查所有的菜品
    public List<Resfood> findAll();
    public Resfood findById(Integer fid);
//    mybatis_plus自带的分页查询
    public Page<Resfood> findByPage(int Pageno, int pagesize, String sortby, String sort);
    public Integer addResfood(Resfood food);

}
