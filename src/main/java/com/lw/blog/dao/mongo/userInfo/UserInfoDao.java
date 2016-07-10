package com.lw.blog.dao.mongo.userInfo;


import com.lw.blog.model.UserInfo;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LWang on 2016/4/24.
 */

@Transactional
public interface UserInfoDao {
		UserInfo findUserInfoByUserName(String userName);
	
	void insertUserInfo(UserInfo userInfo);
}
