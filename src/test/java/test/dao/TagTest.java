package test.dao;

import com.lw.blog.dao.mongo.tag.TagDaoImpl;
import com.lw.blog.dao.mongo.user.UserDaoImpl;
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
public class TagTest {

	private TagDaoImpl tagDao;

	@Before
	public void setUp() throws Exception{
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		tagDao = context.getBean(TagDaoImpl.class);
	}

	@Test
	public void testInsert(){
		Tag tag = new Tag();
		tag.set_name("数据库");
		tag.set_name_eng("database");
		tag.set_creat_at(System.currentTimeMillis());
		tag.set_update_at(System.currentTimeMillis());
		List<String> list = new ArrayList<String>();
		list.add("578e16b371c1d56714a40d21");
		list.add("578e165d71c17ec2925e4488");
		list.add("578e162771c1e00e5d78d43b");
		list.add("578e15cf71c1e82ffe184dcd");
		tag.set_blog_id(list);
		tagDao.insert(tag);
	}

}
