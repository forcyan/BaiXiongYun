package dao;

import java.util.Vector;

import dao.impl.*;
import entity.User;

public interface UserDao {
	// ͳ���û�����
		public int countUsers();
		// �����û�
		public Vector<User> findbyaccount(String getaccount);
		public User findUserByAccount(String getaccount);
		public Vector<User> findbyusername(String getUserName);
		// ����û�
		public int addUser(String getUserName,String pwd,String getaccount);
		// ɾ���û�
		public int delUser(String getaccount);
		
		
}
