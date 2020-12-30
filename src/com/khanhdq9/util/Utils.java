package com.khanhdq9.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.khanhdq9.model.User;

public class Utils {
	
	private static final String COOKIE_NAME = "TODO_USER_COOKIE";

	public static void storeLoginedUser(HttpSession session, User user) {
        session.setAttribute("logined", user);
    }
	
	public static User getUserLogined(HttpSession session) {
		return (User) session.getAttribute("logined");
	}
	
    public static void storeUserCookie(HttpServletResponse response, User user) {
        System.out.println("Store user cookie");
        Cookie cookieUser = new Cookie(COOKIE_NAME, user.toString());
        //1 day
        cookieUser.setMaxAge(24 * 60 * 60);
        response.addCookie(cookieUser);
    }
 
    public static User getUserInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    String[] tmp = cookie.getValue().split("|", 3);                    
                    return new User(Integer.parseInt(tmp[0]), tmp[2]);
                }
            }
        }
        return null;
    }
 
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(COOKIE_NAME, null);
        // 0 giây.
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
}
