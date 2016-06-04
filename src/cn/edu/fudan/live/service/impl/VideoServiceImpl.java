package cn.edu.fudan.live.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import cn.edu.fudan.anniversary.dao.EntityDAO;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IVideoService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class VideoServiceImpl implements IVideoService{
	private EntityDAO entityDAO;

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}


	@Override
	public Video getVideoByVid(int vid) {
		// TODO Auto-generated method stub
		Video video = (Video) entityDAO.get(Video.class, vid);
		return video;
	}


	@Override
	public List<Video> getVideoList(int start, int limit) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("type",1));
		List<Video> videoList = entityDAO.findByCriteria(Video.class, "formatTime", false, start, limit, criterions);
		return videoList;
	}


	@Override
	public List<Video> getLiveList(int start, int limit) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("type",2));
		List<Video> videoList = entityDAO.findByCriteria(Video.class, "formatTime", false, start, limit, criterions);
		return videoList;
	}

	@Override
	public List<Video> getReadyLiveListBySql(int start, int limit){
		String sql = "select v.vid,v.title,v.url,v.description,v.time,v.format_time,u.username,u.headImg,v.cover_img,v.type,u.department "
				+"from video v inner join user u on v.uid=u.uid "
				+"where v.type=1 "
				+"order by v.time desc "
				+"limit "+start+","+limit+";";
		List<Map> list = entityDAO.findBySql(sql);
		List<Video> videos = new ArrayList<Video>();
		for(Map map: list){
			Video v = new Video();
			v.setCoverImg((String) map.get("cover_img"));
			v.setDescription((String) map.get("description"));
			v.setFormatTime((Date) map.get("format_time"));
			v.setHeadImg((String) map.get("headImg"));
			v.setTime(((BigInteger) map.get("time")).longValue());
			v.setTitle((String) map.get("title"));
			v.setType((int) map.get("type"));
			v.setUrl((String) map.get("url"));
			v.setUsername((String) map.get("username"));
			v.setVid((int) map.get("vid"));
			v.setDepartment((String) map.get("department"));
			videos.add(v);
		}
		return videos;
	}

	@Override
	public int getVideoNumber() {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("type",1));		
		return entityDAO.findCountsByCriteria(Video.class, criterions);
	}


	@Override
	public int getLiveNumber() {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("type",2));		
		return entityDAO.findCountsByCriteria(Video.class, criterions);
	}


	@Override
	public void addVideo(Video video) {
		// TODO Auto-generated method stub
		entityDAO.save(video);
		
	}
	

	@Override
	public void updateVideo(Video video) {
		// TODO Auto-generated method stub
		entityDAO.update(video);
	}


	@Override
	public Video getLiveByUsernameAndKeyAndType(String username, String key, int type) {
		// TODO Auto-generated method stub
		String sql = "select v.* "
				+"from video v inner join user u inner join live_key lk on v.uid=u.uid "
				+"where u.username = '" + username + "' and lk.live_key = '" + key + "' and v.type = " + type;
		List<Map> list = entityDAO.findBySql(sql);
		if (list.size() == 0) return null;
		Video v = new Video();
		Map map = list.get(0);
		v.setCoverImg((String) map.get("cover_img"));
		v.setDescription((String) map.get("description"));
		v.setFormatTime((Date) map.get("format_time"));
		v.setTime(((BigInteger) map.get("time")).longValue());
		v.setTitle((String) map.get("title"));
		v.setType((int) map.get("type"));
		v.setUrl((String) map.get("url"));
		v.setVid((int) map.get("vid"));
		v.setUid((int)map.get("uid"));
		return v;	
	}


	@Override
	public Video getLiveByUsernameAndType(String username, int type) {
		// TODO Auto-generated method stub
		String sql = "select v.* "
				+"from video v inner join user u on v.uid=u.uid "
				+"where u.username = '" + username + "'  and v.type = " + type;
		List<Map> list = entityDAO.findBySql(sql);
		if (list.size() == 0) return null;
		Video v = new Video();
		Map map = list.get(0);
		v.setCoverImg((String) map.get("cover_img"));
		v.setDescription((String) map.get("description"));
		v.setFormatTime((Date) map.get("format_time"));
		v.setTime(((BigInteger) map.get("time")).longValue());
		v.setTitle((String) map.get("title"));
		v.setType((int) map.get("type"));
		v.setUrl((String) map.get("url"));
		v.setVid((int) map.get("vid"));
		v.setUid((int)map.get("uid"));
		return v;
	}
	
	@Override
	public List<Video> getVideoByUsername(String username) {
		String sql = "select v.* "
				+"from video v inner join user u on v.uid=u.uid "
				+"where u.username = '" + username + "'  and v.type = " + 1;
		List<Map> list = entityDAO.findBySql(sql);
		
		List<Video> videoList = new ArrayList<Video>();
		
		for (int i = 0; i < list.size(); i++) {
			Video v = new Video();
			Map map = list.get(0);
			v.setCoverImg((String) map.get("cover_img"));
			v.setDescription((String) map.get("description"));
			v.setFormatTime((Date) map.get("format_time"));
			v.setTime(((BigInteger) map.get("time")).longValue());
			v.setTitle((String) map.get("title"));
			v.setType((int) map.get("type"));
			v.setUrl((String) map.get("url"));
			v.setVid((int) map.get("vid"));
			v.setUid((int)map.get("uid"));
			videoList.add(v);
		}

		return videoList;
		
	}


	@Override
	public void deleteVideoByVidList(List<Integer> vidList) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < vidList.size(); i++) {
			disjunction.add(Restrictions.eq("vid", vidList.get(i)));
		}
		criterions.add(disjunction);
		entityDAO.deleteByCriteria(Video.class, criterions);

	}

}
