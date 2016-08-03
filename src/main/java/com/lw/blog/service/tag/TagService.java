package com.lw.blog.service.tag;

import com.lw.blog.model.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LWang on 2016/7/18.
 */

@Service
public interface TagService {
	
	Tag getTagByClassName(String name);

	List<Tag> getAllTags();
	
	boolean isExistTagName(String tagName);
	
	boolean isExistTagEngName(String tagEngName);
	
	boolean isInsertTagInfo(String tagName, String tagEngName);
	
	boolean isEditTagInfo(String tagId,String tagName, String tagEngName);
	
	boolean isDelTagInfo(String tagId);
	
	boolean isExistTags(String tagsName);
	
	void updateTagofBlogId(String blogName, String blogTag);
}
