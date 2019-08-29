package biz.impl;

import biz.LoginBiz;
import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;

public class LoginBizImpl implements LoginBiz {
	private UserDaoImpl userdao=new UserDaoImpl();
	@Override
	public User login(String account, String pwd) {
		System.out.println("loginbiz:");
		System.out.println("account:" + account);
		User user=userdao.findUserByAccount(account);
		if (user != null && user.getPwd().equals(pwd)) {
			System.out.println(user.getPwd());
			System.out.println(pwd);
			return user;
		}
		return null;
	}

}
