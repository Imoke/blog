package com.lw.blog.util.security;

import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.User;
import com.lw.blog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 实现spring security 和 mongoDb的认证
 * Created by LWang on 2017/3/13.
 */
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserDaoImpl userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(!userDao.isUserNameExist(username)){
			throw new UsernameNotFoundException(username+"dont't exist");
		}else {
			User user = userDao.findUserByUserName(username);
	  		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
					user.get_userName(),user.get_userPassword(),true,
					true,true,true,getAuthorities(user.get_authorities()));
			return userDetails;
		}

	}

	/** 根据用户级别，获得角色列表。比如用户级别为1，表示该用户是管理员，则返回ROLE_USER,ROLE_ADMIN
	 * @param access 用户级别
	 * @return 用户角色列表
	 */
	public Collection<GrantedAuthority> getAuthorities(int access){
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);
		authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(access==1){
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		return authList;
	}
}
