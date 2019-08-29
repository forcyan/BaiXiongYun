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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("name");
		System.out.println("filelist"+userName);
		// ҵ���߼�
		FileListBizImpl fileListBizImpl = new FileListBizImpl();
		// ͨ��·����ȡfileList
		String path = "upload";
		//ѡ������ʽ
		String sortingways=request.getParameter("sortingways");
		if(sortingways==null) {
			sortingways="time";
		}
		
		//ѡ����ʾ���ļ�����
		String filter = request.getParameter("filter");
		HashSet<String> extensions = new HashSet<String>();
		System.out.println("��ѯ���ݿ�");
		Vector<FileMessage> files = fileListBizImpl.getFilesByPathAndUser(path, userName);
		//ͼƬ
		if("picture".equals(filter)) {
			extensions.add("BMP");
			extensions.add("JPG");
			extensions.add("GIF");
			extensions.add("PNG");
			extensions.add("PSD");
		}
		//��Ƶ
		else if("music".equals(filter)) {
			extensions.add("MP3");
			extensions.add("WAV");
			extensions.add("WMA");
			extensions.add("FLAC");
			extensions.add("MIDI");
		}
		//��Ƶ
		else if("video".equals(filter)) {
			extensions.add("AVI");
			extensions.add("WMV");
			extensions.add("MP4");
			extensions.add("MKV");
			extensions.add("FLV");
			extensions.add("RMVB");
			extensions.add("MOV");
		}
		//�ĵ�
		else if("document".equals(filter)) {
			extensions.add("TXT");
			extensions.add("DOCX");
			extensions.add("DOC");
			extensions.add("PPT");
			extensions.add("PPTX");
			extensions.add("PDF");
			extensions.add("XLS");
			extensions.add("XLSX");
			
		}
		//ѹ���ļ�
		else if("zip".equals(filter)) {
			extensions.add("ZIP");
			extensions.add("7Z");
			extensions.add("RAR");
			//extensions.add("z01");
			//extensions.add("z02");
		}
		//����
		else if("torrent".equals(filter)) {
			extensions.add("TORRENT");
		}
		
		Vector<FileMessage> filteredFiles = new Vector<FileMessage>();
		for(FileMessage f : files) {
			if(extensions.contains(f.getType().toUpperCase())) {
				filteredFiles.add(f);
			}
		}
		if(filter!=null) {
			files=filteredFiles;
		}
		//�ļ�����
		if(sortingways.equals("time")) {
			System.out.println("ͨ���޸�ʱ������");
			System.out.println(files.size());
			FileMessage temp = new FileMessage(null, null, null, null, null, null, 0);
	        for (int i = 0; i < files.size(); i++) {	            
	            for (int j= 0;j<(files.size()-1-i);j++) {
		            //compareTo���ܣ�
		            //����� timestamp ���������������ȣ��򷵻�ֵ 0��
		            //����� timestamp �������ڸ����������򷵻�С�� 0 ��ֵ��
		            //����� timestamp �������ڸ����������򷵻ش��� 0 ��ֵ��
	            	if(files.elementAt(j+1).getUpdateTime().compareTo(files.elementAt(j).getUpdateTime())<=0) {
	    	        	temp.setFileName(files.elementAt(j+1).getFileName());
	    	        	temp.setSize(files.elementAt(j+1).getSize());
	    	        	temp.setUpdateTime(files.elementAt(j+1).getUpdateTime());
	    	        		    	        	
		            	files.elementAt(j+1).setFileName(files.elementAt(j).getFileName());
		            	files.elementAt(j+1).setSize(files.elementAt(j).getSize());
		            	files.elementAt(j+1).setUpdateTime(files.elementAt(j).getUpdateTime());
		            	
		            	files.elementAt(j).setFileName(temp.getFileName());
		            	files.elementAt(j).setSize(temp.getSize());
		            	files.elementAt(j).setUpdateTime(temp.getUpdateTime());
	            	}
	            }
	        }	        
		}
		
		else if(sortingways.equals("name")) {
			System.out.println("ͨ���ļ�������");
			System.out.println(files.size());
			FileMessage temp = new FileMessage(null, null, null, null, null, null, 0);
	        for (int i = 0; i < files.size(); i++) {	            
	            for (int j= 0;j<(files.size()-1-i);j++) {
	            	if(files.elementAt(j+1).getFileName().compareTo(files.elementAt(j).getFileName())<=0) {
	    	        	temp.setFileName(files.elementAt(j+1).getFileName());
	    	        	temp.setSize(files.elementAt(j+1).getSize());
	    	        	temp.setUpdateTime(files.elementAt(j+1).getUpdateTime());
	    	        		    	        	
		            	files.elementAt(j+1).setFileName(files.elementAt(j).getFileName());
		            	files.elementAt(j+1).setSize(files.elementAt(j).getSize());
		            	files.elementAt(j+1).setUpdateTime(files.elementAt(j).getUpdateTime());
		            	
		            	files.elementAt(j).setFileName(temp.getFileName());
		            	files.elementAt(j).setSize(temp.getSize());
		            	files.elementAt(j).setUpdateTime(temp.getUpdateTime());
	            	}
	            }
	        }
		}
		
		else if(sortingways.equals("size")) {
			System.out.println("ͨ���ļ���С����");
			System.out.println(files.size());
			FileMessage temp = new FileMessage(null, null, null, null, null, null, 0);
	        for (int i = 0; i < files.size() - 1; i++) {
	        	temp.setFileName(files.elementAt(i+1).getFileName());
	        	temp.setSize(files.elementAt(i+1).getSize());
	        	temp.setUpdateTime(files.elementAt(i+1).getUpdateTime());
	            int preIndex = i;
	            while (preIndex >= 0 && temp.getSize()<=files.elementAt(preIndex).getSize()) {
	            	files.elementAt(preIndex+1).setFileName(files.elementAt(preIndex).getFileName());
	            	files.elementAt(preIndex+1).setSize(files.elementAt(preIndex).getSize());
	            	files.elementAt(preIndex+1).setUpdateTime(files.elementAt(preIndex).getUpdateTime());
	                preIndex--;
	            }
	            //files[preIndex + 1] = current;
            	files.elementAt(preIndex + 1).setFileName(temp.getFileName());
            	files.elementAt(preIndex+1).setSize(temp.getSize());
            	files.elementAt(preIndex+1).setUpdateTime(temp.getUpdateTime());
	        }

		}
		for (FileMessage tmp : files) {
			System.out.println(tmp.getFileName());
			System.out.println(tmp.getSize());
			System.out.println(tmp.getUpdateTime());
		}
		request.setAttribute("files", files);
		request.setAttribute("sortingways", sortingways);
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
	
	public static int compairByName(String file1,String file2){
		Comparator<Object> com=Collator.getInstance(java.util.Locale.CHINA);
		return com.compare(file1, file2);
	}

}
