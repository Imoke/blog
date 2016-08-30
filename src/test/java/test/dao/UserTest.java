package test.dao;



import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.User;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LWang on 2016/4/20.
 */
public class UserTest {

	private UserDaoImpl userDao;

	@Before
	public void setUp() throws Exception{
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		userDao = context.getBean(UserDaoImpl.class);
	}

	@Test
	public void testInsert(){
		User user = new User();
		user.set_isThirdPart(true);
		List<Long> loginTimes = new ArrayList<>();
		loginTimes.add(System.currentTimeMillis());
		loginTimes.add(System.currentTimeMillis());
		user.set_loginTime(loginTimes);
		user.set_thirdPart("weibo");
		user.set_userIcon("null");
		user.set_userName("www");
		userDao.insert(user);
	}


}
