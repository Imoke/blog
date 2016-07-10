package test.service;

import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.user.User;
import com.lw.blog.model.user.Username;
import com.lw.blog.service.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by LWang on 2016/7/10.
 */
public class UserTestService {

	private UserDaoImpl userDao;
	private UserServiceImpl userService;

	@Before
	public void setUp() throws Exception{
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userDao = context.getBean(UserDaoImpl.class);
		userService = context.getBean(UserServiceImpl.class);
	}

	@Test
	public void testFindUserByName(){
		User user = userService.findUserByName("lww");
		System.out.println(user.getName()+"  "+user.getAge());
	}


}
