package cn.edu.fudan.live.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

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
public class UserLogin extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	//

	private User user;
	private IUserService userService;
	
	private int error_type;
	private String error_message;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		Set params = new HashSet();
		params.add(username);
		params.add(password);
		ValidateService.ValidateNecessaryArguments(params);

		HttpServletRequest request = ServletActionContext.getRequest();
		
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();

		// session池，全局变量，包含每个登录用户的sesssion
		Map<Integer, HttpSession> sessionPool = (Map<Integer, HttpSession>) application
				.getAttribute("sessionPool");

		// 用户别名池，全局变量，包含每个登录用户的别名
		Map<Integer, String> userAliasPool = (Map<Integer, String>) application
				.getAttribute("userAliasPool");

		// 如果为空，则新建
		if (sessionPool == null) {
			sessionPool = new HashMap<Integer, HttpSession>();
		}
		if (userAliasPool == null) {
			userAliasPool = new HashMap<Integer, String>();
		}

		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u = userService.login(u);
		if (u != null) {
			user = u;
		} else {
			error_type = WarnReminderConfiguration.INEXISTENCE_TELEPHONE_CODE;
			error_message = WarnReminderConfiguration.INEXISTENCE_TELEPHONE;
			return SUCCESS;
		}

		String device_token = "";
		if (session.getAttribute("device_token") != null) {
			device_token = (String) session.getAttribute("device_token");
		}
		User ru = new User();
		ru.setDepartment(u.getDepartment());
		ru.setEmail(u.getEmail());
		ru.setHeadImg(u.getHeadImg());
		ru.setUsername(u.getUsername());
		ru.setType(u.getType());
		ru.setPhone(u.getPhone());
		setUser(ru);

		Integer userID = u.getUid();

		if (sessionPool.containsKey(userID)) {
			HttpSession se = sessionPool.get(userID);
			if (se != null && !se.getId().equals(session.getId())) {

				// 移除全局变量池中原先登录用户的信息
				sessionPool.remove(userID);
				userAliasPool.remove(userID);
			}
		}

		session.setAttribute("user", u);
		session.setAttribute("device_token", device_token);
		sessionPool.put(userID, session);
		application.setAttribute("sessionPool", sessionPool);
		application.setAttribute("userAliasPool", userAliasPool);

		return SUCCESS;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

}
