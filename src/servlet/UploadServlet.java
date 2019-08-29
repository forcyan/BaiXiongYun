package servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.io.File;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.UUID;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import dao.impl.*;
import entity.*;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// ��ȡ�û���
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			// ��ȡ�ϴ����ļ�
			Part part = request.getPart("file");
			// ��ȡ�������Ϣ
			String nametmp = part.getHeader("content-disposition");
			String name = new String(nametmp.getBytes(), "utf8");
			System.out.println("�������Ϣ" + name);// ����ʹ��

			// ��ȡ�ļ���
			int tmp1 = name.lastIndexOf("=") + 2;
			int tmp2 = name.lastIndexOf(".");
			String filename = name.substring(tmp1, tmp2);
			System.out.println(filename);// ����ʹ��

			// ��ȡ�ϴ��ļ���Ŀ¼
			String root = request.getServletContext().getRealPath("/upload");
			System.out.println("�����ϴ��ļ���·����" + root);
			// ��ȡ�ļ�����
			String filetype = name.substring(name.lastIndexOf(".") + 1, name.length() - 1);
			System.out.println("�����ļ����ͣ�" + filetype);
			// ��ȡ�ϴ��ļ�ʱ��ϵͳʱ��
			Timestamp now_time = new Timestamp(System.currentTimeMillis());
			// uuid
			String uuid = UUID.randomUUID().toString();
			// ��ȡ�ļ���С
			long filesize = part.getSize();
			// �ϴ�Ŀ¼
			String newfilename = root + "\\" + uuid + "." + filetype;
			System.out.println("���Բ����µ��ļ�����" + newfilename);
			// �ϴ��ļ���ָ��Ŀ¼�������ϴ��ļ��Ͳ��������
			part.write(newfilename);
			File thefile = new File(newfilename);
			if (thefile.exists()) {
				// ���ļ���Ϣд�����ݿ�
				System.out.println("�ļ�д�����ݿ�");
				FileDaoImpl fileDaoImpl = new FileDaoImpl();
				FileMessage file = new FileMessage(filename, uuid, now_time, filetype, "upload", userName, filesize);
				fileDaoImpl.addFile(file);
			}
			request.setAttribute("info", "�ϴ��ļ��ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "�ϴ��ļ�ʧ��");
		}

		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}