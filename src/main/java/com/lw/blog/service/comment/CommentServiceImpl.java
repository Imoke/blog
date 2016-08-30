package com.lw.blog.service.comment;


import com.lw.blog.dao.mongo.comment.CommentDao;
import com.lw.blog.dao.mongo.comment.CommentDaoImpl;
import com.lw.blog.dao.mongo.comment.CommentValidateDao;
import com.lw.blog.dao.mongo.comment.CommentValidateDaoImpl;
import com.lw.blog.dao.mongo.user.UserDaoImpl;
import com.lw.blog.model.Comment;
import com.lw.blog.model.User;
import com.lw.blog.model.integratedModel.ReplyComment;
import com.lw.blog.model.integratedModel.ShowComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by LWang on 2016/7/18.
 */
@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDaoImpl commentDao;
	@Autowired
	private CommentValidateDaoImpl commentValidateDao;
	@Autowired
	private UserDaoImpl userdao;
	@Override
	public boolean addBlogComment(String blogId, String blog_comment, String userId,String cid,String tid) {
		if(blogId!=null&&!blogId.equals("")){
			if(cid==null){
			Comment comment = new Comment();
			comment.set_content(blog_comment);
			comment.set_fromUserId(userId);
			comment.set_postId(blogId);
				comment.set_replys(null);
			comment.set_commentTime(System.currentTimeMillis());
			commentDao.insert(comment);
			return true;
			}else {
				commentDao.updateCommentInfo(blogId,blog_comment,userId,cid,tid);
				return true;
			}
		}else {
			return false;
		}
	}

	@Override
	public String validateBlogComment(String blog_comment) {
		if(blog_comment.equals("")||blog_comment==null){
			return "the content is not empty";
		}else {
			//评论是否出现不容许出现的词汇，如果有返回信息，没有返回null
			if(commentValidateDao.isHavaIllegalInfo(blog_comment)){
				return "the content is not illegal";
			}else {
				return "OK";
			}
		}
	}

	@Override
	public List<ShowComment> getAllCommentByBlogId(String blogId) {
		//首先在Comment表中获得评论的信息
		List<Comment> comments = commentDao.findCommentByBlogId(blogId);
		if(comments!=null&&comments.size()!=0){
			List<ShowComment> showCommentList = new ArrayList<>();
			for (Comment comment :comments) {
				String blogCommentId = comment.get_id();
				String blogComment = comment.get_content();
				String fromUserId = comment.get_fromUserId();
				List<ReplyComment> replyComments = comment.get_replys();
				long commentTime = comment.get_commentTime();

				User fromUser = userdao.findOne(fromUserId);
				//User toUser = userdao.findOne(toUserId);
				ShowComment showComment = new ShowComment();
				showComment.set_commentId(blogCommentId);
				showComment.set_content(blogComment);
				showComment.set_fromUserIcon(fromUser.get_userIcon());
				showComment.set_fromUserName(fromUser.get_userName());
				showComment.set_commentTime(commentTime);
				showComment.set_replyComment(replyComments);
				showCommentList.add(showComment);
			}
			return showCommentList;
		}else {
			return null;
		}

	}
}
