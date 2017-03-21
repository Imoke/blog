package com.lw.blog.service.tag;

import com.lw.blog.dao.mongo.post.PostDaoImpl;
import com.lw.blog.dao.mongo.tag.TagDao;
import com.lw.blog.dao.mongo.tag.TagDaoImpl;
import com.lw.blog.model.Post;
import com.lw.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * Created by LWang on 2016/7/18.
 */
@Service
public class TagServiceImpl implements TagService {
	@Autowired
	private TagDaoImpl tagDao;
	@Autowired
	private PostDaoImpl postDao;
	public Tag getTagByClassName(String name) {
		return tagDao.getTagByClassName(name);
	}

	public List<Tag> getAllTags() {
		return tagDao.findAllExistTags();
	}

	public boolean isExistTagName(String tagName) {
		Tag tag = tagDao.findTagByName(tagName);
		if(tag!=null&&!tag.equals("")){
			return true;
		}else {
			return false;
		}
	}

	public boolean isExistTagEngName(String tagEngName) {
		Tag tag = tagDao.findTagByEngName(tagEngName);
		if(tag!=null&&!tag.equals("")){
			return true;
		}else {
			return false;
		}
	}

	public boolean isInsertTagInfo(String tagName, String tagEngName) {
		Tag tag = new Tag();
		tag.set_name(tagName);
		tag.set_name_eng(tagEngName);
		tag.set_creat_at(System.currentTimeMillis());
		tag.set_update_at(System.currentTimeMillis());
		tag.set_is_exist(true);
		try {
			tagDao.insert(tag);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}

	public boolean isEditTagInfo(String tagId,String tagName, String tagEngName) {
		Tag tag = new Tag();
		tag.set_name(tagName);
		tag.set_name_eng(tagEngName);
		tag.set_update_at(System.currentTimeMillis());
		try {
			tagDao.updateTagInfo(tagId,tag);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean isDelTagInfo(String tagId) {
		try {
			tagDao.delTagInfo(tagId);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isExistTags(String tagsName) {
		String[] tags=null;
		List<String> taglist = new ArrayList<>();
		if(!tagsName.contains("#")){
			 taglist.add(tagsName);
		}else {
			tags = tagsName.split("#");
			for (String tag :tags) {
				taglist.add(tag);
			}
		}
		if(tagDao.isExistTags(taglist)){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void updateTagofBlogId(String blogName, String blogTag) {
		String postId = null;
		//get blog'id by blogname
		Post post = postDao.findPostByName(blogName);
		postId = post.get_id();

		//获取该博客ID在标签表中的所有标签
		List<Tag> tagList = tagDao.findTagByBlogId(postId);
		if (tagList != null && tagList.size() != 0) {
			for (Tag tag0 : tagList) {
				String tagName = tag0.get_name_eng();
				if (!blogTag.contains("#")) {
					if (!tagName.equals(blogTag)) {
						//删除tagName标签下的这一条博客ID
						//采用更新的方式更新标签下的博客id
						List<String> blogId = tagDao.findTagByName(tagName).get_blog_id();
						if (blogId != null) {
							blogId.remove(postId);
							tagDao.updateTagBlogIdList(tagName, blogId);
						}
					}
					tagDao.updateTagofBlogId(blogTag, postId);
				} else {
					String tag[] = blogTag.split("#");
					List<String> taglist = Arrays.asList(tag);
					if (!taglist.contains(tagName)) {
						List<String> blogId = tagDao.findTagByEngName(tagName).get_blog_id();
						if (blogId != null) {
							blogId.remove(postId);
							tagDao.updateTagBlogIdList(tagName, blogId);
						}
					}
					for (String s : tag) {
						tagDao.updateTagofBlogId(s, postId);
					}
				}
			}

			//add id to all tags

		}else {
			if (!blogTag.contains("#")) {
				tagDao.updateTagofBlogId(blogTag, postId);
			}else {
				String tag[] = blogTag.split("#");
				for (String s : tag) {
					tagDao.updateTagofBlogId(s, postId);
				}
			}
		}
	}
}
