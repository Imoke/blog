package com.lw.blog.dao.mongo.tag;

import com.lw.blog.model.Tag;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */

@Transactional
public interface TagDao {
	Tag getTagByClassName(String name);
	
	Tag findTagByName(String tagName);
	
	Tag findTagByEngName(String tagEngName);
	
	void updateTagInfo(String tagId,Tag tag);
	
	void delTagInfo(String tagId);
	
	List<Tag> findAllExistTags();
	
	boolean isExistTags(List<String> taglist);
	
	void updateTagofBlogId(String blogTag, String postId);
	
	List<Tag> findTagByBlogId(String postId);
	
	void updateTagBlogIdList(String tagName, List<String> blogId);
}
