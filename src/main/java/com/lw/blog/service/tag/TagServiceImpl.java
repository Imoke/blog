package com.lw.blog.service.tag;

import com.lw.blog.dao.mongo.tag.TagDao;
import com.lw.blog.dao.mongo.tag.TagDaoImpl;
import com.lw.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LWang on 2016/7/18.
 */
@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagDaoImpl tagDao;
	public Tag getTagByClassName(String name) {
		return tagDao.getTagByClassName(name);
	}

	public List<Tag> getAllTags() {
		return tagDao.findAllExistTags();
	}

	public boolean isExistTagName(String tagName) {
		Tag tag = tagDao.findTagByName(tagName);
		if(tag!=null&&!tag.equals("")){
			return true;
		}else {
			return false;
		}
	}

	public boolean isExistTagEngName(String tagEngName) {
		Tag tag = tagDao.findTagByEngName(tagEngName);
		if(tag!=null&&!tag.equals("")){
			return true;
		}else {
			return false;
		}
	}

	public boolean isInsertTagInfo(String tagName, String tagEngName) {
		Tag tag = new Tag();
		tag.set_name(tagName);
		tag.set_name_eng(tagEngName);
		tag.set_creat_at(System.currentTimeMillis());
		tag.set_update_at(System.currentTimeMillis());
		tag.set_is_exist(true);
		try {
			tagDao.insert(tag);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}

	public boolean isEditTagInfo(String tagId,String tagName, String tagEngName) {
		Tag tag = new Tag();
		tag.set_name(tagName);
		tag.set_name_eng(tagEngName);
		tag.set_update_at(System.currentTimeMillis());
		try {
			tagDao.updateTagInfo(tagId,tag);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean isDelTagInfo(String tagId) {
		try {
			tagDao.delTagInfo(tagId);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
