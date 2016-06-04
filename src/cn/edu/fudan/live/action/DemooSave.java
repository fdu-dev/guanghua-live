package cn.edu.fudan.live.action;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IDemooService;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

public class DemooSave extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private int vid; // identify specific user
	private long time;
	private String message;

	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IDemooService demooService;
	private IUserService userService;

	public String execute() {

		Set<Object> params = new HashSet<Object>();
		params.add(vid);
		params.add(time);
		params.add(message);
		ValidateService.ValidateNecessaryArguments(params);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user == null) {
			setError_type(101);
			setError_message("请先登录！");
			return SUCCESS;
		}
		Demoo demoo = new Demoo();
		demoo.setMessage(message);
		demoo.setTime(time);
		demoo.setUid(user.getUid());
		demoo.setVid(vid);
		demooService.addDemoo(demoo);
		return SUCCESS;
	}

	/**
	 * @return the phone
	 */


	/**
	 * @return the error_type
	 */
	public Integer getError_type() {
		return error_type;
	}

	/**
	 * @return the error_message
	 */
	public String getError_message() {
		return error_message;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */


	/**
	 * @param error_type
	 *            the error_type to set
	 */
	public void setError_type(Integer error_type) {
		this.error_type = error_type;
	}

	/**
	 * @param error_message
	 *            the error_message to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	/**
	 * @return the userService
	 */
	public IUserService getUserService() {
		return userService;
	}

	public IDemooService getDemooService() {
		return demooService;
	}
	
	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setDemooService(IDemooService demooService) {
		this.demooService = demooService;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
