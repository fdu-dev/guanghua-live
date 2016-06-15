package cn.edu.fudan.live.action;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.exception.DataNotFoundException;
import cn.edu.fudan.anniversary.exception.IllegalArgumentsException;
import cn.edu.fudan.anniversary.util.RandomUtil;
import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.LiveKey;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.ILiveKeyService;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;
import cn.edu.fudan.live.service.ValidateService;

public class LiveOnDvr extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private String app; // identify specific user
	private String stream;
	private String file;

	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	
	private IVideoService videoService;
	private IUserService userService;
	private ILiveKeyService liveKeyService;

	public String execute() throws Exception{

		Set<Object> params = new HashSet<Object>();
		params.add(app);
		params.add(stream);
		ValidateService.ValidateNecessaryArguments(params);
		Video video = videoService.getLiveByUsernameAndKeyAndType(app, stream, 4);
		if (video != null) {
			video.setType(1);
			String filename = file.substring(file.lastIndexOf("/")+1, file.length());
//			video.setUrl(WarnReminderConfiguration.HTTP_URL + app + "/" + stream + ".flv");
			video.setUrl(WarnReminderConfiguration.HTTP_URL + app + "/" + filename);
			videoService.updateVideo(video);
			
			return SUCCESS;
		}
		else {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setStatus(500);
			
			return SUCCESS;
		}
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

	public IVideoService getVideoService() {
		return videoService;
	}
	
	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setVideoService(IVideoService videoService) {
		this.videoService = videoService;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public ILiveKeyService getLiveKeyService() {
		return liveKeyService;
	}

	public void setLiveKeyService(ILiveKeyService liveKeyService) {
		this.liveKeyService = liveKeyService;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public static void main(String[] args) {
		
		String tmp = "./objs/nginx/html/user1-xxc/key-3qe23982309423i423-fdjlkd-14202540687.flv";
		System.out.println(tmp.substring(tmp.lastIndexOf("/"), tmp.length()));
		
	}
	
}
