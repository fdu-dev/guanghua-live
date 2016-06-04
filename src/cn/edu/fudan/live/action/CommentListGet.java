package cn.edu.fudan.live.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.Comment;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.ICommentService;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

public class CommentListGet extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private int vid;
	private int commentedId;
	private int start;
	private int limit;
	
	private List<Comment> commentList;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private ICommentService commentService;
	private IUserService userService;
	
	private int total;
	
	public String execute() {

		Set<Object> params = new HashSet<Object>();
		params.add(vid);
		params.add(commentedId);
		params.add(start);
		params.add(limit);
		ValidateService.ValidateNecessaryArguments(params);
		
//		List<Comment> cl = commentService.getCommentList(vid, commentedId);
//		commentList = new ArrayList<Comment>();
		commentList = commentService.getReadyCommentListByStartLimit(vid, commentedId, start, limit);
		total = commentService.getCommentTotalNumber(vid, commentedId);

		return SUCCESS;
	}


	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}
	
	public List<Comment> getCommentList() {
		return this.commentList;
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
	
	public void setCommentedId(int commentedId) {
		this.commentedId = commentedId;
	}


	public int getStart() {
		return start;
	}


	public void setStart(int start) {
		this.start = start;
	}


	public int getLimit() {
		return limit;
	}


	public void setLimit(int limit) {
		this.limit = limit;
	}


	public int getTotal() {
		return total;
	}

}
