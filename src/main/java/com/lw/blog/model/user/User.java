/**
 * 
 */
package com.lw.blog.model.user;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author LWang
 * @date   2016-1-15 
 */
@Document(collection="user")
public class User {
	@Id
	private String id;
	private Username name;
	private int age;
	private Date birthday;
	private Timestamp createTime;
	private String sprcial;
	
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Username getName() {
		return name;
	}
	public void setName(Username name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSprcial() {
		return sprcial;
	}
	public void setSprcial(String sprcial) {
		this.sprcial = sprcial;
	}
	
	@Override
	public String toString() {
		return "User{" +
				"age=" + age +
				", id='" + id + '\'' +
				", name=" + name +
				", birthday=" + birthday +
				", createTime=" + createTime +
				", sprcial='" + sprcial + '\'' +
				'}';
	}
}
