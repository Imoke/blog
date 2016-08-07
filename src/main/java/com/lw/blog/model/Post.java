package com.lw.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */

@Document(collection = "posts")
public class Post {
	@Id
	private String _id;
	@Field("title")
	private String _title;
	@Field("describe")
	private String _describe;
	@Field("content")
	private String _content;
	@Field("create_at")
	private long _create_at;
	@Field("update_at")
	private long _update_at;
	@Field("tags")
	private List<Tag> _tags;
	@Field("is_exist")
	private boolean _is_exist;
	@Field("fig")
	private String _fig;

	@Override
	public String toString() {
		return "Post{" +
				"_content='" + _content + '\'' +
				", _id='" + _id + '\'' +
				", _title='" + _title + '\'' +
				", _describe='" + _describe + '\'' +
				", _create_at=" + _create_at +
				", _update_at=" + _update_at +
				", _tags=" + _tags +
				", _is_exist=" + _is_exist +
				", _fig=" + _fig +
				'}';
	}

	public String get_content() {
		return _content;
	}

	public void set_content(String _content) {
		this._content = _content;
	}

	public long get_create_at() {
		return _create_at;
	}

	public void set_create_at(long _create_at) {
		this._create_at = _create_at;
	}

	public String get_describe() {
		return _describe;
	}

	public void set_describe(String _describe) {
		this._describe = _describe;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public List<Tag> get_tags() {
		return _tags;
	}

	public void set_tags(List<Tag> _tags) {
		this._tags = _tags;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public long get_update_at() {
		return _update_at;
	}

	public void set_update_at(long _update_at) {
		this._update_at = _update_at;
	}

	public boolean is_is_exist() {
		return _is_exist;
	}

	public void set_is_exist(boolean _is_exist) {
		this._is_exist = _is_exist;
	}

	public String get_fig() {
		return _fig;
	}

	public void set_fig(String _fig) {
		this._fig = _fig;
	}
}
