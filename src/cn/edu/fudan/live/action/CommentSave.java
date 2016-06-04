package cn.edu.fudan.live.action;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;
import cn.edu.fudan.live.bean.Comment;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.service.ICommentService;
import cn.edu.fudan.live.service.IUserService;
import cn.edu.fudan.live.service.ValidateService;

public class CommentSave extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private int vid; // identify specific user
	private int commentedId;
	private String message;

	
	private Comment comment;
	private Integer error_type = WarnReminderConfiguration.SUCCESS_CODE;
	private String error_message = WarnReminderConfiguration.SUCCESS;
	private ICommentService commentService;
	private IUserService userService;

	public String execute() {

		Set<Object> params = new HashSet<Object>();
		params.add(vid);
		params.add(message);
		ValidateService.ValidateNecessaryArguments(params);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("user");

		if (user == null) {
			setError_type(WarnReminderConfiguration.INEXISTENCE_CODE);
			setError_message("尚未登录");
			return SUCCESS;
		}
		Comment comment = new Comment();
		comment.setCommentedId(commentedId);
		comment.setMessage(message);
		comment.setTime(new Date());
		comment.setVid(vid);
		comment.setUid(user.getUid());
		comment.setHeadImg(user.getHeadImg());
		comment.setUsername(user.getUsername());
		commentService.addComment(comment);
		setComment(comment);
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
	
	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setCommentService(ICommentService commentService) {
		this.commentService = commentService;
	}


	public void setVid(int vid) {
		this.vid = vid;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void setCommentedId(int commentedId) {
		this.commentedId = commentedId;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
