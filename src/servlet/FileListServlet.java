package servlet;

import java.io.IOException;
import java.text.Collator;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import biz.impl.FileListBizImpl;
import entity.FileMessage;

@WebServlet("/FileListServlet")
public class FileListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FileListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			String userName = (String) session.getAttribute("name");
			String path = (String) request.getParameter("filepath");

			System.out.println("文件列表\t" + userName + "\t" + path);

			if (userName == null) {
				request.setAttribute("message", "登录后才能访问哦！");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
			if (path == null) {
				path = (String) session.getAttribute("filepath");
				if (path == null) {
					// 默认路径
					path = "upload";
					session.setAttribute("filepath", path);
				}
			} else {
				session.setAttribute("filepath", path);
				String folderName = (String) request.getParameter("foldername");
				if (folderName != null) {
					path = path + "/" + folderName;
					System.out.println("进入文件夹" + folderName+"\t路径\t"+path);
					session.setAttribute("filepath", path);
				}
			}
			
			/*
			 * 以下是排序和分类 不要改！！
			 */

			// 选择排序方式
			String sortingways = request.getParameter("sortingways");
			if (sortingways == null) {
				sortingways = "time";
			}

			// 选择显示的文件类型
			String filter = request.getParameter("filter");
			HashSet<String> extensions = new HashSet<String>();
			System.out.println("查询数据库");
			// 业务逻辑
			FileListBizImpl fileListBizImpl = new FileListBizImpl();
			Vector<FileMessage> files = fileListBizImpl.getFilesByPathAndUser(path, userName);

			// 图片
			if ("picture".equals(filter)) {
				extensions.add("BMP");
				extensions.add("JPG");
				extensions.add("GIF");
				extensions.add("PNG");
				extensions.add("PSD");
			}
			// 音频
			else if ("music".equals(filter)) {
				extensions.add("MP3");
				extensions.add("WAV");
				extensions.add("WMA");
				extensions.add("FLAC");
				extensions.add("MIDI");
			}
			// 视频
			else if ("video".equals(filter)) {
				extensions.add("AVI");
				extensions.add("WMV");
				extensions.add("MP4");
				extensions.add("MKV");
				extensions.add("FLV");
				extensions.add("RMVB");
				extensions.add("MOV");
			}
			// 文档
			else if ("document".equals(filter)) {
				extensions.add("TXT");
				extensions.add("DOCX");
				extensions.add("DOC");
				extensions.add("PPT");
				extensions.add("PPTX");
				extensions.add("PDF");
				extensions.add("XLS");
				extensions.add("XLSX");

			}
			// 压缩文件
			else if ("zip".equals(filter)) {
				extensions.add("ZIP");
				extensions.add("7Z");
				extensions.add("RAR");
				// extensions.add("z01");
				// extensions.add("z02");
			}
			// 种子
			else if ("torrent".equals(filter)) {
				extensions.add("TORRENT");
			}

			Vector<FileMessage> filteredFiles = new Vector<FileMessage>();
			for (FileMessage f : files) {
				if (extensions.contains(f.getType().toUpperCase())) {
					filteredFiles.add(f);
				}
			}
			if (filter != null) {
				files = filteredFiles;
			}
			// 文件排序
			if (sortingways.equals("time")) {
				System.out.println("通过修改时间排序");
				System.out.println(files.size());
				FileMessage temp = new FileMessage(null, null, null, null, null, null, 0);
				for (int i = 0; i < files.size(); i++) {
					for (int j = 0; j < (files.size() - 1 - i); j++) {
						// compareTo介绍：
						// 如果此 timestamp 对象与给定对象相等，则返回值 0；
						// 如果此 timestamp 对象早于给定参数，则返回小于 0 的值；
						// 如果此 timestamp 对象晚于给定参数，则返回大于 0 的值。
						if (files.elementAt(j + 1).getUpdateTime().compareTo(files.elementAt(j).getUpdateTime()) <= 0) {
							temp.setFileName(files.elementAt(j + 1).getFileName());
							temp.setSize(files.elementAt(j + 1).getSize());
							temp.setUpdateTime(files.elementAt(j + 1).getUpdateTime());

							files.elementAt(j + 1).setFileName(files.elementAt(j).getFileName());
							files.elementAt(j + 1).setSize(files.elementAt(j).getSize());
							files.elementAt(j + 1).setUpdateTime(files.elementAt(j).getUpdateTime());

							files.elementAt(j).setFileName(temp.getFileName());
							files.elementAt(j).setSize(temp.getSize());
							files.elementAt(j).setUpdateTime(temp.getUpdateTime());
						}
					}
				}
			}

			else if (sortingways.equals("name")) {
				System.out.println("通过文件名排序");
				System.out.println(files.size());
				FileMessage temp = new FileMessage(null, null, null, null, null, null, 0);
				for (int i = 0; i < files.size(); i++) {
					for (int j = 0; j < (files.size() - 1 - i); j++) {
						if (files.elementAt(j + 1).getFileName().compareTo(files.elementAt(j).getFileName()) <= 0) {
							temp.setFileName(files.elementAt(j + 1).getFileName());
							temp.setSize(files.elementAt(j + 1).getSize());
							temp.setUpdateTime(files.elementAt(j + 1).getUpdateTime());

							files.elementAt(j + 1).setFileName(files.elementAt(j).getFileName());
							files.elementAt(j + 1).setSize(files.elementAt(j).getSize());
							files.elementAt(j + 1).setUpdateTime(files.elementAt(j).getUpdateTime());

							files.elementAt(j).setFileName(temp.getFileName());
							files.elementAt(j).setSize(temp.getSize());
							files.elementAt(j).setUpdateTime(temp.getUpdateTime());
						}
					}
				}
			}

			else if (sortingways.equals("size")) {
				System.out.println("通过文件大小排序");
				System.out.println(files.size());
				FileMessage temp = new FileMessage(null, null, null, null, null, null, 0);
				for (int i = 0; i < files.size() - 1; i++) {
					temp.setFileName(files.elementAt(i + 1).getFileName());
					temp.setSize(files.elementAt(i + 1).getSize());
					temp.setUpdateTime(files.elementAt(i + 1).getUpdateTime());
					int preIndex = i;
					while (preIndex >= 0 && temp.getSize() <= files.elementAt(preIndex).getSize()) {
						files.elementAt(preIndex + 1).setFileName(files.elementAt(preIndex).getFileName());
						files.elementAt(preIndex + 1).setSize(files.elementAt(preIndex).getSize());
						files.elementAt(preIndex + 1).setUpdateTime(files.elementAt(preIndex).getUpdateTime());
						preIndex--;
					}
					// files[preIndex + 1] = current;
					files.elementAt(preIndex + 1).setFileName(temp.getFileName());
					files.elementAt(preIndex + 1).setSize(temp.getSize());
					files.elementAt(preIndex + 1).setUpdateTime(temp.getUpdateTime());
				}

			}
			for (FileMessage tmp : files) {
				System.out.println(tmp.getFileName());
				System.out.println(tmp.getSize());
				System.out.println(tmp.getUpdateTime());
			}
			request.setAttribute("files", files);
			request.setAttribute("sortingways", sortingways);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("info", "上传文件失败");
		}
		request.getRequestDispatcher("home.jsp").forward(request, response);
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

	public static int compairByName(String file1, String file2) {
		Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
		return com.compare(file1, file2);
	}

}
