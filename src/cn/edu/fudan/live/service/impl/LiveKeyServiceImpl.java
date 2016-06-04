package cn.edu.fudan.live.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import cn.edu.fudan.anniversary.dao.EntityDAO;
import cn.edu.fudan.live.bean.LiveKey;
import cn.edu.fudan.live.service.ILiveKeyService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LiveKeyServiceImpl implements ILiveKeyService{
	private EntityDAO entityDAO;

	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@Override
	public void addLiveKey(LiveKey liveKey) {
		// TODO Auto-generated method stub
//		if (entityDAO.isPropertyExist(LiveKey.class, "uid",
//				liveKey.getUid())) {
//			throw new RepeatTelephoneException();
//		}
		entityDAO.save(liveKey);
	}

	@Override
	public void deleteLiveKey(LiveKey liveKey) {
		// TODO Auto-generated method stub
		entityDAO.delete(liveKey);
	}
	
	@Override
	public LiveKey get(int id) {
		return (LiveKey)entityDAO.get(LiveKey.class, id);
	}

	@Override
	public LiveKey getLiveKeyByUid(int uid) {
		// TODO Auto-generated method stub
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(Restrictions.eq("uid",uid));
		List<LiveKey> liveKeyList = entityDAO.findAll(LiveKey.class, null, true, criterions);
		if (liveKeyList.size() != 0) {
			return liveKeyList.get(0);
		}
		else return null;
	}

	@Override
	public void updateLiveKey(LiveKey liveKey) {
		// TODO Auto-generated method stub
		entityDAO.update(liveKey);
	}
	
}
