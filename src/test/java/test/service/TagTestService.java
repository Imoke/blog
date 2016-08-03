package test.service;

import com.lw.blog.model.Post;
import com.lw.blog.service.blog.BlogServiceImpl;
import com.lw.blog.service.tag.TagServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */
public class TagTestService {
	private TagServiceImpl tagService;

	@Before
	public void setUp() throws Exception{
		ConfigurableApplicationContext context = null;
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		tagService = context.getBean(TagServiceImpl.class);
	}

	@Test
	public void testisExistTags(){
		boolean isexist = tagService.isExistTags("lww#qw");
		System.out.println(isexist);
	}

}
