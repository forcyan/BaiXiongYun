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

import dao.impl.FileDaoImpl;
import entity.FileMessage;

@WebServlet("/DownloadServlet")
@MultipartConfig
public class DownloadServlet extends HttpServlet {
	public DownloadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		
        //�õ�Ҫ���ص��ļ���
        String fileName = request.getParameter("filename"); 
        System.out.println("��Ҫ���أ�"+fileName);
        //��ѯ���ݿ�
        System.out.println("�����ݿ�Ѱ���ļ�");
        FileDaoImpl FileDaoImpl = new FileDaoImpl();
        String path="upload";
        Vector<FileMessage> files = FileDaoImpl.findFilesByPathAndUser(path, userName);
        if(files.isEmpty()){
            request.setAttribute("message", "�ļ�������");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            return;
        }
        
        //��ȡ�ļ�uuid
        String fileuuid=files.get(0).getUuidName();
        System.out.println("�ļ�uuid��"+fileuuid);
        System.out.println("�ڷ�����Ѱ���ļ�");
        //�ϴ����ļ����Ǳ�����/WEB-INF/uploadĿ¼�µ���Ŀ¼����
        String fileSaveRootPath=this.getServletContext().getRealPath("upload");
        //�õ�Ҫ���ص��ļ�
        File file = new File(fileSaveRootPath + "\\" + fileuuid+"."+files.get(0).getType());
        System.out.println(fileSaveRootPath + "\\" + fileuuid+"."+files.get(0).getType());
        if(!file.exists()){
            request.setAttribute("message", "�ļ�������2");
            request.getRequestDispatcher("FileListServlet").forward(request, response);
            return;
        }
        //�����ļ���
        String realname = fileName.substring(fileName.indexOf("_")+1)+"."+files.get(0).getType();
        //������Ӧͷ��������������ظ��ļ�
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realname, "UTF-8"));
        //��ȡҪ���ص��ļ������浽�ļ�������
        FileInputStream in = new FileInputStream(fileSaveRootPath + "\\" + fileuuid+"."+files.get(0).getType());
        //���������
        OutputStream out = response.getOutputStream();
        //����������
        byte buffer[] = new byte[1024];
        int len = 0;
        //ѭ�����������е����ݶ�ȡ������������
        while((len=in.read(buffer))>0){
            //��������������ݵ��������ʵ���ļ�����
            out.write(buffer, 0, len);
        }
        //�ر��ļ�������
        in.close();
        //�ر������
        out.close();
        request.getRequestDispatcher("FileListServlet").forward(request, response);
    }
    
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }
}
