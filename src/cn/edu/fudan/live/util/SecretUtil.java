package cn.edu.fudan.live.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SecretUtil {

	//JDK对DESede算法密钥对长度是112位和168位 
	private static final String key = "sj*&9dk3l3jjj22@KO3zOL5ESpEX3u"
			+"YttX1BhgrP1RqgZmyzOcDID5dfY6gs8fc8gkhOlRvi6jG5QUWKDtPO7f8CyC1YQxQvmEMAAAAAASCwBck"
			+"0102030405060708k3l^3O7f8CyC1YQxQvmEMAAOlRvi6jG5QUWKDtPO7f8CyC1YQxQvmEMAAAAAASCwBck"
			+"HK6Jy6OrfB7ZkZg6WD/rQIZfNeFn+nrrXQCTfN7Cv3cAADoAAAAAAOPCv3cAAAAAAM35CFzU/wsIAAAAVNT"
			+"/C8LWWgwUwXwDkMB8A+GrWgzEwXwDlFzAd3AgvncAAAAAZAYAAK8nVQwUwXwDZAAAAEAAAABn0/8LXNT/C9"
			+"PAWgwUwXwDQAAAAATBfAMJTRN4SLuBCnzBfAMAAAAAEAAAAAAAAAAAAAAAAAAAAAABEWnGJorzX8xAuT"
			+"W1+kKO3zOL5ESpEX3uYttX1BhgrP1RqgZmyzOcDID5dfY6gs8fc8gkhOlRvi6jG5QUWKDtPO7f8CyC1YQxQvmEMA";
	private static final byte[] enKey = new byte[168]; 
	static{
		byte[] salt = key.getBytes();
		for(int i = 0; i < salt.length;i++){
			enKey[i % 168] = salt[i];
		}
	}
	
	public static String encode(String src){
		byte[] encryptedData = null;
		String result = "";
		try
		{
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] srcBytes = src.getBytes("utf-8");
			encryptedData = cipher.doFinal(srcBytes);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		result = new String(Base64.encode(encryptedData));
		return result;
	}
	
	public static String decode(String src){
		byte[] srcBytes = Base64.decode(src);
		byte[] encryptedData = null;
		String result = null;
		try
		{
			DESedeKeySpec dks = new DESedeKeySpec(enKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.DECRYPT_MODE, key);
			encryptedData = cipher.doFinal(srcBytes);
			result = new String(encryptedData,"utf-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static String encodeMap(Map<String, Object> map){
		String str = "";
		Set<String> keys = map.keySet();
		for(String key: keys){
			str = str+key+"="+map.get(key)+"&";
		}
		String ret = encode(str);
		return ret;
	}
	
	public static Map<String, Object> decodeToMap(String src) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		src = decode(src);
		String[] params = src.split("&");
		for(int i = 0; i < params.length; i++){
			String[] tmps = params[i].split("=");
			if(tmps.length==2){
				map.put(tmps[0], tmps[1]);
			}else{
				throw new Exception("Invalid encoded input string");
			}
		}
		return map;
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap();
		map.put("s_id", 87);
		map.put("script_id", 192);
		String ret = encodeMap(map);
		System.out.println(ret);
		map = decodeToMap("EhuvxAUdNswxK9r25VxXCwZRKkfKcCd4");
		Set<String> keys = map.keySet();
		for(String key: keys){
			System.out.println(key+" : "+map.get(key));
		}
//		String tmp = URLDecoder.decode("Gz8sPz9jXAwxKz8%2Fo6RcVwsKP10%2FPz8tKQ%3D%3D", "utf-8");
//		System.out.println(tmp);
//		System.out.println(decode(tmp));
	}
	
}
