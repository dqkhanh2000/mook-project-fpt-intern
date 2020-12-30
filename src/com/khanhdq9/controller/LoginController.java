package com.khanhdq9.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khanhdq9.model.User;
import com.khanhdq9.util.DatabaseUtil;
import com.khanhdq9.util.Utils;

//@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		User user = DatabaseUtil.getInstance().checkLogin(username, password);
		
		if(user != null) {
			Utils.storeLoginedUser(req.getSession(), user);
			Utils.storeUserCookie(resp, user);
			resp.sendRedirect(req.getContextPath()+"/home");
		}
		else {
			req.setAttribute("errString", "Sai tài khoản hoặc mật khẩu");
			RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
			dispatcher.forward(req, resp);
		}
	} 
	
}
