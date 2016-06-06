package cn.edu.fudan.anniversary.util.log;

public interface ILogEngine {
	//记录
	public void log(String info);
	
	public void debug(String debug);
	
	public void info(String info);
	
	public void warn(String warn);
	
	public void error(String error);
}
