package com.lw.blog.service.user;


import com.lw.blog.model.user.User;
import org.springframework.stereotype.Service;

/**
 * Created by LWang on 2016/4/20.
 */

@Service
public interface UserService {

	public User findUserByName(String username);

}
