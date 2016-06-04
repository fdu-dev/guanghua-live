package cn.edu.fudan.live.service;

import java.util.List;

import cn.edu.fudan.live.bean.Video;

public interface IVideoService {

	public Video getVideoByVid(int vid);

	public List<Video> getVideoList(int start, int limit);
	
	public List<Video> getLiveList(int start, int limit);

	public int getVideoNumber();

	public int getLiveNumber();

	List<Video> getReadyLiveListBySql(int start, int limit);
	
	public void addVideo(Video video);
	
	public void updateVideo(Video video);
	
	public void deleteVideoByVidList(List<Integer> vidList);

	public Video getLiveByUsernameAndKeyAndType(String username, String key, int type);

	public Video getLiveByUsernameAndType(String username, int type);

	public List<Video> getVideoByUsername(String username);

}
