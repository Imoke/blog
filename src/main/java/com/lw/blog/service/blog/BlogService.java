package com.lw.blog.service.blog;

import com.lw.blog.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
@Service
public interface BlogService {

	List<Post> getAllPostsByPage(int page);
	
	Post getBlogById(String id);

	List<Post> getAllPosts();


	List<Post> getBlogByYear(int year);
	String renderToHtml(String markdownSource);
	
	void insertBlog2database(String blogName, String blogTag, String html, String blogDes,String imgRealPath,String markdown);
	
	void updateBlog(String blogID, String blogName, String blogTag, String html,String markdown,String blogDes, String imgRealPath);
	
	boolean deleteBlog(String blogId);


}
