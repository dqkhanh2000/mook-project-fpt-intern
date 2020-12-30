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

//@WebServlet(urlPatterns = {"/signup"})
public class SignUpController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("signup.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirm-password");
		RequestDispatcher dispatcher = null;
		
		if(username.equals("") || password.equals("") || confirmPassword.equals("")) {
			req.setAttribute("errString", "Không được để trống");
			dispatcher = req.getRequestDispatcher("signup.jsp");
			dispatcher.forward(req, resp);
		}
		else if(!password.equals(confirmPassword)) {
			req.setAttribute("errString", "Mật khẩu không khớp");
			dispatcher = req.getRequestDispatcher("signup.jsp");
			dispatcher.forward(req, resp);
		}
		else {
			int id = DatabaseUtil.getInstance().createUser(username, password);;
			if(id == -1) {
				req.setAttribute("errString", "Có lỗi trong quá trình tạo tài khoản");
				dispatcher = req.getRequestDispatcher("signup.jsp");
				dispatcher.forward(req, resp);
			}
			else {
				User user = new User(id, username);
				Utils.storeLoginedUser(req.getSession(), user);
				Utils.storeUserCookie(resp, user);
				resp.sendRedirect(req.getContextPath()+"/home");
			}
		}
	}
}
