/**
* @Title: PropertyManager.java
* @Description: TODO
* @author: Calvinyang
* @date: Dec 19, 2013 10:10:56 AM
* Copyright: Copyright (c) 2013
* @version: 1.0
*/
package cn.edu.fudan.live.util;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public final class PropertyManager {
	private static Properties properties = getProperties();
	
	/**
	 * @return the properties
	 */
	public static Properties getProperties() {
		if(properties == null) {
			properties = new Properties();
			try {
				URL in = PropertyManager.class.getResource("/config.properties");
				properties.load(in.openStream());
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		return properties;
	}
	
	/**
	 * 
	* @Title: get
	* @Description: 获取key的值
	* @param key
	* @return
	 */
	public static String get(String key) {
		return get(key, null);
	}
	
	/**
	 * 
	* @Title: get
	* @Description: 获取key的值
	* @param key
	* @param defaultValue
	* @return
	 */
	public static String get(String key, String defaultValue) {
		if (properties == null) {
			return defaultValue;
		}
		return properties.containsKey(key) ? properties.get(key).toString() : defaultValue;
	}
	
}
