package com.lw.blog.dao.mongo.userInfo;


import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.model.UserInfo;
import org.springframework.stereotype.Repository;

/**
 * Created by LWang on 2016/4/24.
 */

@Repository
public class UserInfoDaoImpl extends BaseDao<UserInfo> implements UserInfoDao{
	public UserInfo findUserInfoByUserName(String userName) {

		return this.findOne(userName);
	}

	public void insertUserInfo(UserInfo userInfo) {
		this.insert(userInfo);
	}

}
