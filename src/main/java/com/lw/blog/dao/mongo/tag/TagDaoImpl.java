package com.lw.blog.dao.mongo.tag;

import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.model.Tag;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by LWang on 2016/7/17.
 */

@Repository
public class TagDaoImpl extends BaseDao<Tag> implements TagDao {

	public Tag getTagByClassName(String name) {
		Query query = new Query();
		query.addCriteria(new Criteria("name_eng").is(name));
		if(this.find(query).size()!=0){
			return this.find(query).get(0);
		}else {
			return null;
		}
	}

	public Tag findTagByName(String tagName) {
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("is_exist").is(true),
				Criteria.where("name").is(tagName)));
		List<Tag> tags = this.find(query);
		if((tags.size()!=0)&&tags!=null){
			return tags.get(0);
		}else {
			return null;
		}
	}

	public Tag findTagByEngName(String tagEngName) {
		Query query = new Query();
		//query.addCriteria(new Criteria("name_eng").is(tagEngName));
		query.addCriteria(new Criteria().andOperator(Criteria.where("is_exist").is(true),
				Criteria.where("name_eng").is(tagEngName)));
		List<Tag> tags = this.find(query);
		if((tags.size()!=0)&&tags!=null){
			return tags.get(0);
		}else {
			return null;
		}
	}

	public void updateTagInfo(String tagId,Tag tag) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(tagId));
		Update update = new Update();
		update.set("name",tag.get_name());
		update.set("name_eng", tag.get_name_eng());
		update.set("update_at",tag.get_update_at());
		this.update(query,update);
	}

	public void delTagInfo(String tagId) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(tagId));
		Update update = new Update();
		update.set("is_exist",false);
		this.update(query,update);
	}

	public List<Tag> findAllExistTags() {
		Query query = new Query();
		/*query.addCriteria(new Criteria("is_exist").is(true));*/
		return this.find(query);
	}

	@Override
	public boolean isExistTags(List<String> taglist) {
		if(taglist.size()!=0){
			for (String tag :taglist) {
				Query query = new Query();
				query.addCriteria(new Criteria().andOperator(Criteria.where("name_eng").is(tag),
						Criteria.where("is_exist").is(true)));
				if(this.find(query).size()==0){
					return false;
				}
			}
			return true;

		}else {
			return false;
		}
	}

	@Override
	public void updateTagofBlogId(String blogTag, String postId) {
		Tag tag = this.findTagByEngName(blogTag);
		if(tag.get_blog_id()!=null&&!tag.get_blog_id().equals("")){
			//获取标签下的博客ID
			List<String> tags = tag.get_blog_id();
			if(!tags.contains(postId)) {
				tags.add(postId);
				Query query = new Query();
				query.addCriteria(new Criteria("name_eng").is(blogTag));
				Update update = new Update();
				update.set("blog_id", tags);
				this.update(query, update);
			}
		}else {
			List<String> tags = new ArrayList<>();
			tags.add(postId);
			Query query = new Query();
			query.addCriteria(new Criteria("name_eng").is(blogTag));
			Update update = new Update();
			update.set("blog_id",tags);
			this.update(query,update);
		}
	}

	@Override
	public List<Tag> findTagByBlogId(String postId) {
		Query query = new Query();
		query.addCriteria(new Criteria("blog_id").is(postId));
		if(this.find(query).size()!=0){
			return this.find(query);
		}else {
			return null;
		}
	}

	@Override
	public void updateTagBlogIdList(String tagName, List<String> blogId) {

		Query query = new Query();
		query.addCriteria(new Criteria("name_eng").is(tagName));
		Update update = new Update();
		update.set("blog_id", blogId);
		this.update(query, update);
		}

}
