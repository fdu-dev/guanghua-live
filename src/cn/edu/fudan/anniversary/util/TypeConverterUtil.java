package cn.edu.fudan.anniversary.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TypeConverterUtil {
	@SuppressWarnings("deprecation")
	public static String timestampToString(Timestamp date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer sb = new StringBuffer();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Timestamp today = Timestamp.valueOf(df1.format(now)+ " 00:00:00");
		Timestamp yesterday =  new Timestamp(today.getTime() - 86400000);
		long l = now.getTime() - date.getTime();
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
	
		if(date.before(yesterday)){
			sb.append(df.format(date));
		}else if(date.before(today)){
			sb.append("昨天 " + date.getHours() + ":" + date.getMinutes());
		}else{
			if (hour > 0)
				sb.append(hour + "小时");
			else if(min > 0)
				sb.append(min + "分钟");
			else if(s > 0)
				sb.append(s + "秒");
			else if(s == 0)
				sb.append("1秒");
			
			 sb.append("前");
		}
	
		return sb.toString();
	}
}
