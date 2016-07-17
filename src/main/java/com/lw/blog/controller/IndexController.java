package com.lw.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by LWang on 2016/7/12.
 */

@Controller
@RequestMapping("/index")
public class IndexController {
	@RequestMapping("/toIndex")
	public String toIndex(){
		return "index/index";
	}
}
