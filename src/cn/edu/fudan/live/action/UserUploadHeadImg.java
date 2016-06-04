package cn.edu.fudan.live.action;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

public class UserUploadHeadImg extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	//input param
	private File image;
	private String imageFileName;
	private String imageContentType;
	
	private String url;
	
	//return value
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;

	private IUserService userService;
	
	@Override
	public String execute() throws Exception {
		
		Set<Object> params = new HashSet<Object>();
		params.add(image);
//		params.add(rurl);
		ValidateService.ValidateNecessaryArguments(params);
		User user = null;
		HttpSession session = ServletActionContext.getRequest().getSession();
		user = (User) session.getAttribute("user");
		
		String realpath = ServletActionContext.getServletContext().getRealPath("/");
//		System.out.println("realpath: "+realpath);
        if(image != null) {
           File savefile = new File(new File(realpath+"/images"), System.currentTimeMillis()+imageFileName);
//	           System.out.println(savefile.getAbsolutePath());
           if (!savefile.getParentFile().exists())
               savefile.getParentFile().mkdirs();
           FileUtils.copyFile(image, savefile);
           user = userService.getUserByUid(user.getUid());
//	           System.out.println("===========================================================");
//	           System.out.println(savefile.getAbsolutePath().replace(realpath, ""));
//	           System.out.println("===========================================================");
           user.setHeadImg(savefile.getAbsolutePath().replace(realpath, ""));
           userService.updateUser(user);
           url = user.getHeadImg();
//           System.out.println("===========================================");
//           System.out.println(url);
//           System.out.println("===========================================");
	     }else{
//	    	 System.out.println("WrongWrongWrongWrongWrongWrongWrongWrongWrongWrong");
//	    	 System.out.println("WrongWrongWrongWrongWrongWrongWrongWrongWrongWrong");
	    	 error_type = WarnReminderConfiguration.SYSTEM_EXCEPTION_CODE;
	    	 error_message = WarnReminderConfiguration.SYSTEM_EXCEPTION;
	     }
	     return SUCCESS;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
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

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
