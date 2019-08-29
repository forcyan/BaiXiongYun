package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.impl.LoginBizImpl;
import entity.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");

		System.out.println("userName:" + userName);
		System.out.println("password:" + pwd);

		LoginBizImpl loginBiz = new LoginBizImpl();
		User user = loginBiz.login(userName, pwd);

		if (user == null) {
			request.setAttribute("message", "用户名或密码错误！");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			// 登录成功
			// 添加cookie
			System.out.println("用户名密码正确，添加cookie");
			request.getSession().setAttribute("name", userName);
			Cookie cookie=new Cookie("sso",userName);
			cookie.setMaxAge(30 * 60);
			response.addCookie(cookie);
			request.setAttribute("userName", userName);
			request.getRequestDispatcher("FileListServlet").forward(request, response);
			//response.sendRedirect("FileListServlet");
		}
	}

}
