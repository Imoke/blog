package com.lw.blog.controller;

/**
 * Created by LWang on 2016/7/10.
 */

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.lw.blog.model.user.User;
import com.lw.blog.service.user.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/user")
public class UserController {
	@Resource

	private UserServiceImpl userService;
	@RequestMapping("/showUser")
	public String toIndex(HttpServletRequest request,Model model){
		String userName= request.getParameter("username");
		User user = this.userService.findUserByName(userName);
		model.addAttribute("user", user);
		return "showUser";
	}
}
