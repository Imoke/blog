package test.dao;



import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.user.User;
import com.lw.blog.model.user.Username;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		user.setAge(13);
		Username username = new Username();
		username.setUsername("lww");
		username.setNickname("yby");
		user.setName(username);
		userDao.insert(user);
	}


}
