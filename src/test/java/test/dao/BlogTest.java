package test.dao;

		import com.lw.blog.dao.mongo.post.PostDaoImpl;
		import com.lw.blog.model.Post;
		import com.lw.blog.model.Tag;
		import org.junit.Before;
		import org.junit.Test;
		import org.springframework.context.ConfigurableApplicationContext;
		import org.springframework.context.support.ClassPathXmlApplicationContext;

		import java.util.ArrayList;
		import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
public class BlogTest {
	private PostDaoImpl postDao;

	@Before
	public void setUp() throws Exception{
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		postDao = context.getBean(PostDaoImpl.class);
	}

	@Test
	public void testInsert(){
		Post post = new Post();
		post.set_title("Spring Framework Reference");
		post.set_describe("Introduction to the Spring Framework");
		post.set_content("The Spring Framework is a Java platform that provides comprehensive infrastructure support for developing Java applications. Spring handles the infrastructure so you can focus on your application.\n" +
				"Spring enables you to build applications from \"plain old Java objects\" (POJOs) and to apply enterprise services non-invasively to POJOs. This capability applies to the Java SE programming model and to full and partial Java EE.\n" +
				"Examples of how you, as an application developer, can benefit from the Spring platform:\n" +
				"\n" +
				"\t* Make a Java method execute in a database transaction without having to deal with transaction APIs.\n" +
				"\t* Make a local Java method a remote procedure without having to deal with remote APIs.\n" +
				"\t* Make a local Java method a management operation without having to deal with JMX APIs.\n" +
				"\t* Make a local Java method a message handler without having to deal with JMS APIs.\n" +
				"\n");
		post.set_create_at(System.currentTimeMillis());
		post.set_update_at(System.currentTimeMillis());
		List<Tag> list = new ArrayList<Tag>();
		Tag tag1 = new Tag();
		tag1.set_name("Spring");
		//tag.set_id("578b2fcc218657a75bafd893");
		tag1.set_name_eng("Spring");
		list.add(tag1);
		post.set_tags(list);
		postDao.insert(post);
	}
	@Test
	public void testgetAllPostsNumber(){
		System.out.println("post's total numbers : "+postDao.getAllPostsNumber());
	}
	@Test
	public void testfindAllPosts(){
		System.out.println(postDao.findAllPosts());
	}

}
