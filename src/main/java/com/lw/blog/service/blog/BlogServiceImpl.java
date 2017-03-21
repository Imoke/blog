package com.lw.blog.service.blog;

import com.lw.blog.dao.mongo.common.util.Pagination;
import com.lw.blog.dao.mongo.post.PostDaoImpl;
import com.lw.blog.dao.mongo.tag.TagDaoImpl;
import com.lw.blog.model.Post;
import com.lw.blog.model.Tag;
import javafx.geometry.Pos;
import org.pegdown.Extensions;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.RootNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by LWang on 2016/7/17.
 */
@Service
public class BlogServiceImpl implements BlogService{
	@Autowired
	private PostDaoImpl postDao;
	@Autowired
	private TagDaoImpl tagDao;

	private final PegDownProcessor pegdown=new PegDownProcessor(Extensions.ALL);;
	public List<Post> getAllPostsByPage(int page) {
		long totalNumber = postDao.getAllPostsNumber();
		Pagination pagination = new Pagination(page,totalNumber);
		return postDao.getAllPostsByPage(pagination);

	}

	public Post getBlogById(String id) {
		return postDao.findOne(id);
	}

	public List<Post> getAllPosts() {
		return postDao.findAllPosts();
	}

	public List<Post> getBlogByYear(int year) {
		List<Post> postList = postDao.findPostByYear(year);
		return postList;
	}


	public String renderToHtml(String markdownSource) {
		// synchronizing on pegdown instance since neither the processor nor the underlying parser is thread-safe.
		synchronized (pegdown) {
			RootNode astRoot = pegdown.parseMarkdown(markdownSource.toCharArray());
			ToHtmlSerializer serializer = new ToHtmlSerializer(new LinkRenderer());
			//Collections.singletonMap(VerbatimSerializer.DEFAULT, PygmentsVerbatimSerializer.INSTANCE)
			return serializer.toHtml(astRoot);
		}
	}

	public void insertBlog2database(String blogName, String blogTag, String html, String blogDes,String imgRealPath,String markdown) {
		Post post = new Post();
		post.set_title(blogName);
		post.set_describe(blogDes);
		post.set_content_html(html);
		post.set_content_markdown(markdown);
		post.set_create_at(System.currentTimeMillis());
		post.set_update_at(System.currentTimeMillis());
		post.set_is_exist(true);
		post.set_fig(imgRealPath);
		String [] tags;
		List<Tag> list = new ArrayList<>();
		if(blogTag!=null) {
			if(!blogTag.contains("#")){
				Tag tag = new Tag();
				tag = tagDao.findTagByEngName(blogTag);
				list.add(tag);
			}else {
				tags = blogTag.split("#");
				for (String s : tags) {
					Tag tag = new Tag();
					tag = tagDao.findTagByEngName(s);
					list.add(tag);
				}
			}
		}
		post.set_tags(list);
		postDao.insert(post);
	}

	public void updateBlog(String blogID, String blogName, String blogTag, String html,String markdown,String blogDes, String imgRealPath) {

		postDao.updateBlog(blogID,blogName,blogTag,html,markdown,blogDes,imgRealPath);

	}

	public boolean deleteBlog(String blogId) {
		boolean isDel = postDao.deleteBlog(blogId);
		List<Tag> tagList = tagDao.findTagByBlogId(blogId);
		for (Tag tag :tagList) {
			List<String> blogIdList = tag.get_blog_id();
			blogIdList.remove(blogId);
			String tagName = tag.get_name_eng();
			tagDao.updateTagBlogIdList(tagName,blogIdList);
		}
		return isDel;
	}



}
