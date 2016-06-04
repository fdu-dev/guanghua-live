package cn.edu.fudan.anniversary.util.log;

public class LogFactory {
	private static ILogEngine log = null;
	/**
     * 获取logEngine指定的log
     * @param logEngine
     * @return 
     */
    public static ILogEngine getLogInstance(Class<?> logEngine){
        if(log==null){
            try {
                log = (ILogEngine) logEngine.newInstance();
            } catch (InstantiationException e) {
            	String message = "指定的日志类有误,logEngine参数必须是ILogEngine的实现类";
                throw new RuntimeException(message, e);
            } catch (IllegalAccessException e) {
            	String message = "指定的日志类有误,logEngine参数必须是ILogEngine的实现类";
                throw new RuntimeException(message, e);
            }
        }
        return log;
    }
    
    /**
     * 获取系统默认的日志
     * @return
     */
    public static ILogEngine getDefaultlog(){
        if(log==null){
            log = new Log4jLogEngine();
        }else if(!(log instanceof Log4jLogEngine)){
            log = new Log4jLogEngine();
        }
        return log;
    }
}
