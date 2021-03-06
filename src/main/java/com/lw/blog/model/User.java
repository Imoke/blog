/**
 * 
 */
package com.lw.blog.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 *
 */
@Document(collection="user")
public class User {
	@Id
	private String _id;
	@Field("third_part")
	private String _thirdPart;
	@Field("user_name")
	private String _userName;
	@Field("user_icon")
	private String _userIcon;
	@Field("user_password")
	private String _userPassword;
	@Field("login_time")
	private List<Long> _loginTime;
	@Field("is_third_part")
	private Boolean _isThirdPart;
	@Field("register_time")
	private long _registerTime;
	@Field("authorities")//1表示管理员
	private int _authorities;

	@Override
	public String toString() {
		return "User{" +
				"_authorities=" + _authorities +
				", _id='" + _id + '\'' +
				", _thirdPart='" + _thirdPart + '\'' +
				", _userName='" + _userName + '\'' +
				", _userIcon='" + _userIcon + '\'' +
				", _userPassword='" + _userPassword + '\'' +
				", _loginTime=" + _loginTime +
				", _isThirdPart=" + _isThirdPart +
				", _registerTime=" + _registerTime +
				'}';
	}

	public int get_authorities() {
		return _authorities;
	}

	public void set_authorities(int _authorities) {
		this._authorities = _authorities;
	}

	public long get_registerTime() {
		return _registerTime;
	}

	public void set_registerTime(long _registerTime) {
		this._registerTime = _registerTime;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Boolean get_isThirdPart() {
		return _isThirdPart;
	}

	public void set_isThirdPart(Boolean _isThirdPart) {
		this._isThirdPart = _isThirdPart;
	}

	public List<Long> get_loginTime() {
		return _loginTime;
	}

	public void set_loginTime(List<Long> _loginTime) {
		this._loginTime = _loginTime;
	}

	public String get_thirdPart() {
		return _thirdPart;
	}

	public void set_thirdPart(String _thirdPart) {
		this._thirdPart = _thirdPart;
	}

	public String get_userIcon() {
		return _userIcon;
	}

	public void set_userIcon(String _userIcon) {
		this._userIcon = _userIcon;
	}

	public String get_userName() {
		return _userName;
	}

	public void set_userName(String _userName) {
		this._userName = _userName;
	}

	public String get_userPassword() {
		return _userPassword;
	}

	public void set_userPassword(String _userPassword) {
		this._userPassword = _userPassword;
	}

}
