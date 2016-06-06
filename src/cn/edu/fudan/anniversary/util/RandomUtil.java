package cn.edu.fudan.anniversary.util;

import java.util.Random;
import java.util.UUID;


/**
 * 随机码生成工具类
 * @author tom
 * @since  2014-7-16
 */
public class RandomUtil {
	public static void main(String[] args){
		System.out.println(generateRandomCode(12));
		System.out.println(generateUUID());
	}

	
	public final static String generateRandomCode(int length){
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWYZ0123456789";   //生成字符串从此序列中取
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString(); 
	}
	
	public final static String generateRandomNumberCode(int length){
		String base = "0123456789";   //生成字符串从此序列中取
	    Random random = new Random();  
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString(); 
	}

	
	public final static String generateUUID(){
		String s = UUID.randomUUID().toString(); 
        //去掉“-”符号 
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24); 
	}
}
