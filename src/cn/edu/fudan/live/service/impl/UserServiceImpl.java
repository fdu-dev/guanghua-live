package cn.edu.fudan.live.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import cn.edu.fudan.anniversary.dao.EntityDAO;
import cn.edu.fudan.anniversary.exception.LoginFailException;
import cn.edu.fudan.anniversary.exception.RepeatTelephoneException;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IUserService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UserServiceImpl implements IUserService{
	private EntityDAO entityDAO;

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	public User login(User user) {
		// TODO Auto-generated method stub
		String hql = "";
		List<Object> params = new ArrayList<Object>();

		hql = "from User where password=? and username=?";
		params.add(user.getPassword());
		params.add(user.getUsername());

		User u = (User) entityDAO.findUniqueByHql(hql, params);
		if (u == null) {
			throw new LoginFailException();
		}
		return u;
	}
	
	public void addUser(User user) {
		if (entityDAO.isPropertyExist(User.class, "username",
				user.getUsername())) {
			throw new RepeatTelephoneException();
		}
		if (entityDAO.isPropertyExist(User.class, "email",
				user.getEmail())) {
			throw new RepeatTelephoneException();
		}
		entityDAO.save(user);
	}

	@Override
	public User getUserByUid(int uid) {
		// TODO Auto-generated method stub
		User user = (User) entityDAO.get(User.class, uid);
		return user;

	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		entityDAO.update(user);
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("username",username));	
		List<User> userList = entityDAO.findByCriteria(User.class, null, false, 0, 1, criterions);
		if(userList.size()==0)
			return null;
		return userList.get(0);
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("email",email));	
		List<User> userList = entityDAO.findByCriteria(User.class, null, false, 0, 1, criterions);
		if(userList.size()==0)
			return null;
		return userList.get(0);
	}
	
	
}
