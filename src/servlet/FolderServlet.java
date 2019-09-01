package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.impl.FileHandleBizImpl;

/**
 * Servlet implementation class FolderServlet
 */
@WebServlet("/FolderServlet")
public class FolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String findpath = "123";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FolderServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// ��ȡsession
		try {
			HttpSession session = request.getSession();
			String user = (String) session.getAttribute("name");// �û�
			String filepath = (String) session.getAttribute("filepath");// �ļ���·��
			String foldername = (String) request.getParameter("newfoldername");// �ļ�������
			// �߼�ʵ��
			FileHandleBizImpl filehandle = new FileHandleBizImpl();
			filehandle.addFolder(foldername, filepath, user);
			request.setAttribute("message", "�ļ��д����ɹ�");

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "�½��ļ���ʧ��");
		}
		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
