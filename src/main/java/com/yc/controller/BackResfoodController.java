package com.yc.controller;

import com.yc.bean.Resfood;
import com.yc.biz.*;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@EnableSwagger2
@RestController
@RequestMapping("back/resfood")
@Api(tags = "后台菜品管理")
@Slf4j
public class BackResfoodController {
    @Autowired
    private FastDFSBizImpl fastDFSBiz;
    @Autowired
    private ResfoodBizImpl resfoodBiz;
    @RequestMapping(value = "/addNewFood",method = {RequestMethod.GET,RequestMethod.POST})
    public Map<String,Object>addNewFood(String fname,
                                        Double normprice,
                                        Double realprice,
                                        String detail,
                                        MultipartFile fphoto){
        Map<String,Object>map = new HashMap<>();
        Resfood rf = new Resfood();
        try {
            //        TODO：步骤一：将图片上传到fastDFS中，返回图片地址
//        真正的图片http://storage服务器中的nginx:8888
//        以配置文件形式在springboot配置 存到数据resfood表中的fphoto
            String path = this.fastDFSBiz.uploadFile(fphoto);
            rf.setFname(fname);
            rf.setNormprice(normprice);
            rf.setRealprice(realprice);
            rf.setDetail(detail);
            rf.setFphoto(path);
            resfoodBiz.addResfood(rf);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("obj", e.getMessage());
            return map;
        }
        map.put("code",1);
        map.put("obj", rf);
        return map;
    }
}
