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
	@Field("ctrate_at")
	private long _ctrate_at;
	@Field("update_at")
	private long _update_at;
	@Field("tags_name")
	private List<String> _tags_name;

	@Override
	public String toString() {
		return "Post{" +
				"_content='" + _content + '\'' +
				", _id='" + _id + '\'' +
				", _title='" + _title + '\'' +
				", _describe='" + _describe + '\'' +
				", _ctrate_at=" + _ctrate_at +
				", _update_at=" + _update_at +
				", _tags=" + _tags_name +
				'}';
	}

	public String get_content() {
		return _content;
	}

	public void set_content(String _content) {
		this._content = _content;
	}

	public long get_ctrate_at() {
		return _ctrate_at;
	}

	public void set_ctrate_at(long _ctrate_at) {
		this._ctrate_at = _ctrate_at;
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

	public List<String> get_tags() {
		return _tags_name;
	}

	public void set_tags(List<String> _tags_name) {
		this._tags_name = _tags_name;
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
}
