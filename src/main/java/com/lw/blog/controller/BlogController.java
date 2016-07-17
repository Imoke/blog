package com.lw.blog.controller;

import com.lw.blog.model.Post;
import com.lw.blog.service.blog.BlogService;
import com.lw.blog.service.blog.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	/**
	 * Get all posts and sort by time.
	 * @return
	 */
	@RequestMapping("/{page}")
	@ResponseBody
	public List<Post> getAllPosts(@PathVariable int page){
		List <Post> posts = blogService.getAllPostsByPage(page);
		return posts;
	}
}
