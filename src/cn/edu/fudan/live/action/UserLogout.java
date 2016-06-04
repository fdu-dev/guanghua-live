package cn.edu.fudan.live.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;

/**
 * 
 * ClassName: Login
 * 
 * @Description: 登录
 * 
 * @author xurisun
 * 
 * @date 2015-4-8
 */
public class UserLogout extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private int error_type;
	private String error_message;

	public String execute() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user != null) {
			session.removeAttribute("user");
			return SUCCESS;
		}
		// session池，全局变量，包含每个登录用户的sesssion

		return SUCCESS;
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

}
