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
	@Field("from_user_name")
	private String _fromUserName;
	@Field("from_user_icon")
	private String _fromUserIcon;
	@Field("to_user_id")
	private String _toUserId;
	@Field("to_user_name")
	private String _toUserName;
	@Field("to_user_icon")
	private String _toUserIcon;
	@Field("content")
	private String _content;
	@Field("comment_time")
	private long commentTime;

	@Override
	public String toString() {
		return "ReplyComment{" +
				"_id='" + _id + '\'' +
				", _fromUserId='" + _fromUserId + '\'' +
				", _fromUserName='" + _fromUserName + '\'' +
				", _fromUserIcon='" + _fromUserIcon + '\'' +
				", _toUserId='" + _toUserId + '\'' +
				", _toUserName='" + _toUserName + '\'' +
				", _toUserIcon='" + _toUserIcon + '\'' +
				", _content='" + _content + '\'' +
				", commentTime=" + commentTime +
				'}';
	}


	public String get_fromUserIcon() {
		return _fromUserIcon;
	}

	public void set_fromUserIcon(String _fromUserIcon) {
		this._fromUserIcon = _fromUserIcon;
	}

	public String get_toUserIcon() {
		return _toUserIcon;
	}

	public void set_toUserIcon(String _toUserIcon) {
		this._toUserIcon = _toUserIcon;
	}

	public String get_fromUserName() {
		return _fromUserName;
	}

	public void set_fromUserName(String _fromUserName) {
		this._fromUserName = _fromUserName;
	}

	public String get_toUserName() {
		return _toUserName;
	}

	public void set_toUserName(String _toUserName) {
		this._toUserName = _toUserName;
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
