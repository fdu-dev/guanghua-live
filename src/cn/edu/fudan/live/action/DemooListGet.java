package cn.edu.fudan.live.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.IDemooService;
import cn.edu.fudan.live.service.IUserService;

public class DemooListGet extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int vid;
	private int max;
	
	private List<Demoo> demooList;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private IDemooService demooService;
	private IUserService userService;
	
	public String execute() {
		List<Demoo> dl;
		if (max <= 0) {
			dl = demooService.getDemooList(vid);			
		}
		else {
			dl = demooService.getDemooList(vid);
		}
//		demooList = new ArrayList<Demoo>();
		demooList = dl; 

		if (dl.size() != 0) {
//			for (int i = 0; i < dl.size(); i++) {
//				Demoo demoo = dl.get(i);
//				User user = userService.getUserByUid(demoo.getUid());
//				demoo.setUsername(user.getUsername());
//				demoo.setHeadImg(user.getHeadImg());
//				demoo.setUid(0);
//				demooList.add(demoo);
//			}
		} else {
			error_type = WarnReminderConfiguration.INEXISTENCE_TELEPHONE_CODE;
			error_message = WarnReminderConfiguration.INEXISTENCE_TELEPHONE;
			return SUCCESS;
		}

		return SUCCESS;
	}


	public void setDemooService(IDemooService videoService) {
		this.demooService = videoService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setDemooList(List<Demoo> demooList) {
		this.demooList = demooList;
	}
	
	public List<Demoo> getDemooList() {
		return this.demooList;
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
	
	public void setMax(int max) {
		this.max = max;
	}
	

}
