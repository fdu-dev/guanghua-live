package cn.edu.fudan.live.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IDemooService;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;
import cn.edu.fudan.live.service.ValidateService;

public class DemooBulkDelete extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private List<Integer> didList; // identify specific user

	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private Integer succeedNum = 0;
	private IDemooService demooService;
	private IUserService userService;
	private IVideoService videoService;

	public String execute() {

		Set<Object> params = new HashSet<Object>();
		params.add(didList);
		ValidateService.ValidateNecessaryArguments(params);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		if (user == null) {
			setError_type(WarnReminderConfiguration.INEXISTENCE_CODE);
			setError_message(WarnReminderConfiguration.INEXISTENCE);
			return SUCCESS;
		}

		if (user.getType() == -1) {
			demooService.deleteDemooByDidList(didList);
			return SUCCESS;
		}
		
		demooService.deleteDemooByDidListAndUid(didList,user.getUid());
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

	public List<Integer> getDidList() {
		return didList;
	}

	public void setDidList(List<Integer> didList) {
		this.didList = didList;
	}

	/**
	 * @param error_message
	 *            the error_message to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public Integer getSucceedNum() {
		return succeedNum;
	}

	public void setSucceedNum(Integer succeedNum) {
		this.succeedNum = succeedNum;
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
	
	public void setVideoService(IVideoService videoService) {
		this.videoService = videoService;
	}

}
