package com.lw.blog.model.integratedModel;

import java.util.List;

/**
 * Created by LWang on 2016/8/21.
 */
public class ShowComment {

	private String _content;
	private String _commentId;
	private String _fromUserId;
	private String _fromUserName;
	private String _fromUserIcon;
	private List<ReplyComment> _replyComment;
	private long _commentTime;

	@Override
	public String toString() {
		return "ShowComment{" +
				"_commentId='" + _commentId + '\'' +
				", _content='" + _content + '\'' +
				", _fromUserId='" + _fromUserId + '\'' +
				", _fromUserName='" + _fromUserName + '\'' +
				", _fromUserIcon='" + _fromUserIcon + '\'' +
				", _replyComment=" + _replyComment +
				", _commentTime=" + _commentTime +
				'}';
	}

	public String get_commentId() {
		return _commentId;
	}

	public void set_commentId(String _commentId) {
		this._commentId = _commentId;
	}

	public long get_commentTime() {
		return _commentTime;
	}

	public void set_commentTime(long _commentTime) {
		this._commentTime = _commentTime;
	}

	public String get_content() {
		return _content;
	}

	public void set_content(String _content) {
		this._content = _content;
	}

	public String get_fromUserIcon() {
		return _fromUserIcon;
	}

	public void set_fromUserIcon(String _fromUserIcon) {
		this._fromUserIcon = _fromUserIcon;
	}

	public String get_fromUserId() {
		return _fromUserId;
	}

	public void set_fromUserId(String _fromUserId) {
		this._fromUserId = _fromUserId;
	}

	public String get_fromUserName() {
		return _fromUserName;
	}

	public void set_fromUserName(String _fromUserName) {
		this._fromUserName = _fromUserName;
	}

	public List<ReplyComment> get_replyComment() {
		return _replyComment;
	}

	public void set_replyComment(List<ReplyComment> _replyComment) {
		this._replyComment = _replyComment;
	}
}