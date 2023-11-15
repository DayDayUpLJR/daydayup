package com.yc.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.RedisKeys;
import com.yc.bean.Resfood;
import com.yc.dao.ResfoodMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(readOnly = true)
@Slf4j
public class ResfoodBizImpl implements ResfoodBiz{
    @Autowired
    private ResfoodMapper resfoodMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<Resfood> findAll() {
        List<Resfood>list = resfoodMapper.selectList(null);
        return list;
    }

    @Override
    public Resfood findById(Integer fid) {
        QueryWrapper<Resfood> wrapper =  new QueryWrapper();
        wrapper.eq("fid",fid);
        Resfood resfood = resfoodMapper.selectOne(   wrapper );
        return resfood;
    }
//pageno 第几页 pagesize 每页多少条数据 sortby根据那列排序 sort  asc/desc
    @Override
    public Page<Resfood> findByPage(int pageno, int pagesize, String sortby, String sort) {
//        Page<Resfood>page = new Page<>();
//        OrderItem orderItem = new OrderItem();
//        orderItem.setColumn(sortby);
//        orderItem.setAsc("asc".equalsIgnoreCase(sort));
//        page.setSize(pagesize);
//        page.setCurrent(pageno);
//        page.addOrder(orderItem);
//
//        QueryWrapper<Resfood>wrapper = new QueryWrapper<>();
        System.out.println(pagesize+"-=-=-=-=-=-=-=-=-=");
        QueryWrapper wrapper = new QueryWrapper();
        if("asc".equalsIgnoreCase(sort)){
            wrapper.orderByAsc(sortby);
        }else {
            wrapper.orderByDesc(sortby);
        }
        Page<Resfood> Page = new Page<>(pageno,pagesize);
//        TODO: 要到redis查询这些resfood的浏览数
        //        执行设置查询
        Page<Resfood>resfoodPage = this.resfoodMapper.selectPage(Page,wrapper);
        List<Resfood>list = resfoodPage.getRecords();
        List<String>key = new ArrayList<>();
        for (Resfood rf: list) {
            key.add(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+rf.getFid());
        }
        List<Integer> allFoodDetailCountValues = redisTemplate.opsForValue().multiGet(key);
        for (int i = 0; i < list.size(); i++) {
            if(   allFoodDetailCountValues.get(i)==null){
                continue;
            }
            list.get(i).setDetail_count(    allFoodDetailCountValues.get(i));
        }
        log.info("总记录数 = "+resfoodPage.getTotal());
        log.info("总页数 = "+resfoodPage.getPages());
        log.info("当前页码 = "+resfoodPage.getCurrent());

        return resfoodPage;
    }
@Transactional(
        propagation = Propagation.SUPPORTS, //支持事务环境下运行
        isolation = Isolation.DEFAULT,//隔离级别与数据库一致
        timeout = 2000,
        readOnly = false,
        rollbackFor = RuntimeException.class
)
    @Override
    public Integer addResfood(Resfood food) {
        int test = resfoodMapper.insert(food);
        if (test!=0){
            return 1;
        }
//        QueryWrapper wrapper = new QueryWrapper();
//        wrapper.eq("fname",food.getFname());
//        wrapper.eq("normprice",food.getNormprice());
//        wrapper.eq("fphoto",food.getFphoto());
//        wrapper.eq("realprice",food.getRealprice());
//        wrapper.eq("detail",food.getDetail());

        return null;
    }
}
