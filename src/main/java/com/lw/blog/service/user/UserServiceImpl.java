package com.lw.blog.service.user;


import com.lw.blog.dao.mongo.user.UserDao;
import com.lw.blog.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LWang on 2016/4/20.
 */

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	public User findUserByName(String username) {
		return userDao.findUserByName(username);
	}
}
