package cn.edu.fudan.live.bean;

public class LiveKey implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	
	private int uid;
	
	private int vid;
	
	private String liveKey;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getVid() {
		return vid;
	}

	public void setVid(int vid) {
		this.vid = vid;
	}

	public String getLiveKey() {
		return liveKey;
	}

	public void setLiveKey(String liveKey) {
		this.liveKey = liveKey;
	}


}
