package biz.impl;

import java.util.LinkedHashMap;
import java.util.Vector;

import biz.FileListBiz;
import dao.impl.FileDaoImpl;
import entity.FileMessage;

public class FileListBizImpl implements FileListBiz {

	@Override
	public Vector<FileMessage> getFilesByPathAndUser(String path, String userName) {
		FileDaoImpl fileDaoImpl = new FileDaoImpl();
		Vector<FileMessage> files = fileDaoImpl.findFilesByPathAndUser(path, userName);
		return files;
	}
	
	@Override
	public Vector<FileMessage> getFilesByUser(String userName) {
		FileDaoImpl fileDaoImpl = new FileDaoImpl();
		Vector<FileMessage> files = fileDaoImpl.findFilesByUser(userName);
		return files;
	}

	@Override
	public String getLastPath(String path) {
		String pathNames[] = path.split("/");
		int len = pathNames.length;
		String lastPath = "";
		for (int i = 0; i < len - 1; i++) {
			if (i == 0) {
				lastPath = pathNames[i];
			} else {
				lastPath = lastPath + "/" + pathNames[i];
			}
		}
		return lastPath;
	}

	@Override
	public LinkedHashMap<String, String> getPaths(String path) {
		LinkedHashMap<String, String> paths = new LinkedHashMap<>();
		String pathNames[] = path.split("/");
		String curPath = "";
		for (String name : pathNames) {
			if (curPath == "") {
				curPath = name;
				paths.put(name, curPath);
			} else {
				curPath = curPath + "/" + name;
				paths.put(name, curPath);
			}
		}
		return paths;
	}
}
