package dao;

import java.util.Vector;

import entity.FileMessage;

public interface FileDao {
	// 统计文件总数
	public int countFiles();
	// 查找文件
	public Vector<FileMessage> findFilesByPathAndUser(String path, String userName);
	public Vector<FileMessage> findFilesByTypeAndUser(String type, String user);
	public Vector<FileMessage> findFilesByUser(String user);
	// 添加文件
	public int addFile(FileMessage file);
	// 删除文件
	public int delFileByUuidName(String uuidName);
	// 删除文件夹
	public int delFolder(String path, String fileName);
}
