package com.lw.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Created by LWang on 2016/7/17.
 */

@Document(collection = "tags")
public class Tag {
	@Id
	private String _id;
	@Field("name")
	private String _name;
	@Field("creat_at")
	private long _creat_at;
	@Field("update_at")
	private long _update_at;
	@Field("name_eng")
	private String _name_eng;
	@Field("blog_id")
	private List<String> _blog_id;
	@Field("is_exist")
	private boolean _is_exist;

	@Override
	public String toString() {
		return "Tag{" +
				"_blog_id=" + _blog_id +
				", _id='" + _id + '\'' +
				", _name='" + _name + '\'' +
				", _creat_at=" + _creat_at +
				", _update_at=" + _update_at +
				", _name_eng='" + _name_eng + '\'' +
				", _is_exist=" + _is_exist +
				'}';
	}

	public long get_creat_at() {
		return _creat_at;
	}

	public void set_creat_at(long _creat_at) {
		this._creat_at = _creat_at;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public long get_update_at() {
		return _update_at;
	}

	public void set_update_at(long _update_at) {
		this._update_at = _update_at;
	}

	public List<String> get_blog_id() {
		return _blog_id;
	}

	public void set_blog_id(List<String> _blog_id) {
		this._blog_id = _blog_id;
	}

	public String get_name_eng() {
		return _name_eng;
	}

	public void set_name_eng(String _name_eng) {
		this._name_eng = _name_eng;
	}

	public boolean is_is_exist() {
		return _is_exist;
	}

	public void set_is_exist(boolean _is_exist) {
		this._is_exist = _is_exist;
	}
}
