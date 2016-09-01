/**
 * 
 */
package com.lw.blog.dao.mongo.user;


import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.model.User;
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
public class UserDaoImpl extends BaseDao<User>implements UserDao {
	//public static final Logger logger= LoggerFactory.getLogger(UserDaoImpl.class);


	@Override
	public void insertUserRegisterInfo(String username, String password) {
		User user = new User();
		user.set_isThirdPart(false);
		user.set_userName(username);
		user.set_userPassword(password);
		user.set_registerTime(System.currentTimeMillis());
		this.insert(user);

	}

	@Override
	public boolean isUserNameExist(String username) {
		Query query = new Query();
		query.addCriteria(new Criteria("user_name").is(username));
		List<User> users = this.find(query);
		return users.size() != 0;

	}

	@Override
	public boolean isPasswordFit(String username, String password) {
		Query query = new Query();
		query.addCriteria(new Criteria("user_name").is(username));
		String pass = this.find(query).get(0).get_userPassword();
		return pass.equals(password);
	}

	@Override
	public User findUserByUserName(String userName) {
		Query query = new Query();
		query.addCriteria(new Criteria("user_name").is(userName));
		return this.find(query).get(0);
	}


}
