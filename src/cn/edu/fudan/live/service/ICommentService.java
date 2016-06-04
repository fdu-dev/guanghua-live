package cn.edu.fudan.live.service;

import java.util.List;

import cn.edu.fudan.live.bean.Comment;

public interface ICommentService {

	public void addComment(Comment comment);

	public List<Comment> getCommentList(int vid, int commentedId);

	public Comment getCommentByCid(int cid);

	public void deleteCommentByCidList(List<Integer> cidList);
	
	public void deleteCommentByCidListAndUid(List<Integer> cidList, int uid);
	
	public List<Comment> getReadyCommentListByStartLimit(int vid, int commentedId, int start, int limit);
	
	public int getCommentTotalNumber(int vid, int commentedId);

}
