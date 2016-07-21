package com.lw.blog.dao.mongo.tag;

import com.lw.blog.model.Tag;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LWang on 2016/7/17.
 */

@Transactional
public interface TagDao {
	Tag getTagByClassName(String name);
}
