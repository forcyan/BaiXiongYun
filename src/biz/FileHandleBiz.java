package biz;

import java.util.Vector;

import entity.FileMessage;

public interface FileHandleBiz {
	/**
	 * 
	 * @param filename
	 * @param filepath
	 * @return 
	 */
	public boolean deleteFile(String filename,String filepath);
	/**
	 * 
	 * @param foldername
	 * @param folderpath
	 * @param user
	 */
	public void addFolder(String foldername,String folderpath,String user);
	/**
	 * 
	 * @param foldername
	 * @param user
	 * @param fileSaveRootPath
	 * @return
	 */
	public boolean deleteFolder(String foldername,String path,String user,String fileSaveRootPath);
	
	public FileMessage searchFile(Vector<FileMessage> files,String filename);
}
