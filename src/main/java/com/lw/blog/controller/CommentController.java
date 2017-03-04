package com.lw.blog.controller;
import com.lw.blog.model.User;
import com.lw.blog.model.integratedModel.ShowComment;
import com.lw.blog.service.comment.CommentService;
import com.lw.blog.service.user.UserServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;


/**
 *
 * Created by LWang on 2016/8/20.
 */

@Controller
@RequestMapping("/comment")
@SessionAttributes("username")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserServiceImpl userService;
	private static final Logger logger = Logger.getLogger(CommentController.class);
	/**
	 * validate the blog comment
	 * @param blog_comment 博客的评论
	 * @return 信息
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
		return commentService.validateBlogComment(decodeBlogComment);
	}

	/**
	 *
	 * @param blogId 被评论博客的ID
	 * @param blog_comment 评论的内容
	 * @param cid 某条评论的ID
	 * @param tid 某条评论的用户的ID
	 * @return 所有的评论
	 */
	@RequestMapping("/add_comment")
	@ResponseBody
	public List<ShowComment> addBlogComment(@RequestParam(value="blogId") String blogId,
											@RequestParam(value="blog_comment") String blog_comment,
											@RequestParam(value="cid", required=false) String cid,
											@RequestParam(value="tid", required=false) String tid,
											HttpSession session){
		String decodeBlogComment="";
		try {
			decodeBlogComment=URLDecoder.decode(blog_comment, "UTF-8");
			//logger.error(decodeBlogComment);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		boolean isAddCommentSuccess;
		//String userId="57b95a1d99ff07057c91fe5c";
		//获取目前登陆的用户的信息
		String userName ="";
		String userId;
		Enumeration<String> e = session.getAttributeNames();
		while (e.hasMoreElements()) {
			String s = e.nextElement();
			if(s.equals("username")&&!session.getAttribute(s).equals("")&&session.getAttribute(s)!=null){
				userName = (String) session.getAttribute(s);
				logger.info(userName);
			}
		}
		//根据用户名找到用户的Id
		User user = userService.findUserByUserName(userName);
		if(user!=null){
			userId = user.get_id();
			//在评论数据库中添加数据
			logger.info(userId);
			isAddCommentSuccess = commentService.addBlogComment(blogId, decodeBlogComment, userId,cid , tid);
			if(isAddCommentSuccess){
				//评论数据的显示
				return commentService.getAllCommentByBlogId(blogId);
			}else {
				//评论没有添加成功，返回错误信息
				logger.error("error happend at adding comment ");
				return null;
			}
		}else {
			logger.error("用户名是空的");
			return null;
		}

	}

	/**
	 * show the blog comment
	 * @param blogId 博客的ID
	 * @return 以 博客Id为blogId的所有评论的信息
	 */
	@RequestMapping("/show_comment")
	@ResponseBody
	public List<ShowComment> showBlogComment(@RequestParam String blogId){
		return commentService.getAllCommentByBlogId(blogId);
	}
}
