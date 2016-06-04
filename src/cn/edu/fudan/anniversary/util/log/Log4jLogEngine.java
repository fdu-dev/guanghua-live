package cn.edu.fudan.anniversary.util.log;

import org.apache.log4j.Logger;

public class Log4jLogEngine implements ILogEngine {
	public static Logger requestLogger = Logger.getLogger("requestLogger");
	public static Logger errorLogger = Logger.getLogger("errorLogger");
	public void log(String info) {
		logRequest(info);		
	}
	
	public void logRequest(String info){
		requestLogger.info(info);
	}

	public void debug(String debug) {
		// TODO Auto-generated method stub
		
	}

	public void info(String info) {
		// TODO Auto-generated method stub
		
	}

	public void warn(String warn) {
		// TODO Auto-generated method stub
		
	}

	public void error(String error) {
		errorLogger.info(error);
	}

}
