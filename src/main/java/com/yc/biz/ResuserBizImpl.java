package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resuser;
import com.yc.dao.ResuserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ResuserBizImpl implements ResuserBiz{
    @Autowired
    private ResuserMapper resuserMapper;

    @Override
    public Resuser findByName(String name) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",name);
        Resuser resuser = resuserMapper.selectOne(wrapper);
        return resuser;
    }

    @Override
    public Resuser findByName(String name, String password) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",name);
        wrapper.eq("pwd",password);
        Resuser resuser = resuserMapper.selectOne(wrapper);
        return resuser;
    }

    @Override
    public Resuser findById(Integer userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid",userid);
        Resuser resuser = resuserMapper.selectOne(wrapper);
        return resuser;
    }
}
