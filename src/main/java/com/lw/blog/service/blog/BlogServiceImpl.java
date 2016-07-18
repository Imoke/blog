package com.lw.blog.service.blog;

import com.lw.blog.dao.mongo.common.util.Pagination;
import com.lw.blog.dao.mongo.post.PostDaoImpl;
import com.lw.blog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
@Service
public class BlogServiceImpl implements BlogService{
	@Autowired
	private PostDaoImpl postDao;
	public List<Post> getAllPostsByPage(int page) {
		long totalNumber = postDao.getAllPostsNumber();
		Pagination pagination = new Pagination(page,totalNumber);
		return postDao.getAllPostsByPage(pagination);

	}

	public Post getBlogById(String id) {
		return postDao.findOne(id);
	}
}
