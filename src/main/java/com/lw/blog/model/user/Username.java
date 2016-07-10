/**
 * 
 */
package com.lw.blog.model.user;

/**
 * @author LWang
 * @date   2016-1-15 
 */
public class Username {
	private String username;
	private String nickname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "Username{" +
				"nickname='" + nickname + '\'' +
				", username='" + username + '\'' +
				'}';
	}
}
