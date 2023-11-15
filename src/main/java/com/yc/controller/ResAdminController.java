package com.yc.controller;

import com.yc.StringUtils;
import com.yc.bean.Resadmin;
import com.yc.bean.Resuser;
import com.yc.biz.ResadminBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@EnableSwagger2
@RestController
@RequestMapping("resadmin")
@Api(tags = "管理员")
@Slf4j
public class ResAdminController {
    @Autowired
    private ResadminBiz resadminBiz;


    @RequestMapping(value= "login",method ={POST} )
    public Map<String,Object> login(String raname,String rapwd, HttpSession session){
        System.out.println(raname+"---------------");
        System.out.println(rapwd);
        Map<String,Object>map = new HashMap<>();
        String code=(String) session.getAttribute("code");

        if (StringUtils.empty(raname) ||StringUtils.empty(rapwd)){
            map.put("code",-2);
            map.put("msg","用户名或密码为空");
            return map;
        }
//        处理密码md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(rapwd.getBytes());
//        访问业务层 login
        Resadmin ru = resadminBiz.login(raname,md5Pass);

        if(ru == null){
//            失败 则code=0
            map.put("code",-1);
            map.put("msg","登入失败用户名或密码错误");
            return map;
        }
//        成功
        map.put("code","1");
        ru.setRapwd("");
        session.setAttribute("resadmin",ru);
        map.put("obj",ru);
        return map;
    }
}
