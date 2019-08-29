package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class PermissionFilter
 */
@WebFilter("/PermissionFilter")
public class PermissionFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public PermissionFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpResp = (HttpServletResponse) response;
		String url = httpReq.getRequestURI();//��������url
		System.out.println("�����url:" + url);
		//�ж��Ƿ��ǵ�¼ҳ
			if (!url.substring(url.length()-9).equals("login.jsp")&&
					!url.substring(url.length()-13).equals("SignupServlet")) {
				//��¼�Ƿ�ɹ�
				if (!isLogin(httpReq)) {
					System.out.println("û�е�¼��������");
					httpReq.getSession().setAttribute("message", "δ��¼");
					httpResp.sendRedirect("login.jsp");
				}
			}
		chain.doFilter(request, response);
	}

	boolean isLogin(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String username = "";
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sso")) {
					username = cookie.getValue();
				}
			}
		}
		if (username.equals(""))
			return false;
		else
			return true;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
