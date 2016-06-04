package cn.edu.fudan.live.action;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * ClassName: Register
 * 
 * @Description: 注册
 * 
 * @author xurisun
 * 
 * @date 2015-4-9
 */
// TODO
public class UserRegister extends ActionSupport {

	private static final long serialVersionUID = 1L;
	// 所需参数
	/**
	 * 用户名
	 */
	private String username = "";
	/**
	 * 用户密码
	 */
	private String password = "";

	private String department = "";
	
	private String email = "";
	
	private String phone = "";

	private int type = 1;

	// 返回值
	/**
	 * 用户对象
	 */
	private User user;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;

	private IUserService userService;

	// private IPermissionService permissionService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() {
		Set params = new HashSet();
		params.add(password);
		params.add(username);
		params.add(email);
		ValidateService.ValidateNecessaryArguments(params);
		
		HttpSession session = ServletActionContext.getRequest().getSession();

		User a = userService.getUserByUsername(username);
		if(a!=null){
			error_type = WarnReminderConfiguration.REPEAT_USERNAME_CODE;
			error_message = WarnReminderConfiguration.REPEAT_USERNAME;
			return SUCCESS;
		}
		a = userService.getUserByEmail(email);
		if(a!=null){
			error_type = WarnReminderConfiguration.REPEAT_EMAIL_CODE;
			error_message = WarnReminderConfiguration.REPEAT_EMAIL;
			return SUCCESS;
		}
		
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setDepartment(department);
		u.setEmail(email);
		u.setPhone(phone);
		u.setType(type);
		userService.addUser(u);
		u = userService.login(u);
		session.setAttribute("user", u);
		User ru = new User();
		ru.setDepartment(u.getDepartment());
		ru.setEmail(u.getEmail());
		ru.setHeadImg(u.getHeadImg());
		ru.setUsername(u.getUsername());
		ru.setType(u.getType());
		setUser(ru);
		return SUCCESS;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setError_type(Integer error_type) {
		this.error_type = error_type;
	}

	public Integer getError_type() {
		return error_type;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getError_message() {
		return error_message;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setDepartment(String department) {
		this.department = department;
	}

}