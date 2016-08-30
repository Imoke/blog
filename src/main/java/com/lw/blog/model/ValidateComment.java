package com.lw.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by LWang on 2016/8/20.
 */
@Document(collection = "validate_comment")
public class ValidateComment {
	@Id
	private String _id;
	@Field("wrong_comment")
	private String _wrongComment;

	@Override
	public String toString() {
		return "ValidateComment{" +
				"_id='" + _id + '\'' +
				", _wrongComment='" + _wrongComment + '\'' +
				'}';
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_wrongComment() {
		return _wrongComment;
	}

	public void set_wrongComment(String _wrongComment) {
		this._wrongComment = _wrongComment;
	}
}
