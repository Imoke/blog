package com.lw.blog.controller;
import com.lw.blog.model.integratedModel.ShowComment;
import com.lw.blog.service.comment.CommentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


/**
 * Created by LWang on 2016/8/20.
 */

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;

	private static final Logger logger = Logger.getLogger(CommentController.class);
	/**
	 * validate the blog comment
	 * @param blog_comment
	 * @return
	 */
	@RequestMapping("/validate_comment")
	@ResponseBody
	public String validateBlogComment(@RequestParam String blog_comment){
		String decodeBlogComment="";
		try {
			decodeBlogComment=URLDecoder.decode(blog_comment, "UTF-8");
			//logger.error(decodeBlogComment);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String validateInfo = commentService.validateBlogComment(decodeBlogComment);
		return validateInfo;
	}

	/**
	 *
	 * @param blogId 被评论博客的ID
	 * @param blog_comment 评论的内容
	 * @param cid 某条评论的ID
	 * @param tid 某条评论的用户的ID
	 * @return
	 */
	@RequestMapping("/add_comment")
	@ResponseBody
	public List<ShowComment> addBlogComment(@RequestParam(value="blogId", required=true) String blogId,
											@RequestParam(value="blog_comment", required=true) String blog_comment,
											@RequestParam(value="cid", required=false) String cid,
											@RequestParam(value="tid", required=false) String tid){
		String decodeBlogComment="";
		try {
			decodeBlogComment=URLDecoder.decode(blog_comment, "UTF-8");
			//logger.error(decodeBlogComment);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//在评论数据库中添加数据
		boolean isAddCommentSuccess;
		String userId="57b95a1d99ff07057c91fe5c";
		isAddCommentSuccess = commentService.addBlogComment(blogId, decodeBlogComment, userId,cid , tid);
		if(isAddCommentSuccess){
			//评论数据的显示
			List<ShowComment> showComments=commentService.getAllCommentByBlogId(blogId);
			return showComments;
		}else {
			//评论没有添加成功，返回错误信息
			logger.error("error happend at adding comment ");
			return null;
		}
	}

	/**
	 * show the blog comment
	 * @param blogId
	 * @return
	 */
	@RequestMapping("/show_comment")
	@ResponseBody
	public List<ShowComment> showBlogComment(@RequestParam String blogId){
		List<ShowComment> showComments=commentService.getAllCommentByBlogId(blogId);
		return showComments;
	}
}
