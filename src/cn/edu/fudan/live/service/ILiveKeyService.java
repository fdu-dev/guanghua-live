package cn.edu.fudan.live.service;


import cn.edu.fudan.live.bean.LiveKey;

public interface ILiveKeyService {
	
	public void addLiveKey(LiveKey liveKey);
	
	public void deleteLiveKey(LiveKey liveKey);
	
	public LiveKey get(int id);

	public LiveKey getLiveKeyByUid(int uid);
	
	public void updateLiveKey(LiveKey liveKey);

	public LiveKey getLiveKeyByUsername(String app);

}
