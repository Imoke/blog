package com.lw.blog.controller;

import com.lw.blog.model.Post;
import com.lw.blog.model.Tag;
import com.lw.blog.service.blog.BlogService;
import com.lw.blog.service.tag.TagService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Created by LWang on 2016/7/17.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private TagService tagService;
	@Value("#{configProperties['mdFile.path']}")
	private String mdFilePath;
	@Value("#{configProperties['server.path']}")
	private String serverPath;
	@Value("#{configProperties['imgFile.path']}")
	private String imgFilePath;

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
		List<Post> posts = new ArrayList<Post>();
		if(!name.equals("")&&name!=null){
			Tag tag = tagService.getTagByClassName(name);
			if(tag!=null) {
				List<String> postIdlist = tag.get_blog_id();

				if (postIdlist!=null) {
					for (String postId : postIdlist) {
						//get blog by blog's id.
						Post post = new Post();
						post = blogService.getBlogById(postId);
						posts.add(post);
					}
					return posts;
				} else {
					return posts;
				}
			}else {
				return posts;
			}
		}else {
			return posts;
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
		if(postList.size()!=0) {
			int newYear = Integer.parseInt(format.format(postList.get(0).get_create_at()).substring(0, 4));
			int oldYear = Integer.parseInt(format.format(postList.get(postList.size() - 1).get_create_at()).substring(0, 4));
			if (oldYear == newYear) {
				years.add(newYear);
			} else {
				int d_value = newYear - oldYear;
				for (int i = 0; i <= d_value; i++) {
					years.add(newYear - i);
				}
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
	 *
	 * @param request
	 * @param newHtml
	 * @param file2
	 * @param blogName
	 * @param blogTag
	 * @param blogDes
	 * @param blogEngName
	 * @param markdown
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadEditBlog" ,method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public String uploadEditBlog(HttpServletRequest request,@RequestParam(value="add-blog-html") String newHtml,@RequestParam(value="add-fig", required=false) MultipartFile file2,
						 @RequestParam(value="add-blogName") String blogName , @RequestParam(value="add-blogTag") String blogTag,
						 @RequestParam(value="add-blogDes") String blogDes,@RequestParam(value="add-blogEngName") String blogEngName,
								 @RequestParam(value="add-blog-markdown") String markdown,
								 @RequestParam(value="add-blogID") String blogId) throws IOException {

			String imgRealPath=null;
			String imgName = null;
			try {
				// 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
				//String webRootPath = System.getProperty("ft.webapp");
				String rootPath = request.getSession().getServletContext().getRealPath("/resources/upload");
				File filePath=new File(rootPath);
				if(!filePath.exists()){
					filePath.mkdirs();
				}

				if(file2!=null){
					InputStream inImg = null;
					OutputStream outImg = null;
					try {
						imgRealPath = filePath.getAbsolutePath() + File.separator + file2.getOriginalFilename();
						imgName = file2.getOriginalFilename();
						File serverFileImg = new File(imgRealPath);
						inImg = file2.getInputStream();
						outImg = new FileOutputStream(serverFileImg);
						byte[] b2 = new byte[1024];
						int len2 = 0;
						while ((len2 = inImg.read(b2)) > 0) {
							outImg.write(b2, 0, len2);
						}
						outImg.close();
						inImg.close();
					}catch (Exception e){
						return "fail";
					}finally {
						if (outImg != null) {
							outImg.close();
							outImg = null;
						}

						if (inImg != null) {
							inImg.close();
							inImg = null;
						}
					}
				}
				//向后台数据库中传入数据

				blogService.insertBlog2database(blogName, blogTag, newHtml, blogDes, imgName, markdown);
				//update tags info
				tagService.updateTagofBlogId(blogName, blogTag);
				return "success";
			} catch (Exception e) {
				return "fail";
			}
	}


	@RequestMapping(value="/uploadUpdateBlog" ,method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public String uploadUpdateBlog(HttpServletRequest request,@RequestParam(value="add-blog-html") String newHtml,@RequestParam(value="add-fig", required=false) MultipartFile file2,
								 @RequestParam(value="add-blogName") String blogName , @RequestParam(value="add-blogTag") String blogTag,
								 @RequestParam(value="add-blogDes") String blogDes,@RequestParam(value="add-blogEngName") String blogEngName,
								 @RequestParam(value="add-blogID") String blogId, @RequestParam(value="add-blog-markdown") String markdown) throws IOException {

		String imgRealPath=null;
		String imgName = null;
		try {
			// 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
			//String webRootPath = System.getProperty("ft.webapp");
			String rootPath = request.getSession().getServletContext().getRealPath("/resources/upload");
			File filePath=new File(rootPath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}

			if(file2!=null){
				InputStream inImg = null;
				OutputStream outImg = null;
				try {
					/*File dirImg = new File(rootPath+blogEngName);
					if (!dirImg.exists())
						dirImg.mkdirs();*/
					imgRealPath = filePath.getAbsolutePath() + File.separator + file2.getOriginalFilename();
					imgName = file2.getOriginalFilename();
					File serverFileImg = new File(imgRealPath);
					inImg = file2.getInputStream();
					outImg = new FileOutputStream(serverFileImg);
					byte[] b2 = new byte[1024];
					int len2 = 0;
					while ((len2 = inImg.read(b2)) > 0) {
						outImg.write(b2, 0, len2);
					}
					outImg.close();
					inImg.close();
				}catch (Exception e){
					return "fail";
				}finally {
					if (outImg != null) {
						outImg.close();
						outImg = null;
					}

					if (inImg != null) {
						inImg.close();
						inImg = null;
					}
				}
			}
			//向后台数据库中传入数据

			blogService.updateBlog(blogId, blogName, blogTag, newHtml, markdown, blogDes, imgName);
			//update tags info
			tagService.updateTagofBlogId(blogName, blogTag);
			return "success";

		} catch (Exception e) {

			return "fail";
		}

	}

	/**
	 *
	 * @param request
	 * @param imgFile
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/uploadImg" ,method = RequestMethod.POST, produces = "application/json;charset=utf8")
	@ResponseBody
	public String uploadImg(HttpServletRequest request,@RequestParam(value="add-fig", required=true) MultipartFile imgFile
								 ) throws IOException {
		String imgName = null;
		String imgRealPath = null;
		try {
			// 获得在tomcat中项目的路径， 需要在web.xml配置ft.webapp
			//String webRootPath = System.getProperty("ft.webapp");
			String rootPath = request.getSession().getServletContext().getRealPath("/resources/upload");
			File filePath=new File(rootPath);
			if(!filePath.exists()){
				filePath.mkdirs();
			}

			if(imgFile!=null){
				InputStream inImg = null;
				OutputStream outImg = null;
				try {
					File dirImg = new File(rootPath);
					if (!dirImg.exists())
						dirImg.mkdirs();
					imgName = imgFile.getOriginalFilename();
					imgRealPath = dirImg.getAbsolutePath() + File.separator + imgFile.getOriginalFilename();
					File serverFileImg = new File(imgRealPath);
					inImg = imgFile.getInputStream();
					outImg = new FileOutputStream(serverFileImg);
					byte[] b2 = new byte[1024];
					int len2 = 0;
					while ((len2 = inImg.read(b2)) > 0) {
						outImg.write(b2, 0, len2);
					}
					outImg.close();
					inImg.close();
				}catch (Exception e){
					return "fail";
				}finally {
					if (outImg != null) {
						outImg.close();
						outImg = null;
					}

					if (inImg != null) {
						inImg.close();
						inImg = null;
					}
				}
			}

			return "location: "+serverPath+"/resources/upload/" + imgName;
		} catch (Exception e) {
			return "fail";
		}
	}
	/**
	 *
	 * @param blogId
	 * @return
	 */
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


