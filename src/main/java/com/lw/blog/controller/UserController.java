package com.lw.blog.controller;

/**
 * Created by LWang on 2016/7/10.
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/isLogin")
    @ResponseBody
    public boolean isUserLogin(){
        //获取session 的信息
        if(false){
            //如果用户是登录过的，存在session的信息
            return true;
        }else {
            return false;
        }
    }
}
