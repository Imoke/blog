package test.dao;

import com.lw.blog.dao.mongo.post.PostDaoImpl;
import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.Post;
import com.lw.blog.model.Tag;
import com.lw.blog.model.user.User;
import com.lw.blog.model.user.Username;
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
		post.set_title("CAP 问题");
		post.set_content("分布式领域CAP理论，\n" +
				"Consistency(一致性), 数据一致更新，所有数据变动都是同步的\n" +
				"Availability(可用性), 好的响应性能\n" +
				"Partition tolerance(分区容错性) 可靠性");
		post.set_create_at(System.currentTimeMillis());
		post.set_update_at(System.currentTimeMillis());
		List<Tag> list = new ArrayList<Tag>();
		Tag tag = new Tag();
		tag.set_name("分布式处理");
		tag.set_id("578b2fcc218657a75bafd893");
		tag.set_name_eng("distributed-processing");
		list.add(tag);
		post.set_tags(list);
		postDao.insert(post);
	}
	@Test
	public void testgetAllPostsNumber(){
		System.out.println("post's total numbers : "+postDao.getAllPostsNumber());
	}

}
