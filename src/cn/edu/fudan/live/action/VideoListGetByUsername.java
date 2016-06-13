package cn.edu.fudan.live.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;
import cn.edu.fudan.live.service.ValidateService;

public class VideoListGetByUsername extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	
	private List<Video> videoList;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IVideoService videoService;
	private IUserService userService;

	@SuppressWarnings({ "unchecked", "rawtypes" })	
	public String execute() {
		Set params = new HashSet();
		params.add(username);
		ValidateService.ValidateNecessaryArguments(params);

		User user = null;
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		
		if (user != null) {
			if (user.getType() == -1) {
				videoList = videoService.getVideoList(0, 0);
			}
		}
		if (videoList == null) {
			videoList = videoService.getVideoByUsername(username);			
		}
		return SUCCESS;
	}


	public void setVideoService(IVideoService videoService) {
		this.videoService = videoService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
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


	public List<Video> getVideoList() {
		return videoList;
	}

	
}
