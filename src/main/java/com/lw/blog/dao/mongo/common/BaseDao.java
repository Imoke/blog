/**
 * 
 */
package com.lw.blog.dao.mongo.common;


import com.lw.blog.dao.mongo.common.util.MongoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


/**
 * @author LWang
 * @date   2016-1-18 
 */
public class BaseDao<T> implements IBaseDao<T> {
	private Class<T> clazz;
	
	public BaseDao(){
		ParameterizedType type=(ParameterizedType)getClass().getGenericSuperclass();
		clazz=(Class<T>)type.getActualTypeArguments()[0];
	}
	
	@Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoFactory mongoFactory;
    
	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#findCount(org.springframework.data.mongodb.core.query.Query)
	 */
	public Long findCount(Query query) {
		return mongoTemplate.count(query, clazz);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#find(org.springframework.data.mongodb.core.query.Query)
	 */
	
	public List<T> find(Query query) {
		return mongoTemplate.find(query, clazz);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#findList(int, int)
	 */
	
	public List<T> findList(int start, int limit) {
		Query query = new Query();
        query.with(new Sort(new Order(Direction.ASC, "_id")));
        query.skip(start).limit(limit);
        return find(query);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#findOne(java.lang.String)
	 */
	
	public T findOne(String id) {
		Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        return mongoTemplate.findOne(query, clazz);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#insert(java.lang.Object)
	 */
	
	public void insert(T entity) {
		mongoTemplate.insert(entity);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#update(java.lang.Object)
	 */
	
	public void update(T entity) throws Exception {
		Map<String, Object> map = mongoFactory.converObjectToParams(entity);

        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(map.get("id")));
        Update update = (Update) map.get("update");
        this.update(query, update);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#update(org.springframework.data.mongodb.core.query.Query, org.springframework.data.mongodb.core.query.Update)
	 */
	
	public void update(Query query, Update update) {
		mongoTemplate.updateFirst(query, update, clazz);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.common.IBaseDao#remove(java.lang.Object)
	 */
	
	public void remove(T entity) {
		mongoTemplate.remove(entity);

	}
	public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

}
