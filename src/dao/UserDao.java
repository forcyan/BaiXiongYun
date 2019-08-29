package dao;

import java.util.Vector;

import dao.impl.*;
import entity.User;

public interface UserDao {
	// 统计用户总数
		public int countUsers();
		// 查找用户
		public Vector<User> findbyaccount(String getaccount);
		public User findUserByAccount(String getaccount);
		public Vector<User> findbyusername(String getUserName);
		// 添加用户
		public int addUser(String getUserName,String pwd,String getaccount);
		// 删除用户
		public int delUser(String getaccount);
		
		
}
