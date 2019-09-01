package biz.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.Vector;

import biz.FileHandleBiz;
import dao.impl.FileDaoImpl;
import entity.FileMessage;

public class FileHandleBizImpl implements FileHandleBiz {

	public FileHandleBizImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addFolder(String foldername, String folderpath, String user) {
		System.out.println("�½��ļ���"+foldername);
		FileDaoImpl filedao = new FileDaoImpl();
		// uuid
		String uuid = UUID.randomUUID().toString();
		// ��ȡ�ϴ��ļ�ʱ��ϵͳʱ��
		Timestamp now_time = new Timestamp(System.currentTimeMillis());
		// �ļ�����
		String filetype = "folder";
		// �ļ���С
		long size = 0;
		FileMessage folder = new FileMessage(foldername, uuid, now_time, filetype, folderpath, user, size);
		filedao.addFile(folder);

	}

	@Override
	public boolean deleteFolder(String foldername,String path, String userName, String fileSaveRootPath) {
		FileDaoImpl FileDaoImpl = new FileDaoImpl();
		FileListBizImpl filelistBiz = new FileListBizImpl();
		Vector<FileMessage> folders = filelistBiz.getFilesByPathAndUser(path, userName);
		Vector<FileMessage> allfile = filelistBiz.getFilesByPathAndUser(path+"/"+foldername, userName);
		System.out.println("ɾ���ļ���"+path+foldername);
		//ɾ���ļ���
		if(folders.isEmpty())
			return false;
		for(FileMessage tmp : folders) {
			if(tmp.getFileName().equals(foldername)&&tmp.getType().equals("folder")) {
				System.out.println("ɾ���ļ���"+tmp.getFileName());
				String fileuuid = tmp.getUuidName();
				FileDaoImpl.delFileByUuidName(fileuuid);
			}
		}
		// ɾ���ļ����µ��ļ�
		for (FileMessage tmp : allfile) {
			String fileuuid = tmp.getUuidName();
			File file = new File(fileSaveRootPath + "\\" + fileuuid + "." + tmp.getType());
			if (!file.exists())
				return false;
			else {
				file.delete();
				FileDaoImpl.delFileByUuidName(fileuuid);
				return true;
			}
		}
		return true;// �ļ�����û���ļ�
	}

	@Override
	public boolean deleteFile(String filename, String filepath) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public FileMessage searchFile(Vector<FileMessage> files,String filename) {
		for(FileMessage tmp:files) {
			System.out.println(tmp.getFileName());
			if(tmp.getFileName().equals(filename))
				return tmp;
		}
		return null;
	}

}
