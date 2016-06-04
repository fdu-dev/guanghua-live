package cn.edu.fudan.live.action;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;
import cn.edu.fudan.live.util.MailUtil;

public class RetrivePassword extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private String email;
	
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IUserService userService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String execute() {
		Set params = new HashSet();
		params.add(email);
		ValidateService.ValidateNecessaryArguments(params);
		
		User user = userService.getUserByEmail(email);
		if(user==null){
			error_type = WarnReminderConfiguration.ERROR_CODE;
			error_message = "该邮箱不存在！";
		}else{
			String url = MailUtil.generateUrl4User(user);
			String username = user.getUsername();
			MailUtil.sendModifyPswdMail(email, username, url);
		}
		return SUCCESS;
	}

	public Integer getError_type() {
		return error_type;
	}

	public String getError_message() {
		return error_message;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
}
