package com.khanhdq9.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khanhdq9.model.TodoItem;
import com.khanhdq9.model.User;
import com.khanhdq9.util.DatabaseUtil;
import com.khanhdq9.util.Utils;

public class HomeController extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		User userInSession = Utils.getUserLogined(req.getSession());
		
		if(userInSession == null) {
			User userInCookie = Utils.getUserInCookie(req);
			if(userInCookie == null)
				resp.sendRedirect(req.getContextPath()+"/login");
			else {
				Utils.storeLoginedUser(req.getSession(), userInCookie);
				controller(req, resp, userInCookie);
			}
		}
		else {
			controller(req, resp, userInSession);
		}
	}
	
	

	private void controller(HttpServletRequest req, HttpServletResponse resp, User userInSession) throws IOException, ServletException {
		if(req.getParameter("delete") != null) {
			if(DatabaseUtil
					.getInstance()
					.deleteTodo(Integer.parseInt(req.getParameter("delete")),
							userInSession.getId())) {
				resp.sendRedirect(req.getContextPath()+"/home");
			}
		}
		else if(req.getParameter("complete") != null) {
			if(DatabaseUtil
					.getInstance()
					.completeTodo(Integer.parseInt(req.getParameter("complete")),
							userInSession.getId())) {
				resp.sendRedirect(req.getContextPath()+"/home");
			}
		}
		else if(req.getParameter("undo") != null) {
			if(DatabaseUtil
					.getInstance()
					.unCompleteTodo(Integer.parseInt(req.getParameter("undo")),
							userInSession.getId())) {
				resp.sendRedirect(req.getContextPath()+"/home");
			}
		}
		else if(req.getParameter("logout") != null) {
			Utils.deleteUserCookie(resp);
			Utils.storeLoginedUser(req.getSession(), null);
			resp.sendRedirect(req.getContextPath()+"/home");
		}
		else {
			String search = req.getParameter("key");
			
			List<TodoItem> listRoot = null;
			
			if(search != null) {
				listRoot = DatabaseUtil.getInstance().searchTodo(userInSession.getId(), search);
			}
			else {
				listRoot = DatabaseUtil.getInstance().getListTodo(userInSession.getId());
			}
			
			RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
			List<TodoItem> list = new ArrayList<>();
			List<TodoItem> listCompleted = new ArrayList<>();
			for(TodoItem item :  listRoot) {
				if(item.isCompleted()) listCompleted.add(item);
				else list.add(item);
			}
			req.setAttribute("list", list);
			req.setAttribute("listCompleted", listCompleted);
			dispatcher.forward(req, resp);
		}
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		int id = Integer.parseInt(req.getParameter("update"));
		User user = Utils.getUserLogined(req.getSession());
		boolean isUpdate = (id != -1);
		if(isUpdate) {
			TodoItem item = new TodoItem(id, user.getId(), title, content, false);
			DatabaseUtil.getInstance().updateTodo(item);
		}
		else {
			TodoItem item = new TodoItem(user.getId(), title, content);
			DatabaseUtil.getInstance().insertTodo(item);
		}
		
		resp.sendRedirect(req.getContextPath()+"/home");
	}
}
