/**
 * 
 */
package com.lw.blog.dao.mongo.user;


import com.lw.blog.model.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


/**
 * @author LWang
 * @date   2016-1-15 
 */
@Transactional
public interface UserDao {

    void insertUserRegisterInfo(String username, String password);


    boolean isUserNameExist(String username);

    boolean isPasswordFit(String username, String password);

    User findUserByUserName(String userName);

    List<User> findUserByLocalUserName(String username);
}
