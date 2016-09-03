package com.lw.blog.service.user;


import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by LWang on 2016/4/20.
 */

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDaoImpl userDao;

	@Override
	public void userRegister(String username, String password) {
		userDao.insertUserRegisterInfo(username,password);
	}

	@Override
	public String userLogin(String username, String password) {
		String result;
		//先看用户名是否存在
		if(userDao.isUserNameExist(username)){
			//密码能否一致
			if(userDao.isPasswordFit(username,password)){
				result = "OK";
			}else {
			result = "NO_PASS";
			}
		}else {
			result = "NO_USER";
		}
		return result;
	}

	@Override
	public User findUserByUserName(String userName) {
		if(!userName.equals("")){
			return userDao.findUserByUserName(userName);
		}else {
			return null;
		}
	}

	@Override
	public boolean insertThirdPartUserInfo(String username, String imageUrl, String thirdPart) {
		User user = new User();
		user.set_userName(username);
		user.set_isThirdPart(true);
		user.set_thirdPart(thirdPart);
		user.set_userIcon(imageUrl);
		user.set_registerTime(System.currentTimeMillis());
		userDao.insert(user);
		return true;
	}

	@Override
	public boolean isExistLocalUserName(String username) {
		List<User> users = userDao.findUserByLocalUserName(username);
		return users.size() != 0;
	}


}
