package cn.edu.fudan.live.action;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

public class UserUpdate extends ActionSupport {

	private static final long serialVersionUID = 1L;

	// 请求所需参数
	/**
	 * 旧密码
	 */
	
	private String oldPassword = "";
	/**
	 * 新密码
	 */
	private String newPassword = "";
	
	// 返回值
	private User user;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;

	private IUserService userService;

	@Override
	public String execute() {

		Set<Object> params = new HashSet<Object>();
		params.add(oldPassword);
		params.add(newPassword);
		ValidateService.ValidateNecessaryArguments(params);
		User user = null;
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");

		if (user == null) {
			setError_type(WarnReminderConfiguration.INEXISTENCE_CODE);
			setError_message("尚未登录");
			return SUCCESS;
		}
		
		user = userService.getUserByUid(user.getUid());
		if (user.getPassword().equals(oldPassword)) {
			user.setPassword(newPassword);
			userService.updateUser(user);
		} else {
			error_type = WarnReminderConfiguration.VALIDATE_OLD_PASSWORD_WRONG_CODE;
			error_message = WarnReminderConfiguration.VALIDATE_OLD_PASSWORD_WRONG;
			return SUCCESS;
		}
		User ru = new User();
		ru.setDepartment(user.getDepartment());
		ru.setEmail(user.getEmail());
		ru.setHeadImg(user.getHeadImg());
		ru.setUsername(user.getUsername());
		ru.setType(user.getType());
		setUser(ru);
		
		return SUCCESS;
		
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public Integer getError_type() {
		return error_type;
	}

	public void setError_type(Integer error_type) {
		this.error_type = error_type;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
