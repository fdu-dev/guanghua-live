package cn.edu.fudan.live.service;

import cn.edu.fudan.live.bean.User;

public interface IUserService {

	public User login(User user);

	public void addUser(User user);
	
	public User getUserByUid(int uid);
	
	public void updateUser(User user);

	public User getUserByUsername(String username);

	public User getUserByEmail(String email);

}
