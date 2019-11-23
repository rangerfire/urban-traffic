package com.moyu.entity;

public class Comment {
	private String name;
	private String email;
	private String phone;
	private String content;
	public Comment(String name, String email, String phone, String content) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.content = content;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
