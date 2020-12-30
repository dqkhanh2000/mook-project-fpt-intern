package com.khanhdq9.model;

public class TodoItem {
	private int id;
	private int userId;
	private String title;
	private String content;
	private boolean isCompleted = false;
	
	public TodoItem(int id, int userId, String title, String content, boolean isCompleted) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.isCompleted = isCompleted;
	}
	
	public TodoItem(int userId, String title, String content) {
		this.userId = userId;
		this.title = title;
		this.content = content;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	
}
