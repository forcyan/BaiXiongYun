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
			// 获取用户名
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			// 获取上传的文件
			Part part = request.getPart("file");
			// 获取请求的信息
			String nametmp = part.getHeader("content-disposition");
			String name = new String(nametmp.getBytes(), "utf8");
			System.out.println("请求的信息" + name);// 测试使用

			// 获取文件名
			int tmp1 = name.lastIndexOf("=") + 2;
			int tmp2 = name.lastIndexOf(".");
			String filename = name.substring(tmp1, tmp2);
			System.out.println(filename);// 测试使用

			// 获取上传文件的目录
			String root = request.getServletContext().getRealPath("/upload");
			System.out.println("测试上传文件的路径：" + root);
			// 获取文件类型
			String filetype = name.substring(name.lastIndexOf(".") + 1, name.length() - 1);
			System.out.println("测试文件类型：" + filetype);
			// 获取上传文件时的系统时间
			Timestamp now_time = new Timestamp(System.currentTimeMillis());
			// uuid
			String uuid = UUID.randomUUID().toString();
			// 获取文件大小
			long filesize = part.getSize();
			// 上传目录
			String newfilename = root + "\\" + uuid + "." + filetype;
			System.out.println("测试产生新的文件名：" + newfilename);
			// 上传文件到指定目录，不想上传文件就不调用这个
			part.write(newfilename);
			File thefile = new File(newfilename);
			if (thefile.exists()) {
				// 将文件信息写到数据库
				System.out.println("文件写入数据库");
				FileDaoImpl fileDaoImpl = new FileDaoImpl();
				FileMessage file = new FileMessage(filename, uuid, now_time, filetype, "upload", userName, filesize);
				fileDaoImpl.addFile(file);
			}
			request.setAttribute("info", "上传文件成功");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "上传文件失败");
		}

		request.getRequestDispatcher("FileListServlet").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}