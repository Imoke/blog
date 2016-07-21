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
		return tagDao.find(new Query());
	}
}
