/**
 * @Title: MailUtil.java
 * @Description: TODO
 * @author: Calvinyang
 * @date: Dec 19, 2013 10:19:10 AM
 * Copyright: Copyright (c) 2013
 * @version: 1.0
 */
package cn.edu.fudan.live.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import cn.edu.fudan.live.bean.User;

public final class MailUtil {
	private static String content;
	private static Authenticator authenticator;
	private static Properties prop;

	/**
	 * 
	 * @Title: generateUrl4User
	 * @Description: 构造激活链接
	 * @param id
	 * @return
	 */
	public static String generateUrl4User(User u) {
		StringBuffer sb = new StringBuffer();//time+uid
		sb.append(System.currentTimeMillis());
		String encodeUid = Base64.encode(String.valueOf(u.getUid()).getBytes());
		String secretUid = SecretUtil.encode(encodeUid);
		sb.append(secretUid);
		return PropertyManager.get("web.resetPasswordUrl") + Base64.encode(sb.toString().getBytes());
	}

	/**
	 * 
	 * @Title: getContent
	 * @Description: 获取邮件内容
	 * @param params
	 * @return
	 */
	private static String getContent(Map<String, String> params, String template) {
		String content = getContent(template);
		for (String key : params.keySet()) {
			content = content.replace("${" + key + "}", params.get(key));
		}
		return content;
	}

	/**
	 * @return the content
	 */
	private static String getContent(String template) {
		if (content == null) {
			StringBuffer sb = new StringBuffer();
			URL in = PropertyManager.class.getResource(template);
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(in.openStream()));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (br != null) {
						br.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}

	/**
	 * 
	 * @Title: getAuthenticator
	 * @Description: 获取认证用户
	 * @return
	 */
	private static Authenticator getAuthenticator() {
		if (authenticator == null) {
			authenticator = new MyAuthencator(
					PropertyManager.get("email.username"),
					PropertyManager.get("email.password"));
		}
		return authenticator;
	}
	
	public static void sendModifyPswdMail(String email,String user, String url){
		Map<String, String> map = new HashMap<String, String>();
		map.put("activateUrl", url);
		map.put("user", user);
		try {
			sendMail(email, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param receiver -- email of receiver
	 * @Title: sendMail
	 * @Description: 发送邮件
	 */
	public static void sendMail(String receiver, Map<String, String> params)
			throws Exception {
		Session session = Session.getDefaultInstance(getProps(),
				getAuthenticator());
		Message message = new MimeMessage(session);
		Address from = new InternetAddress(
				PropertyManager.get("email.username"));
		message.setFrom(from);
		Address to = new InternetAddress(receiver);
		message.setRecipient(Message.RecipientType.TO, to);
		message.setSubject(PropertyManager.get("email.subject"));
		message.setSentDate(new Date());
		Multipart part = new MimeMultipart();
		BodyPart body = new MimeBodyPart();
		body.setContent(getContent(params, "/modifypasswd.tpl"), "text/html;charset=utf-8");
		part.addBodyPart(body);
		message.setContent(part);
		Transport.send(message);
	}

	/**
	 * 
	 * @author: Calvinyang
	 * @Description: 用户认证
	 * @date: Dec 19, 2013 2:21:36 PM
	 */
	static class MyAuthencator extends Authenticator {
		private String name;
		private String pwd;

		/**
		 * @param name
		 * @param pwd
		 */
		public MyAuthencator(String name, String pwd) {
			super();
			this.name = name;
			this.pwd = pwd;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(name, pwd);
		}

	}

	private static Properties getProps() {
		if (prop == null) {
			prop = new Properties();
			prop.put("mail.smtp.host", PropertyManager.get("email.host"));
			prop.put("mail.smtp.port", PropertyManager.get("email.port"));
			prop.put("mail.smtp.auth", PropertyManager.get("email.auth"));
		}
		return prop;
	}

//	/**
//	 * @Title: sendComplaintMail
//	 * @Description: TODO
//	 * @param params
//	 * @throws Exception 
//	 */
//	public static void sendComplaintMail(Map<String, String> params, User u) throws Exception {
//		Session session = Session.getDefaultInstance(getProps(),
//				getAuthenticator());
//		Message message = new MimeMessage(session);
//		Address from = new InternetAddress(
//				PropertyManager.get("email.username"));
//		message.setFrom(from);
//		Address to = new InternetAddress(PropertyManager.get("email.username"));
//		message.setRecipient(Message.RecipientType.TO, to);
//		if (u.getUid() != null) {
//			Address cc = new InternetAddress(u.getUid());
//			message.setRecipient(Message.RecipientType.CC, cc);
//		}
//		message.setSubject("复旦生涯精灵 - 留言/建议");
//		message.setSentDate(new Date());
//		Multipart part = new MimeMultipart();
//		BodyPart body = new MimeBodyPart();
//		if (null == params.get("title")) {
//			params.put("title", "");
//		}
//		body.setContent(getContent(params, "/complaint.tpl"), "text/html;charset=utf-8");
//		part.addBodyPart(body);
//		message.setContent(part);
//		Transport.send(message);
//	}
}
