/**
 * 
 */
package com.lw.blog.dao.mongo.user;


import com.lw.blog.model.user.User;
import com.mongodb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * @author LWang
 * @date   2016-1-15 
 */
@Repository
public class UserDaoImpl implements UserDao {
	public static final Logger logger= LoggerFactory.getLogger(UserDaoImpl.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#_test()
	 */
	
	public void _test() {
		Set<String> colles=this.mongoTemplate.getCollectionNames();
		for(String colle : colles){
			logger.info("CollectionName="+colle);
		}
		DB db=this.mongoTemplate.getDb();
		logger.info("db="+db.toString());
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#createColletion()
	 */
	
	public void createColletion() {
		if (!this.mongoTemplate.collectionExists(User.class)) {
			this.mongoTemplate.createCollection(User.class);
		}
		else {
			logger.info("集合已经存在");
		}
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#insert(com.lw.blog.model.User)
	 */
	
	public void insert(User user) {
		this.mongoTemplate.insert(user);

	}

	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#update(com.lw.blog.model.User)
	 */
	
	public void update(User user) {
		Query query=new Query();
		query.addCriteria(new Criteria("_id").is(user.getId()));
		Update update=new Update();
		update.set("age", user.getAge());
		update.set("brithday", user.getBirthday());
		//update.set("name", user.getName());
		//update.set("special", user.getSprcial());
		this.mongoTemplate.updateFirst(query, update, User.class);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#findUserList(int, int)
	 */
	
	public List<User> findUserList(int skip, int limit) {
		Query query=new Query();
		query.with(new Sort(new Order(Direction.ASC, "_id")));
		query.skip(skip).limit(limit);
		return this.mongoTemplate.find(query, User.class);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#findOneUser(java.lang.String)
	 */
	
	public User findOneUser(String id) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(id));
		return this.mongoTemplate.findOne(query, User.class);
	}

	/* (non-Javadoc)
	 * @see com.lw.blog.dao.user.UserDao#findUserByName(java.lang.String)
	 */
	
	public User findUserByName(String username) {
		Query query = new Query();
		query.addCriteria(new Criteria("name.username").is(username));
		return this.mongoTemplate.findOne(query, User.class);
	}


}
