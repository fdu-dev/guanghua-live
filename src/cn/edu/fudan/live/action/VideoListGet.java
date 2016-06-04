package cn.edu.fudan.live.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;

public class VideoListGet extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int start;
	private int limit;
	
	private List<Video> videoList;
	private int total;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IVideoService videoService;
	private IUserService userService;
	
	public String execute() {

//		List<Video> vl = videoService.getVideoList(start, limit);
//		videoList = new ArrayList<Video>();
		videoList = videoService.getReadyLiveListBySql(start, limit);
		if (videoList.size() != 0) {
//			for (int i = 0; i < vl.size(); i++) {
//				Video video = vl.get(i);
//				User user = userService.getUserByUid(video.getUid());
//				video.setUsername(user.getUsername());
//				video.setHeadImg(user.getHeadImg());
//				video.setUid(0);
//				video.setDepartment(user.getDepartment());				
//				videoList.add(video);
//			}
		} else {
			error_type = 1001;
			error_message = "无更多数据";
			return SUCCESS;
		}
		total = videoService.getVideoNumber();
		return SUCCESS;
	}


	public void setVideoService(IVideoService videoService) {
		this.videoService = videoService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public void setVideoList(List<Video> videoList) {
		this.videoList = videoList;
	}
	
	public List<Video> getVideoList() {
		return this.videoList;
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

	public void setStart(int start) {
		this.start = start;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}
}
