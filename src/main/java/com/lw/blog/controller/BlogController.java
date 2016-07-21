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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * Get all posts and sort by time in page.
	 * @return
	 */
	@RequestMapping("/{page}")
	@ResponseBody
	public List<Post> getAllPosts(@PathVariable int page){
		List <Post> posts = blogService.getAllPostsByPage(page);
		return posts;
	}

	/**
	 * Get all posts's numbers.
	 * @return
	 */
	@RequestMapping("/num")
	@ResponseBody
	public long getPostPagesNum(){
		List<Post> postList = blogService.getAllPosts();
		int posts = postList.size();
		int pageNum = 0;
		if(posts<5){
			pageNum = 1;
		}else {
			if ((posts%5) > 0){
				pageNum = (posts/5)+1;
			}else {
				pageNum = posts/5;
			}
		}
		return pageNum;
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
			List<String> postIdlist = tagService.getTagByClassName(name).get_blog_id();
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

	@RequestMapping("/all_blog")
	@ResponseBody
	public List<List<Post>> getPostByYear(){
		List<Integer> years = new ArrayList<Integer>();
		List<Post> postList = blogService.getAllPosts();
		//get years
		int newYear = Integer.parseInt(format.format(postList.get(0).get_create_at()).substring(0,4));
		int oldYear = Integer.parseInt(format.format(postList.get(postList.size()-1).get_create_at()).substring(0,4));
		if (oldYear == newYear){
			years.add(newYear);
		}else {
			int d_value = newYear - oldYear;
			for (int i=0;i<=d_value;i++){
				years.add(newYear-i);
			}
		}
		List<List<Post>> postPerYear =  new ArrayList<List<Post>>();
		for(int i=0;i<years.size();i++){
			int year = years.get(i);
			List<Post> posts = blogService.getBlogByYear(year);
			postPerYear.add(posts);
		}
		return postPerYear;
	}

}
