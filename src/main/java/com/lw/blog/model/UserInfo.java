package com.lw.blog.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by LWang on 2016/4/23.
 */
@Document(collection="userInfo")
public class UserInfo {
	@Id
	private String username;
	private String password;
	private String email;
	private long joinTime;
	private long lastloginTime;

	@Override
	public String toString() {
		return "UserInfo{" +
				"email='" + email + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", joinTime=" + joinTime +
				", lastloginTime=" + lastloginTime +
				'}';
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}

	public long getLastloginTime() {
		return lastloginTime;
	}

	public void setLastloginTime(long lastloginTime) {
		this.lastloginTime = lastloginTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
