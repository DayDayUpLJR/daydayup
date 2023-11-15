package com.yc.controller;

import com.yc.StringUtils;
import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//静态导入：导入静态常量和静态属性
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@EnableSwagger2
@RestController
@RequestMapping("resuser")
@Api(tags = "用户操作")
@Slf4j
public class ResuserController {
    @Autowired
    private ResuserBiz resuserBiz;
    @RequestMapping(value = "logout",method = {GET,POST})
    public Map<String,Object>logout(HttpSession session){
        Map<String,Object>map = new HashMap<>();
        session.removeAttribute("resuser");
        session.invalidate();
        map.put("code",1);
        return map;
    }
    @RequestMapping(value="isLogin",method = {GET,POST})
    public Map<String,Object>isLogin(HttpSession session){
         Map<String,Object>map = new HashMap<>();
         if(session.getAttribute("resuser")==null){
             map.put("code",0);
         }else{
             map.put("code",1);
             Resuser resuser = (Resuser) session.getAttribute("resuser");
             map.put("obj",resuser);
         }
         return map;
    }
    @RequestMapping(value= "login",method ={GET,POST} )
    public Map<String,Object>login(Resuser resuser, HttpSession session){
        Map<String,Object>map = new HashMap<>();
        String code=(String) session.getAttribute("code");
        if(!resuser.getYzm().equals(code)){
            map.put("code",-1);
            map.put("msg","验证码错误");
            return map;
        }
        if (StringUtils.empty(resuser.getUsername()) ||StringUtils.empty(resuser.getPwd())){
            map.put("code",-2);
            map.put("msg","用户名或密码为空");
            return map;
        }
//        处理密码md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(resuser.getPwd().getBytes());
//        访问业务层 login
        Resuser ru = resuserBiz.findByName(resuser.getUsername(),md5Pass);

        if(ru == null){
//            失败 则code=0
            map.put("code",-1);
            map.put("msg","登入失败用户名或密码错误");
            return map;
        }
//        成功
        map.put("code","1");
        session.setAttribute("resuser",ru);
        ru.setPwd("");
        map.put("obj",ru);
        return map;
    }
}
