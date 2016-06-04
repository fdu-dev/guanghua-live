package cn.edu.fudan.live.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;

public class VideoGet extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int vid;
	
	private Video video;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IVideoService videoService;
	private IUserService userService;
	
	public String execute() {
		Video v = videoService.getVideoByVid(vid);
		if (v != null) {
			User user = userService.getUserByUid(v.getUid());
			video = v;
			video.setUsername(user.getUsername());
			video.setHeadImg(user.getHeadImg());
			video.setCoverImg(v.getCoverImg());
			video.setUid(0);
			video.setDepartment(user.getDepartment());
		} else {
			error_type = 1002;
			error_message = "没有该vid的视频存在";
			return SUCCESS;
		}
		return SUCCESS;
	}


	public void setVideoService(IVideoService videoService) {
		this.videoService = videoService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Video getVideo() {
		return this.video;
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

	public void setVid(int vid) {
		this.vid = vid;
	}
	
}
