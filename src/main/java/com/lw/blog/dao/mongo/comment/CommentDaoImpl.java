package com.lw.blog.dao.mongo.comment;

import com.lw.blog.dao.mongo.common.BaseDao;
import com.lw.blog.model.Comment;
import com.lw.blog.model.integratedModel.ReplyComment;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;


@Repository
public class CommentDaoImpl extends BaseDao<Comment> implements CommentDao {



	@Override
	public List<Comment> findCommentByBlogId(String blogId) {
		if(blogId!=null&&!blogId.equals("")){
			Query query = new Query();
			query.addCriteria(new Criteria("post_id").is(blogId));
			return this.find(query);
		}else {
			return null;
		}
	}

	@Override
	public void updateCommentInfo(String blogId, String blog_comment, String userId, String cid, String tid ,
								  String fromUserName,String toUserName,String fromUserIcon,String toUserIcon) {
		Query query = new Query();
		query.addCriteria(new Criteria("_id").is(cid));
		List<ReplyComment> replyComments= this.find(query).get(0).get_replys();

		ReplyComment replyComment = new ReplyComment();
		replyComment.setCommentTime(System.currentTimeMillis());
		replyComment.set_content(blog_comment);
		replyComment.set_fromUserId(userId);
		replyComment.set_fromUserName(fromUserName);
		replyComment.set_toUserId(tid);
		replyComment.set_toUserName(toUserName);
		replyComment.set_fromUserIcon(fromUserIcon);
		replyComment.set_toUserIcon(toUserIcon);
		replyComment.set_id((System.currentTimeMillis()+"").trim());
		if(replyComments==null){
			List<ReplyComment> replyCommentList = new ArrayList<>();
			replyCommentList.add(replyComment);
			Update update = new Update();
			update.set("replys", replyCommentList);
			this.update(query,update);
		}else {
			replyComments.add(replyComment);
			Update update = new Update();
			update.set("replys", replyComments);
			this.update(query, update);
		}


	}
}
