package dao;

import java.util.Vector;

import entity.FileMessage;

public interface FileDao {
	// ͳ���ļ�����
	public int countFiles();
	// �����ļ�
	public Vector<FileMessage> findFilesByPathAndUser(String path, String userName);
	public Vector<FileMessage> findFilesByTypeAndUser(String type, String user);
	public Vector<FileMessage> findFilesByUser(String user);
	// ����ļ�
	public int addFile(FileMessage file);
	// ɾ���ļ�
	public int delFileByUuidName(String uuidName);
	// ɾ���ļ���
	public int delFolder(String path, String fileName);
}
