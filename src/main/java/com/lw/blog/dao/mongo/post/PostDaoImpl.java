package com.lw.blog.dao.mongo.post;

import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.dao.mongo.common.util.Pagination;
import com.lw.blog.model.Post;
import com.lw.blog.model.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */

@Repository
public class PostDaoImpl extends BaseDao<Post> implements PostDao {

	private SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public long getAllPostsNumber() {
		return this.findCount(new Query());
	}

	public List<Post> getAllPostsByPage(Pagination pagination) {
		Query query = new Query();
		query.addCriteria(new Criteria("is_exist").is(true));
		query.with(new Sort(Sort.Direction.DESC,"create_at"));
		query.skip(pagination.getSkip()).limit(pagination.getPageSize());
		return this.find(query);
	}

	public List<Post> findAllPosts() {
		Query query = new Query();
		query.addCriteria(new Criteria("is_exist").is(true));
		Sort sort = new Sort(Sort.Direction.DESC,"create_at");
		query.with(sort);
		return this.find(query);
	}

	public void updateBlog(String blogID, String blogName, String blogTag, String html) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(blogID));
		Update update = new Update();
		update.set("title",blogName);
		update.set("content",html);
		update.set("update_at",System.currentTimeMillis());
		update.set("is_exist",true);
		this.update(query, update);
	}

	@Override
	public Post findPostByName(String blogName) {
		Query query = new Query();
		query.addCriteria(new Criteria("title").is(blogName));
		return this.find(query).get(0);

	}


	public List<Post> findPostByYear(int year) {
		String largeTime = (1+year)+"-01-01 00:00:00";
		String smallTime = year+"-01-01 00:00:00";
		long t1=0,t2=0;
		try {
			t1 = format.parse(largeTime).getTime();
			t2 = format.parse(smallTime).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Query query = new Query();
		query.addCriteria(new Criteria("create_at").lt(t1).gte(t2));
		Sort sort = new Sort(Sort.Direction.DESC,"create_at");
		query.with(sort);
		return this.find(query);
	}

	public boolean deleteBlog(String blogId) {
		Query query  = new Query();
		query.addCriteria(new Criteria("_id").is(blogId));
		Update update = new Update();
		update.set("is_exist",false);
		this.update(query,update);
		return true;
	}
}
