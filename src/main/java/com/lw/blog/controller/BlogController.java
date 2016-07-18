package com.lw.blog.controller;

import com.lw.blog.model.Post;
import com.lw.blog.service.blog.BlogService;
import com.lw.blog.service.blog.BlogServiceImpl;
import com.lw.blog.service.tag.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private TagService tagService;
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

	/**
	 *Get blog by blog's id.
	 * @param id 	a blog's id.
	 * @return
	 */
	@RequestMapping("/blog-detail/{id}")
	@ResponseBody
	public Post getPostById(@PathVariable String id){
		if(!id.equals("")&&id!=null) {
			Post post = blogService.getBlogById(id);
			return post;
		}
		else return null;
	}

	/**
	 *
	 * @param name tag's english name
	 * @return
	 */
	@RequestMapping("/blog-class/{name}")
	@ResponseBody
	public List<Post> getPostByClassName(@PathVariable String name){
		if(!name.equals("")&&name!=null){
			List<String> postIdlist = tagService.getTagByClassNume(name).get_blog_id();
			List<Post> posts = new ArrayList<Post>();
			for (String postId : postIdlist ) {
				//get blog by blog's id.
				Post post = new Post();
				post = blogService.getBlogById(postId);
				posts.add(post);
			}
			return posts;
		}else {
			return null;
		}

	}
}
