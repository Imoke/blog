package com.lw.blog.dao.mongo.tag;

import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.model.Tag;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by LWang on 2016/7/17.
 */

@Repository
public class TagDaoImpl extends BaseDao<Tag> implements TagDao {

	public Tag getTagByClassNume(String name) {
		Query query = new Query();
		query.addCriteria(new Criteria("name_eng").is(name));
		return this.find(query).get(0);
	}
}
