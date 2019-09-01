package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import biz.impl.FileHandleBizImpl;

/**
 * Servlet implementation class DeleteFolderServlet
 */
@WebServlet("/DeleteFolderServlet")
public class DeleteFolderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteFolderServlet() {
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
		try {
			// ��ȡ�û���
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			String filepath = (String) session.getAttribute("filepath");
			String foldername = request.getParameter("foldername"); 
			System.out.println("��Ҫɾ���ļ���"+foldername);
			String fileSaveRootPath=this.getServletContext().getRealPath("upload");
			FileHandleBizImpl filehandle=new FileHandleBizImpl();
			if(filehandle.deleteFolder(foldername,filepath ,userName, fileSaveRootPath)) {
				request.setAttribute("message", "�ļ���ɾ���ɹ�");
			}else {
				request.setAttribute("message", "�ļ���ɾ��ʧ��");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "�ļ���ɾ��ʧ��");
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
