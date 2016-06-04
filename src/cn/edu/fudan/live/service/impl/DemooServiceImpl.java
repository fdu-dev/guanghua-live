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
import cn.edu.fudan.anniversary.exception.RepeatTelephoneException;
import cn.edu.fudan.live.bean.Comment;
import cn.edu.fudan.live.bean.Demoo;
import cn.edu.fudan.live.bean.Video;
import cn.edu.fudan.live.service.IDemooService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DemooServiceImpl implements IDemooService {
	private EntityDAO entityDAO;

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
	
	
	@Override
	public void addDemoo(Demoo demoo) {
		// TODO Auto-generated method stub
		if (!entityDAO.isPropertyExist(Video.class, "vid",
				demoo.getVid())) {
			throw new RepeatTelephoneException();
		}
		entityDAO.save(demoo);
	}
	
	@Override
	public List<Demoo> getDemooList(int vid) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("vid",vid));
		List<Demoo> demooList = entityDAO.findAll(Demoo.class, "time", false, criterions);
		return demooList;
	}

	@Override
	public Demoo getDemooByDid(int did) {
		// TODO Auto-generated method stub
		Demoo demoo = (Demoo) entityDAO.get(Demoo.class, did);
		return demoo;
	}


	@Override
	public void deleteDemooByDidList(List<Integer> didList) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		Disjunction disjunction = Restrictions.disjunction();
		for (int i = 0; i < didList.size(); i++) {
			disjunction.add(Restrictions.eq("did", didList.get(i)));
		}
		criterions.add(disjunction);
		entityDAO.deleteByCriteria(Demoo.class, criterions);
		
	}


	@Override
	public void deleteDemooByDidListAndUid(List<Integer> didList, int uid) {
		// TODO Auto-generated method stub
		if (didList.size() == 0) {
			return;
		}
		String sql = "select d.* "
				+"from demoo d join video v on v.vid=d.vid "
				+"where v.uid="+uid+" AND (";
		for (int i = 0; i < didList.size() - 1; i++) {
			sql = sql + "d.did=" + didList.get(i) + " or ";
		}
		sql = sql + "d.did=" + didList.get(didList.size() - 1) +  ")";
		List<Map> list = entityDAO.findBySql(sql);
		for(Map map:list){
			Demoo d = new Demoo();
			d.setDid((int)map.get("did"));
			d.setMessage((String) map.get("message"));
			d.setTime(((BigInteger)map.get("time")).longValue());
			d.setUid((int) map.get("uid"));
			d.setVid((int) map.get("vid"));
			entityDAO.delete(d);
		}
		
	}


	@Override
	public List<Demoo> getDemooList(int vid, int max) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("vid",vid));
		List<Demoo> demooList = entityDAO.findByCriteria(Demoo.class, "time", false, 0, 500, criterions);
		return demooList;
	}


}
