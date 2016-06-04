package cn.edu.fudan.live.bean;

import java.util.Date;

public class Comment implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private int cid;
	
	private int vid;
	
	private int uid;
	
	private Date time;
	
	private int commentedId;
	
	private String message;
	
	private String username;
	
	private String headImg;
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getCommentedId() {
		return commentedId;
	}

	public void setCommentedId(int commentId) {
		this.commentedId = commentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

}
