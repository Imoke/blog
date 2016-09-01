package com.lw.blog.dao.mongo.comment;


import com.lw.blog.model.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by LWang on 2016/7/17.
 */

@Transactional
public interface CommentDao {

	List<Comment> findCommentByBlogId(String blogId);
	
	void updateCommentInfo(String blogId, String blog_comment, String userId, String cid, String tid,String fromUserName,String toUserName);
}
