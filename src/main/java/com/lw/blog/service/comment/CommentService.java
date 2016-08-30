package com.lw.blog.service.comment;


import com.lw.blog.model.integratedModel.ShowComment;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by LWang on 2016/7/18.
 */

@Service
public interface CommentService {
	
	
	boolean addBlogComment(String blogId, String blog_comment, String userId,String cid ,String tid);
	
	String validateBlogComment(String blog_comment);
	
	
	List<ShowComment> getAllCommentByBlogId(String blogId);
}
