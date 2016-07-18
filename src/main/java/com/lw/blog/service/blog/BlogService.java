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
}
