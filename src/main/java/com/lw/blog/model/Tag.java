package com.lw.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

	@Override
	public String toString() {
		return "Tag{" +
				"_creat_at='" + _creat_at + '\'' +
				", _id='" + _id + '\'' +
				", _name='" + _name + '\'' +
				", _update_at='" + _update_at + '\'' +
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
}
