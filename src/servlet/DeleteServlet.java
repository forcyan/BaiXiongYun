package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.impl.FileHandleBizImpl;
import dao.impl.FileDaoImpl;
import entity.FileMessage;

@WebServlet("/DeleteServlet")
@MultipartConfig
public class DeleteServlet extends HttpServlet {

	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		String filepath = (String) session.getAttribute("filepath");
    	//�õ�Ҫ���ص��ļ���
        String fileName = request.getParameter("filename"); 
        System.out.println("��Ҫɾ����"+fileName);
        //��ѯ���ݿ�
        System.out.println("�����ݿ�Ѱ���ļ�");
        FileDaoImpl FileDaoImpl = new FileDaoImpl();
        Vector<FileMessage> files = FileDaoImpl.findFilesByPathAndUser(filepath,userName);
        if(files.isEmpty()){
            request.setAttribute("message", "�ļ�������");
            request.getRequestDispatcher("FileListServlet").forward(request, response);
            return;
        }
        FileHandleBizImpl filehandle=new FileHandleBizImpl();
		FileMessage thefile=filehandle.searchFile(files, fileName);
        //��ȡ�ļ�uuid
        String fileuuid=thefile.getUuidName();
        System.out.println("�ļ�uuid��"+fileuuid);
        
        
        System.out.println("�ڷ�����Ѱ���ļ�");
        //�ϴ����ļ����Ǳ�����uploadĿ¼�µ���Ŀ¼����
        String fileSaveRootPath=this.getServletContext().getRealPath("upload");

        //�õ�Ҫɾ�����ļ�
        File file = new File(fileSaveRootPath + "\\" + fileuuid+"."+thefile.getType());
        System.out.println(fileSaveRootPath + "\\" + fileuuid+"."+thefile.getType());
        //����ļ�������
        if(!file.exists()){
            request.setAttribute("message", "�ļ�������");
            request.getRequestDispatcher("FileListServlet").forward(request, response);
            return;
        }
        else
        {
        	file.delete();
        }
        //���ļ������ݿ�ɾ��
        System.out.println("�����ݿ�ɾ��"+fileuuid);
        FileDaoImpl.delFileByUuidName(fileuuid);
        request.getRequestDispatcher("FileListServlet").forward(request, response);
    }

    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
