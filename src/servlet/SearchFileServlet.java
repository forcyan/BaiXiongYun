package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.impl.FileListBizImpl;
import entity.FileMessage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class SearchFileServlet
 */
@WebServlet("/SearchFileServlet")
public class SearchFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	Vector<FileMessage> files=new Vector<FileMessage>();
    	//��ȡ�û���
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		//��ȡ�ؼ���
		String searchthing=request.getParameter("searchthing");
		//��ȡ���û������ļ�
		
		System.out.println(userName+"searching"+searchthing);
		
		FileListBizImpl filelistBiz=new FileListBizImpl();
		Vector<FileMessage> allfile=filelistBiz.getFilesByUser(userName);
		for (FileMessage tmp : allfile) {
			if(tmp.getFileName().indexOf(searchthing)!=-1) {
				System.out.println(tmp.getFileName());
				System.out.println(tmp.getSize());
				System.out.println(tmp.getUpdateTime());
				files.add(tmp);
			}
		}
		
		request.setAttribute("files", files);
		request.getRequestDispatcher("home.jsp").forward(request, response);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

}
