package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.UserDaoImpl;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			String account = request.getParameter("signupAccount");
			String username = request.getParameter("signupUsername");
			String pwd = request.getParameter("signupPwd");
			String repwd = request.getParameter("signupRepwd");
			UserDaoImpl userdao = new UserDaoImpl();
			System.out.println(account+username+pwd+repwd);
			if (!pwd.equals(repwd)) {
				request.setAttribute("message", "ע��ʧ��");
				request.getRequestDispatcher("login.jsp").forward(request, response);

			} else {
				System.out.println("ע����Ϣ"+account+username+pwd);
				if(userdao.addUser(username, pwd, account)!=0) {
					System.out.println("ע��ɹ������cookie");
					request.setAttribute("message", "ע��ɹ�");
					request.getSession().setAttribute("name", account);
					Cookie cookie=new Cookie("sso",account);
					cookie.setMaxAge(30 * 60);
					response.addCookie(cookie);
					request.setAttribute("userName", account);
					request.getRequestDispatcher("FileListServlet").forward(request, response);
				}
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
