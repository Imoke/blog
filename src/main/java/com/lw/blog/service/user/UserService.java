package com.lw.blog.service.user;


import com.lw.blog.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by LWang on 2016/4/20.
 */

@Service
public interface UserService {

    void userRegister(String username, String password);

    String userLogin(String username, String password);

    User findUserByUserName(String userName);
}
