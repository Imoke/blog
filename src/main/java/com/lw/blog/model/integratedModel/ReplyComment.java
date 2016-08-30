package com.lw.blog.model.integratedModel;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by LWang on 2016/8/27.
 */
@Document(collection = "reply_comments")
public class ReplyComment {
	@Id
	private String _id;
	@Field("from_user_id")
	private String _fromUserId;
	@Field("to_user_id")
	private String _toUserId;
	@Field("content")
	private String _content;
	@Field("comment_time")
	private long commentTime;

	@Override
	public String toString() {
		return "ReplyComment{" +
				"_content='" + _content + '\'' +
				", _id='" + _id + '\'' +
				", _fromUserId='" + _fromUserId + '\'' +
				", _toUserId='" + _toUserId + '\'' +
				", commentTime=" + commentTime +
				'}';
	}

	public String get_content() {
		return _content;
	}

	public void set_content(String _content) {
		this._content = _content;
	}

	public String get_fromUserId() {
		return _fromUserId;
	}

	public void set_fromUserId(String _fromUserId) {
		this._fromUserId = _fromUserId;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_toUserId() {
		return _toUserId;
	}

	public void set_toUserId(String _toUserId) {
		this._toUserId = _toUserId;
	}

	public long getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(long commentTime) {
		this.commentTime = commentTime;
	}
}
