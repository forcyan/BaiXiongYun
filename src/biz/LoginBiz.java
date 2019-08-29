package biz;

import entity.User;

public interface LoginBiz {
	public User login(String account, String password);
}
