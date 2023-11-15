package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yc.bean.Resadmin;
import com.yc.dao.ResadminMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
public class ResadminBizImpl implements  ResadminBiz{
    @Autowired
    private ResadminMapper resadminMapper;
    @Override
    public Resadmin login(String raname, String rapwd) {
        QueryWrapper<Resadmin> wrapper = new QueryWrapper<Resadmin>();

        wrapper.eq("raname",raname);
        wrapper.eq("rapwd",rapwd);
        Resadmin resadmin = resadminMapper.selectOne(   wrapper  );
        return resadmin;
    }
}
