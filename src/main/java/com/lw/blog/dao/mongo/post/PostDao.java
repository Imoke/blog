package com.lw.blog.dao.mongo.post;

import com.lw.blog.dao.mongo.common.util.Pagination;
import com.lw.blog.model.Post;
import com.lw.blog.model.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */

@Transactional
public interface PostDao {
    long getAllPostsNumber();
	List<Post> getAllPostsByPage(Pagination pagination);
	
	List<Post> findAllPosts();
	
	void updateBlog(String blogID, String blogName, String blogTag, String html);
	
	Post findPostByName(String blogName);
}
