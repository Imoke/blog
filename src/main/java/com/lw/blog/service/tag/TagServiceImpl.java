package com.lw.blog.service.tag;

import com.lw.blog.dao.mongo.tag.TagDao;
import com.lw.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LWang on 2016/7/18.
 */
@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagDao tagDao;
	public Tag getTagByClassNume(String name) {
		return tagDao.getTagByClassNume(name);
	}
}
