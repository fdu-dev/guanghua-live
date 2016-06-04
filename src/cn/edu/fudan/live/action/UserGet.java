package cn.edu.fudan.live.action;


import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

public class UserGet extends ActionSupport {

	private String username;
	// private
	private User user;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private static final long serialVersionUID = 1L;
	private IUserService userService;

	public String execute() {
//		Set params = new HashSet();
//		params.add(username);
//		ValidateService.ValidateNecessaryArguments(params);

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		User uuser = (User) session.getAttribute("user");
		if (uuser == null) {
			error_type = WarnReminderConfiguration.INEXISTENCE_TELEPHONE_CODE;
			error_message = WarnReminderConfiguration.INEXISTENCE_TELEPHONE;
			return SUCCESS;			
		}
//		System.out.println(username);
//		System.out.println(uuser.getUsername());
//		User u = userService.getUserByUsername(username);
		User u = userService.getUserByUsername(uuser.getUsername());
		if (u != null) {
			uuser.setUsername(u.getUsername());
			uuser.setDepartment(u.getDepartment());
			uuser.setEmail(u.getEmail());
		} else {
			error_type = WarnReminderConfiguration.INEXISTENCE_TELEPHONE_CODE;
			error_message = WarnReminderConfiguration.INEXISTENCE_TELEPHONE;
			return SUCCESS;
		}
		User ru = new User();
		ru.setDepartment(u.getDepartment());
		ru.setEmail(u.getEmail());
		ru.setHeadImg(u.getHeadImg());
		ru.setUsername(u.getUsername());
		ru.setType(u.getType());
		ru.setPhone(u.getPhone());
		setUser(ru);

		return SUCCESS;
	}


	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}
	
	public Integer getError_type() {
		return error_type;
	}

	public void setError_type(Integer error_type) {
		this.error_type = error_type;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
