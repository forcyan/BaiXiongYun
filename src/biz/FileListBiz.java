package biz;

import java.util.LinkedHashMap;
import java.util.Vector;

import entity.FileMessage;

public interface FileListBiz {
	//public Vector<FileMessage> getFilesByTypeAndUser(String userName, String type);

	public Vector<FileMessage> getFilesByPathAndUser(String path, String userName);
	
	public Vector<FileMessage> getFilesByUser(String userName);

	public String getLastPath(String path);

	public LinkedHashMap<String, String> getPaths(String path);
}