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
    	//得到要下载的文件名
        String fileName = request.getParameter("filename"); 
        System.out.println("将要删除："+fileName);
        //查询数据库
        System.out.println("在数据库寻找文件");
        FileDaoImpl FileDaoImpl = new FileDaoImpl();
        Vector<FileMessage> files = FileDaoImpl.findFilesByPathAndUser(filepath,userName);
        if(files.isEmpty()){
            request.setAttribute("message", "文件不存在");
            request.getRequestDispatcher("FileListServlet").forward(request, response);
            return;
        }
        FileHandleBizImpl filehandle=new FileHandleBizImpl();
		FileMessage thefile=filehandle.searchFile(files, fileName);
        //获取文件uuid
        String fileuuid=thefile.getUuidName();
        System.out.println("文件uuid："+fileuuid);
        
        
        System.out.println("在服务器寻找文件");
        //上传的文件都是保存在upload目录下的子目录当中
        String fileSaveRootPath=this.getServletContext().getRealPath("upload");

        //得到要删除的文件
        File file = new File(fileSaveRootPath + "\\" + fileuuid+"."+thefile.getType());
        System.out.println(fileSaveRootPath + "\\" + fileuuid+"."+thefile.getType());
        //如果文件不存在
        if(!file.exists()){
            request.setAttribute("message", "文件不存在");
            request.getRequestDispatcher("FileListServlet").forward(request, response);
            return;
        }
        else
        {
        	file.delete();
        }
        //将文件从数据库删除
        System.out.println("从数据库删除"+fileuuid);
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
