package cn.edu.fudan.live.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.RandomUtil;
import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;

public class VideoUpdate extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int vid;
	private File coverImg;
	private String title;
	private String description;
	
	private Video video;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IVideoService videoService;
	private IUserService userService;
	
	public String execute() throws IOException {
		Video v = videoService.getVideoByVid(vid);

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			setError_type(101);
			setError_message("请先登录！");
			return SUCCESS;
		}
		
		
		if (v != null) {
			if (user.getType() == -1 || user.getUid() == v.getUid()) {
				User u = userService.getUserByUid(v.getUid());
				if (title != null && !title.isEmpty()) {
					v.setTitle(title);
				}
				if (description != null && !description.isEmpty()) {
					v.setDescription(description);
				}
				String realpath = ServletActionContext.getServletContext().getRealPath("/");		
		        if(coverImg != null) {
		            File savefile = new File(new File(realpath+"/videoCoverImg"), RandomUtil.generateUUID() + ".jpg");
		            if (!savefile.getParentFile().exists())
		                savefile.getParentFile().mkdirs();
		            FileUtils.copyFile(coverImg, savefile);
		            v.setCoverImg(savefile.getAbsolutePath().replace(realpath, ""));
		        }
				videoService.updateVideo(v);
				video = v;
				video.setUsername(u.getUsername());
				video.setHeadImg(u.getHeadImg());
				video.setCoverImg(v.getCoverImg());
				video.setUid(0);
				video.setDepartment(u.getDepartment());				
			}
			else {
				setError_type(WarnReminderConfiguration.INEXISTENCE_CODE);
				setError_message("您的权限不够");
				return SUCCESS;
			}
		} else {
			error_type = 1002;
			error_message = "没有该vid的视频存在";
			return SUCCESS;
		}
		return SUCCESS;
	}


	public void setCoverImg(File coverImg) {
		this.coverImg = coverImg;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setDescription(String description) {
		this.description = description;
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
