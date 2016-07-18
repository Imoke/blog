package test.service;

import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.Post;
import com.lw.blog.model.user.User;
import com.lw.blog.service.blog.BlogServiceImpl;
import com.lw.blog.service.user.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
public class BlogTestService {
	private BlogServiceImpl blogService;

	@Before
	public void setUp() throws Exception{
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		blogService = context.getBean(BlogServiceImpl.class);
	}

	@Test
	public void testgetAllPostsByPage(){
		List<Post> posts = blogService.getAllPostsByPage(1);
		System.out.println(posts);
	}

}
