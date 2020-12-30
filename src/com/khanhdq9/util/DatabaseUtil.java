package com.khanhdq9.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.khanhdq9.model.TodoItem;
import com.khanhdq9.model.User;

public class DatabaseUtil {
	
	private static DatabaseUtil service = null;
	private static String HOST = "localhost";
    private static String DB_NAME = "todo";
    private static String USERNAME = "root";
    private static String PASSWORD = "87654321";
    private static String connectionString = "jdbc:mysql://"+ HOST + ":3306/" + DB_NAME+"?characterEncoding=utf-8";
    
    private Connection conn;
	
	public static DatabaseUtil getInstance(){
		if(service != null) return service;
		else {
			return new DatabaseUtil();
		}
	}
	
	public DatabaseUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.conn = DriverManager.getConnection(connectionString, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User checkLogin(String username, String password) {
		String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setNString(2, password);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				return new User(rs.getInt("id"), rs.getString("username"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int createUser(String username, String password) {
		String sql = "INSERT INTO users(username, password) VALUES (?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, username);
			stmt.setNString(2, password);
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return -1;
		}
		return -1;
	}
	
	public List<TodoItem> getListTodo(int userId){
		String sql = "SELECT * FROM todo WHERE user_id = " + userId + " ORDER BY id DESC";
		List<TodoItem> list = new ArrayList<>();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				list.add(new TodoItem(rs.getInt("id"),
						rs.getInt("user_id"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getBoolean("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public boolean insertTodo(TodoItem item) {
		String sql = "INSERT INTO todo(user_id, title, content) VALUES (?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, item.getUserId());
			stmt.setNString(2, item.getTitle());
			stmt.setNString(3, item.getContent());
			return stmt.executeUpdate() > 0;
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public boolean deleteTodo(int id, int userId) {
		String sql = "DELETE FROM todo WHERE id = ? AND user_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setInt(2, userId);
			return stmt.executeUpdate() > 0;
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public boolean completeTodo(int id, int userId) {
		String sql = "UPDATE todo SET status = 1 WHERE id = ? AND user_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setInt(2, userId);
			return stmt.executeUpdate() > 0;
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	public boolean unCompleteTodo(int id, int userId) {
		String sql = "UPDATE todo SET status = 0 WHERE id = ? AND user_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setInt(2, userId);
			return stmt.executeUpdate() > 0;
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public boolean updateTodo(TodoItem item) {
		String sql = "UPDATE todo SET title = ?, content = ? WHERE id = ? AND user_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, item.getTitle());
			stmt.setString(2, item.getContent());
			stmt.setInt(3, item.getId());
			stmt.setInt(4, item.getUserId());
			return stmt.executeUpdate() > 0;
			
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}
	
	public List<TodoItem> searchTodo(int userId, String key){
		key = '%'+key+'%';
		String sql = "SELECT * FROM todo WHERE title LIKE ? AND user_id = ?";
		List<TodoItem> list = new ArrayList<>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(2, userId);
			stmt.setString(1, key);
			
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				list.add(new TodoItem(rs.getInt("id"),
						rs.getInt("user_id"),
						rs.getString("title"),
						rs.getString("content"),
						rs.getBoolean("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

}
