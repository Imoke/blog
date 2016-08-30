package com.lw.blog.model;

import com.lw.blog.model.integratedModel.ReplyComment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;


/**
 * Created by LWang on 2016/7/17.
 */

@Document(collection = "comments")
public class Comment {
	@Id
	private String _id;
	@Field("post_id")
	private String _postId;
	@Field("from_user_id")
	private String _fromUserId;
	@Field("to_user_id")
	private String _toUserId;
	@Field("content")
	private String _content;
	@Field("comment_time")
	private long _commentTime;
	@Field("replys")
	private List<ReplyComment> _replys;

	@Override
	public String toString() {
		return "Comment{" +
				"_commentTime=" + _commentTime +
				", _id='" + _id + '\'' +
				", _postId='" + _postId + '\'' +
				", _fromUserId='" + _fromUserId + '\'' +
				", _toUserId='" + _toUserId + '\'' +
				", _content='" + _content + '\'' +
				", _replys=" + _replys +
				'}';
	}

	public long get_commentTime() {
		return _commentTime;
	}

	public void set_commentTime(long _commentTime) {
		this._commentTime = _commentTime;
	}

	public List<ReplyComment> get_replys() {
		return _replys;
	}

	public void set_replys(List<ReplyComment> _replys) {
		this._replys = _replys;
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

	public String get_postId() {
		return _postId;
	}

	public void set_postId(String _postId) {
		this._postId = _postId;
	}

	public String get_toUserId() {
		return _toUserId;
	}

	public void set_toUserId(String _toUserId) {
		this._toUserId = _toUserId;
	}
}
