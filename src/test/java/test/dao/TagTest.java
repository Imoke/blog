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
		tag.set_name("分布式处理");
		tag.set_creat_at(System.currentTimeMillis());
		tag.set_update_at(System.currentTimeMillis());
		tagDao.insert(tag);
	}


}
