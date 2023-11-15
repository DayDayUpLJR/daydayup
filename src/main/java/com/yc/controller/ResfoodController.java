package com.yc.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.bean.MyPageBean;
import com.yc.bean.RedisKeys;
import com.yc.bean.Resfood;
import com.yc.biz.ResfoodBizImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.yc.bean.RedisKeys.RESFOOD_DETAIL_COUNT_FID_;

@EnableSwagger2
@RestController
@RequestMapping("resfood")
@Slf4j
@Api("食品购买")
public class ResfoodController {
    @Autowired
    private ResfoodBizImpl resfoodBiz;
    @Autowired
    private RedisTemplate redisTemplate;
    @RequestMapping(value = "/detailCountAdd",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="查询详情次数增加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid",value = "菜单号",required = true)
    })
    public Map<String,Object>detailCountAdd(Integer fid){
        Map<String,Object> map = new HashMap<>();
        Long count = 1L;
        if(redisTemplate.hasKey(RedisKeys.RESFOOD_DETAIL_COUNT_FID_+fid)==false){
            redisTemplate.opsForValue().set(RESFOOD_DETAIL_COUNT_FID_+fid,1);
        }else {
            count = redisTemplate.opsForValue().increment(RedisKeys.RESFOOD_DETAIL_COUNT_FID_ + fid);
        }
        map.put("code",1);
        map.put("obj",count);//obj存正常返回的运算结果
        return map;
    }
    @GetMapping("/findFood")
    private List<Resfood> findAll(){
        return resfoodBiz.findAll();
    }
    @GetMapping("/findById/{fid}")
    private Map<String,Object> findById(@PathVariable Integer id){
        Map<String,Object>map = new HashMap<>();
        Resfood rf = null;
        try{
            rf = resfoodBiz.findById(id);
            map.put("code",0);
            map.put("data",rf);
            map.put("res","ok");
        }catch (Exception e){
          e.printStackTrace();
        }
       return map;
    }
    @RequestMapping(value = "/findByPage",method = {RequestMethod.GET,RequestMethod.POST})
    @ApiOperation(value="分页查询操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageno",value = "页号",required = true),
            @ApiImplicitParam(name = "pagesize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "sortby",value = "排序列",required = true),
            @ApiImplicitParam(name = "sort",value = "排序方法",required = true)
    })
    private Map<String, Object> findByPage(@RequestParam int pageno, @RequestParam int pagesize, @RequestParam(required = false) String sortby, @RequestParam(required = false) String sort){
        Map<String, Object>map = new HashMap<>();
        Page<Resfood>page = null;
        try{
            page = this.resfoodBiz.findByPage(pageno,pagesize,sortby,sort);
            System.out.println("=========test=========");
        }catch (Exception e){
            map.put("code",0);
            map.put("msg",e.getCause());
            e.printStackTrace();
            return map;
        }

        map.put("code",1);
        //方案一个web model 用于在web界面/app界面上显示结果
        MyPageBean pageBean = new MyPageBean();
        pageBean.setPageno(pageno);
        pageBean.setPagesize(pagesize);
        pageBean.setSortby(sortby);
        pageBean.setSort(sort);
        pageBean.setTotal(page.getTotal());//总记录数
        pageBean.setDataset(page.getRecords());//记录集合
        //其他的分页数据
        //计算总页数
        long totalPages = page.getTotal()%pageBean.getPagesize()==0?
        page.getTotal()/pageBean.getPagesize():page.getTotal()/pageBean.getPagesize()+1;
        pageBean.setTotalpages((int) totalPages);
//        上一页也到的计算
        if(pageBean.getPageno()<=1){
            pageBean.setPre(1);
        }else {
            pageBean.setPre(pageBean.getPageno()-1);
        }
//        下一页的页号
        if(pageBean.getPageno() == totalPages){
            pageBean.setNext((int) totalPages);
        }else {
            pageBean.setNext(pageBean.getPageno()+1);
        }
//        方案二 ：spring BeanUtils框架来自自动转化
//        BeanUtils.copyProperties(page,pageBean);

        map.put("data",pageBean);
        return map;
    }
    @GetMapping("addFood")
    private void AddFood(@RequestParam Resfood resfood){
        resfoodBiz.addResfood(resfood);
    }
}
