package cn.edu.fudan.anniversary.listener;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.edu.fudan.anniversary.util.config.ApplicationConfiguration;
import cn.edu.fudan.live.bean.User;

public class MySessionListener implements HttpSessionListener,
		ServletRequestListener {

	@SuppressWarnings("unused")
	private HttpServletRequest request;

	/**
	 * 监听request创建
	 */
	public void requestInitialized(ServletRequestEvent re) {
		request = (HttpServletRequest) re.getServletRequest();
	}

	/**
	 * 监听request销毁
	 */
	public void requestDestroyed(ServletRequestEvent re) {
		// TODO Auto-generated method stub
	}

	/**
	 * 监听Session创建
	 */
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		session.setMaxInactiveInterval(ApplicationConfiguration.SESSION_TIME_OUT);

	}

	/**
	 * 监听Session失效
	 */
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		// application：所有的全局变量保存在这里
		ServletContext application = session.getServletContext();
		@SuppressWarnings("unchecked")
		// session池，用户ID和用户session的对应关系
		Map<Integer, HttpSession> sessionPool = (Map<Integer, HttpSession>) application
				.getAttribute("sessionPool");
		@SuppressWarnings("unchecked")
		// 别名池，用户ID和用户别名的对应关系
		Map<Integer, String> userAliasPool = (Map<Integer, String>) application
				.getAttribute("userAliasPool");

		User user = (User) session.getAttribute("user");

		Integer uid = 0;
		if (user != null)
			uid = user.getUid();

		if (sessionPool != null && sessionPool.containsKey(uid)) {
			sessionPool.remove(uid);
		}
		if (userAliasPool != null && userAliasPool.containsKey(uid)) {
			userAliasPool.remove(uid);
		}
		application.setAttribute("sessionPool", sessionPool);
		application.setAttribute("userAliasPool", userAliasPool);
	}

}