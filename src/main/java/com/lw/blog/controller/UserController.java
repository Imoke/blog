package com.lw.blog.controller;

import com.lw.blog.service.user.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;

@Controller
@RequestMapping("/user")
@SessionAttributes("username")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    private static Logger logger = Logger.getLogger(UserController.class);

    /**
     * 判断用户是否是登录过的
     * @return true 表示登录过的用户
     */
    @RequestMapping("/isLogin")
    @ResponseBody
    public boolean isUserLogin(HttpSession session){
        //返回状态值
        boolean isLogin = false;
        //获取session 的信息
        Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String s = e.nextElement();
            logger.info(s + " == " + session.getAttribute(s));
            if(s.equals("username")&&!session.getAttribute(s).equals("")&&session.getAttribute(s)!=null){
                isLogin = true;
            }
        }
        return isLogin;
    }

    /**
     * 注册用户入库
     * @param username 用户名
     * @param password 密码
     * @return bool值，true表示注册成功，false表示失败
     */
    @RequestMapping("/register")
    @ResponseBody
    public boolean userRegister(@RequestParam (value = "username") String username ,
                                @RequestParam( value = "password") String password
    ){
        if(!username.equals("")&&!password.equals("")){
            //todo 密码的加密，然后替换掉passowd
            userService.userRegister(username,password);
            return true;
        }else {
            return false;
        }
    }

    @RequestMapping("/login")
    @ResponseBody
    public String  userLogin(@RequestParam (value = "username") String username ,
                             @RequestParam( value = "password") String password,
                             Model model, HttpSession session){
        String backMessage;
        if(!username.equals("")&&!password.equals("")){

            //todo 密码的解密，或者用同样的方式加密后和数据库对比
            String result =userService.userLogin(username,password);
            switch (result) {
                case "NO_USER": //没有用户
                    backMessage = "11";
                    break;
                case "NO_PASS":  //密码不对
                    backMessage = "12";
                    break;
                case "OK": //通过
                    logger.debug("OK");
                    model.addAttribute("username",username);
                    Map modelMap = model.asMap();
                    for (Object modelKey : modelMap.keySet()) {
                        Object modelValue = modelMap.get(modelKey);
                        logger.info(modelKey + " -- " + modelValue);
                    }

                    Enumeration<String> e = session.getAttributeNames();
                    while (e.hasMoreElements()) {
                        String s = e.nextElement();
                        logger.info(s + " == " + session.getAttribute(s));
                    }
                    backMessage = "0";
                    break;
                default:
                    //未知错误
                    backMessage = "13";
                    break;
            }

        }else {
            backMessage="10"; //用户名或者密码为空
            return backMessage;
        }
        return backMessage;
    }


    @RequestMapping("/thirdPart_login")
    @ResponseBody
    public String  thirdPartLogin(@RequestParam (value = "username") String username ,
                                  @RequestParam (value = "imageUrl") String imageUrl ,
                             @RequestParam( value = "thirdPart") String thirdPart,
                             Model model, HttpSession session){
        String backMessage;
        //首先把第三方的登录信息存入数据库
        boolean isInsert = userService.insertThirdPartUserInfo(username,imageUrl,thirdPart);
        //把用户名保存在session中
        if(isInsert){
        model.addAttribute("username",username);
         Map modelMap = model.asMap();
         for (Object modelKey : modelMap.keySet()) {
               Object modelValue = modelMap.get(modelKey);
               logger.info(modelKey + " -- " + modelValue);
         }

          Enumeration<String> e = session.getAttributeNames();
          while (e.hasMoreElements()) {
              String s = e.nextElement();
               logger.info(s + " == " + session.getAttribute(s));
          }
          return "1";
    }else{
          return "0";
    }}

    /**
     *
     * @param register_username
     * @return
     */
    @RequestMapping("/checkRegisterUserName")
    @ResponseBody
    public Boolean isExistUserName(@RequestParam String register_username){
        boolean exist = false;
        exist = userService.isExistLocalUserName(register_username);
        if(exist){
            return false;
        }else {
            return true;
        }
    }
}
