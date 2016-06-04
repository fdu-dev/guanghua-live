package cn.edu.fudan.live.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Order;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import cn.edu.fudan.anniversary.dao.EntityDAO;
import cn.edu.fudan.anniversary.exception.RepeatTelephoneException;
import cn.edu.fudan.live.bean.Comment;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.ICommentService;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CommentServiceImpl implements ICommentService {
	private EntityDAO entityDAO;

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub
		if (!entityDAO.isPropertyExist(Video.class, "vid",
				comment.getVid())) {
			throw new RepeatTelephoneException();
		}
		if ((!entityDAO.isPropertyExist(Comment.class, "cid",
				comment.getCommentedId())) && (comment.getCommentedId() != 0)) {
			throw new RepeatTelephoneException();
		}
		entityDAO.save(comment);
	}
	
	@Override
	public List<Comment> getCommentList(int vid, int commentedId) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("vid",vid));
		criterions.add(Restrictions.eq("commentedId", commentedId));
		List<Comment> commentList = entityDAO.findAll(Comment.class, "time", false, criterions);
		return commentList;
	}
	
	@Override
	public List<Comment> getReadyCommentListByStartLimit(int vid, int commentedId, int start, int limit){
		String sql = "select c.cid,c.vid,c.uid,c.time,c.commented_id,c.message,u.username,u.headImg "
				+"from comment c join user u on c.uid=u.uid "
				+"where c.vid="+vid+" AND c.commented_id="+commentedId+" "
				+"order by c.time desc "
				+"limit "+start+","+limit+";";
		List<Map> list = entityDAO.findBySql(sql);
		List<Comment> comments = new ArrayList<Comment>();
		for(Map map:list){
			Comment c = new Comment();
			c.setCid((int) map.get("cid"));
			c.setCommentedId((int) map.get("commented_id"));
			c.setHeadImg((String) map.get("headImg"));
			c.setMessage((String) map.get("message"));
			c.setTime((Date) map.get("time"));
			c.setUid((int) map.get("uid"));
			c.setUsername((String) map.get("username"));
			c.setVid((int) map.get("vid"));
			comments.add(c);
		}
		return comments;
	}
	
	@Override
	public Comment getCommentByCid(int cid) {
		// TODO Auto-generated method stub
		Comment comment = (Comment) entityDAO.get(Comment.class, cid);
		return comment;
	}
	
	@Override
	public void deleteCommentByCidList(List<Integer> cidList) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < cidList.size(); i++) {
			disjunction.add(Restrictions.eq("cid", cidList.get(i)));
			disjunction.add(Restrictions.eq("commentedId", cidList.get(i)));
		}
		criterions.add(disjunction);
		entityDAO.deleteByCriteria(Comment.class, criterions);
	}
	
	@Override
	public int getCommentTotalNumber(int vid, int commentedId) {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("vid", vid);
		properties.put("commentedId", commentedId);
		return entityDAO.countByCriteria(Comment.class, properties).intValue();
	}
	@Override
	public void deleteCommentByCidListAndUid(List<Integer> cidList, int uid) {
		// TODO Auto-generated method stub
		if (cidList.size() == 0) {
			return;
		}
		String sql = "select c.* "
				+"from comment c join video v on v.vid=c.vid "
				+"where v.uid="+uid+" AND (";
		for (int i = 0; i < cidList.size() - 1; i++) {
			sql = sql + "c.cid=" + cidList.get(i) + " or c.commented_id=" + cidList.get(i) + " or ";
		}
		sql = sql + "c.cid=" + cidList.get(cidList.size() - 1) + " or c.commented_id=" + cidList.get(cidList.size() - 1) + ")";
		List<Map> list = entityDAO.findBySql(sql);
		for(Map map:list){
			Comment c = new Comment();
			c.setCid((int) map.get("cid"));
			c.setCommentedId((int) map.get("commented_id"));
			c.setMessage((String) map.get("message"));
			c.setTime((Date) map.get("time"));
			c.setUid((int) map.get("uid"));
			c.setVid((int) map.get("vid"));
			entityDAO.delete(c);
		}

	}

}
