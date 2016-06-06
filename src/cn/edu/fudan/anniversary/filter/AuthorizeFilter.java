package cn.edu.fudan.anniversary.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import cn.edu.fudan.live.bean.User;
import cn.edu.fudan.anniversary.exception.WarnReminder;
import cn.edu.fudan.anniversary.util.config.ApplicationConfiguration;
import cn.edu.fudan.anniversary.util.config.WarnReminderConfiguration;

/**
 * 权限拦截器
 * 
 * @author tom
 * @since 2014-7-20
 */
public class AuthorizeFilter implements Filter {

	/** 需要排除（不拦截）的URL的正则表达式 ,游客也可访问 */
	private Pattern excepUrlPattern;

	public void init(FilterConfig filterConfig) throws ServletException {
		String excepUrlRegex = ApplicationConfiguration.GUEST_AUTHROIZE_PATTERN;
		if (excepUrlRegex != null && !excepUrlRegex.equals("")) {
			excepUrlPattern = Pattern.compile(excepUrlRegex);
		}
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		String servletPath = request.getServletPath();

		chain.doFilter(request, response);
		// 如果请求的路径是游客可以访问的URL时，则直接放行
//		if (excepUrlPattern.matcher(servletPath).matches()) {
//			chain.doFilter(request, response);
//			return;
//		}
//
//		User user = (User) session.getAttribute("user");
////		System.out.println("[AuthorizeFilter] user : "+user);
//		if (user == null) {
//			response.setContentType("application/json");
//			WarnReminder reminder = new WarnReminder();
//			reminder.setError_type(WarnReminderConfiguration.LIMIT_NOT_LOGIN_CODE);
//			reminder.setError_message(WarnReminderConfiguration.LIMIT_NOT_LOGIN);
//			String str = JSONObject.fromObject(reminder).toString();
//			response.getWriter().write(str);
//			response.getWriter().close();
//		} else {
//			String excepUrlRegex = ApplicationConfiguration.USER_AUTHROIZE_PATTERN;
//			Pattern authroizePattern = Pattern.compile(excepUrlRegex);
//			if (authroizePattern.matcher(servletPath).matches()) {
//				chain.doFilter(request, response);
//			} else {
//				response.setContentType("application/json");
//				WarnReminder reminder = new WarnReminder();
//				reminder.setError_type(WarnReminderConfiguration.LIMIT_PERMISSION_CODE);
//				reminder.setError_message(WarnReminderConfiguration.LIMIT_PERMISSION);
//				String str = JSONObject.fromObject(reminder).toString();
//				response.getWriter().write(str);
//				response.getWriter().close();
//			}
//		}
	}

	public void destroy() {
	}
}
