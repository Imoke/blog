package com.lw.blog.controller;

import com.lw.blog.model.Post;
import com.lw.blog.service.blog.BlogService;
import com.lw.blog.service.tag.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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

	private static final Logger logger = Logger.getLogger(BlogController.class);
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

	/**
	 * Get all blog by year
	 * @return
	 */
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

	/**
	 * file upload and add blog
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/upload" ,method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public String upload(@RequestParam("add-blog") MultipartFile file,@RequestParam("add-blogName") String blogName ,
						 @RequestParam("add-blogTag") String blogTag,@RequestParam("add-blogDes") String blogDes) throws IOException {
		if (!file.isEmpty()) {
			InputStream in = null;
			OutputStream out = null;

			try {
				// 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
				String webRootPath = System.getProperty("ft.webapp");
				String html = null;
				//logger.info(webRootPath);
				// String rootPath = System.getProperty("catalina.home");
				File dir = new File(webRootPath + File.separator + "uploadFiles");
				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					html += new String(b,0,len,"UTF-8");
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				html = blogService.renderToHtml(html);
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				logger.info("blogName:　" + blogName);
				logger.info("blogTag: " + blogTag);
				logger.info("blog: "+html);
				logger.info("blogDes: "+blogDes);

				//insert form to database

				blogService.insertBlog2database(blogName, blogTag, html, blogDes);
				//update tags info
				tagService.updateTagofBlogId(blogName,blogTag);
				return "success";

			} catch (Exception e) {

				return "fail";
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}

				if (in != null) {
					in.close();
					in = null;
				}
			}
		} else {

			return null;
		}
	}

	/**
	 * edit blog
	 * @param file
	 * @param blogName
	 * @param blogTag
	 * @param blogID
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/editBlog" ,method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public String editBlog(@RequestParam("edit-blog") MultipartFile file,@RequestParam("edit-blogID") String blogID,
						   @RequestParam("edit-blogName") String blogName , @RequestParam("edit-blogTag") String blogTag) throws IOException {
		if (!file.isEmpty()) {
			InputStream in = null;
			OutputStream out = null;

			try {
				// 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
				String webRootPath = System.getProperty("ft.webapp");
				String html = null;
				//logger.info(webRootPath);
				// String rootPath = System.getProperty("catalina.home");
				File dir = new File(webRootPath + File.separator + "uploadFiles");
				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				in = file.getInputStream();
				out = new FileOutputStream(serverFile);
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = in.read(b)) > 0) {
					html += new String(b,0,len,"UTF-8");
					out.write(b, 0, len);
				}
				out.close();
				in.close();
				html = blogService.renderToHtml(html);
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				logger.info("blogName:　" + blogName);
				logger.info("blogTag: " + blogTag);
				logger.info("blog: "+html);


				//insert form to database

				blogService.updateBlog(blogID, blogName, blogTag, html);

				return "success";

			} catch (Exception e) {

				return "fail";
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}

				if (in != null) {
					in.close();
					in = null;
				}
			}
		} else {

			return null;
		}
	}

	@RequestMapping("/deleteBlog")
	@ResponseBody
	public Boolean deleteBlog(@RequestParam String blogId){
		boolean isDel = false;
		if(blogId!=null){
			isDel = blogService.deleteBlog(blogId);
		}
		return isDel;
	}

	/**
	 * check is input tags exist in tag table
	 * @param blogTag include not only one tag,splited use #.
	 * @return
	 */
	@RequestMapping("/checkTagisExist")
	@ResponseBody
	public Boolean isExistTags(@RequestParam String blogTag){
		boolean exist ;
		exist = tagService.isExistTags(blogTag);
		if(exist){
			return true;
		}else {
			return false;
		}
	}

}


