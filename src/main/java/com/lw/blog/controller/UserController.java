package com.lw.blog.controller;

/**
 * Created by LWang on 2016/7/10.
 */


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/isLogin")
    public boolean isUserLogin(){
        //获取session 的信息
        if(true){
            return true;
        }else {
            return false;
        }
    }
}
