package cn.edu.fudan.anniversary.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.json.JSONException;

import cn.edu.fudan.anniversary.util.IpUtil;
import cn.edu.fudan.anniversary.util.config.ApplicationConfiguration;
import cn.edu.fudan.anniversary.util.log.ILogEngine;
import cn.edu.fudan.anniversary.util.log.Log4jLogEngine;
import cn.edu.fudan.anniversary.util.log.LogFactory;

import net.sf.json.JSONArray;

/**
 * 初始化过滤器
 * 
 * @author tom
 * @since 2014-7-20
 */
public class InitFilter implements Filter {
	// 处理请求的起点，对请求和返回编码，记录请求
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		// 编码
		HttpServletRequest request = (HttpServletRequest) arg0;		
		HttpServletResponse response = (HttpServletResponse) arg1;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		// 记录请求日志
		String userAgent = request.getHeader("User-Agent");
		String path = request.getServletPath().toString();
		if (!(userAgent.contains("google") || userAgent.contains("baidu") || userAgent
				.contains("bing"))) {
			ILogEngine logger = LogFactory.getLogInstance(Log4jLogEngine.class);
			StringBuffer buf = new StringBuffer();
			Date time = new Date();
			String ip = IpUtil.getIpAddr(request);
			@SuppressWarnings("unchecked")
			Map<String, String[]> paramsMap = request.getParameterMap();
			String params = JSONArray.fromObject(paramsMap).toString();
			buf.append(path);
			buf.append(" | ");
			buf.append(DateFormat.getInstance().format(time));
			buf.append(" | ");
			buf.append(ip);
			buf.append(" | ");
			buf.append(params);
			buf.append(" | ");
			buf.append(userAgent);
			logger.log(buf.toString());
		}

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(ApplicationConfiguration.SESSION_TIME_OUT);

		// 首页跳转
		if (path.equals("") || path.equals("/")) {
			// response.sendRedirect("intro.jsp");
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
			return;
		}
		arg2.doFilter(request, response);
	}

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
