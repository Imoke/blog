package com.lw.blog.dao.mongo.post;

import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.dao.mongo.common.util.Pagination;
import com.lw.blog.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */

@Repository
public class PostDaoImpl extends BaseDao<Post> implements PostDao {


	public long getAllPostsNumber() {
		return this.findCount(new Query());
	}

	public List<Post> getAllPostsByPage(Pagination pagination) {
		Query query = new Query();
		query.skip(pagination.getSkip()).limit(pagination.getPageSize());
		return this.find(query);
	}
}
