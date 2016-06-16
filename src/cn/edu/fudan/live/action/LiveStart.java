package cn.edu.fudan.live.action;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.RandomUtil;
import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.LiveKey;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.ILiveKeyService;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.IVideoService;
import cn.edu.fudan.live.service.ValidateService;

public class LiveStart extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private String title; // identify specific user
	private File coverImg;
	private String description;

	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	
	private String key;
	
	private IVideoService videoService;
	private IUserService userService;
	private ILiveKeyService liveKeyService;

	public String execute() throws Exception{

		Set<Object> params = new HashSet<Object>();
		params.add(title);
		ValidateService.ValidateNecessaryArguments(params);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");
		System.out.println("[LiveStart]");
		if (user == null) {
			setError_type(101);
			setError_message("请先登录！");
			return SUCCESS;
		}
		
		if (user.getType() != 2) {
			setError_type(103);
			setError_message("您暂时没有开通直播的权限");
			return SUCCESS;
		}
        Video vv = videoService.getLiveByUsernameAndType(user.getUsername(), 2);
		if (vv != null) {
			setError_type(104);
			setError_message("您已开通直播");
			return SUCCESS;
		}
		Video video = new Video();
		video.setTitle(title);
		video.setDescription(description);

		String realpath = ServletActionContext.getServletContext().getRealPath("/");		
        if(coverImg != null) {
            File savefile = new File(new File(realpath+"/videoCoverImg"), RandomUtil.generateUUID() + ".jpg");
// 	           System.out.println(savefile.getAbsolutePath());
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            FileUtils.copyFile(coverImg, savefile);
// 	           System.out.println("===========================================================");
// 	           System.out.println(savefile.getAbsolutePath().replace(realpath, ""));
// 	           System.out.println("===========================================================");
            video.setCoverImg(savefile.getAbsolutePath().replace(realpath, ""));
//            System.out.println("===========================================");
//            System.out.println(url);
//            System.out.println("===========================================");
 	    }
        else {
        	video.setCoverImg(realpath+"/videoCoverImg/default.jepg");
        }
		
        video.setUid(user.getUid());
        video.setType(3);

        Video v = videoService.getLiveByUsernameAndType(user.getUsername(), 3);
        
        if (v != null) {
        	v.setTitle(video.getTitle());
        	v.setDescription(video.getDescription());
        	v.setHeadImg(video.getHeadImg());
        	videoService.updateVideo(v);
        }
        else {
        	videoService.addVideo(video);
        }
        
        LiveKey liveKey = liveKeyService.getLiveKeyByUid(user.getUid());
        if (liveKey != null) {
            liveKey.setLiveKey(RandomUtil.generateUUID());
            liveKeyService.updateLiveKey(liveKey);
        }
        else {
        	liveKey = new LiveKey();
            liveKey.setLiveKey(RandomUtil.generateUUID());
            liveKey.setUid(user.getUid());
            liveKey.setVid(video.getVid());        	
            liveKeyService.addLiveKey(liveKey);
        }        
        setKey(liveKey.getLiveKey());
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

	public ILiveKeyService getLiveKeyService() {
		return liveKeyService;
	}

	public void setLiveKeyService(ILiveKeyService liveKeyService) {
		this.liveKeyService = liveKeyService;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public File getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(File coverImg) {
		this.coverImg = coverImg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setVideoService(IVideoService videoService) {
		this.videoService = videoService;
	}
	
	
}
