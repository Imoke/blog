/**
 * 
 */
package com.lw.blog.dao.mongo.user;


import com.lw.blog.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author LWang
 * @date   2016-1-15 
 */
@Transactional
public interface UserDao {
	/**
	 * 测试
	 * @author LWang
	 * @date   2016-1-15 下午2:14:48
	 */
	void _test();
	/**
	 * 创建一个集合
	 * @author LWang
	 * @date   2016-1-15 下午2:09:05
	 */
	void createColletion();
	/**
	 * 插入用户
	 * @author LWang
	 * @date   2016-1-15 下午2:09:32
	 * @param user
	 */
	void insert(User user);
	/**
	 * 修改用户
	 * @author LWang
	 * @date   2016-1-15 下午2:10:01
	 * @param user
	 */
	void update(User user);
	/**
	 * 查找所有用户
	 * @author LWang
	 * @date   2016-1-15 下午2:10:14
	 * @param skip 
	 * @param limit
	 * @return
	 */
	List<User> findUserList(int skip, int limit);
	/**
	 * 通过ID查找用户
	 * @author LWang
	 * @date   2016-1-15 下午2:10:43
	 * @param id
	 * @return
	 */
	User findOneUser(String id);
	/**
	 * 通过用户名查找用户
	 * @author LWang
	 * @date   2016-1-15 下午2:11:13
	 * @param username  
	 * @return
	 */
	User findUserByName(String username);
	
}
