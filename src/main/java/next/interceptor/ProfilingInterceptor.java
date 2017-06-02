package next.interceptor;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class ProfilingInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(ProfilingInterceptor.class);
	private static final String AUTHORIZATION = "Authorization";
	private static final String START_TIME = "startTime";
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute(START_TIME, System.currentTimeMillis());
		
		String base64Credentials = request.getHeader(AUTHORIZATION).split(" ")[1];
		String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
		log.debug("Autorization Header: {}", base64Credentials);
		log.debug("Autorization Header Decode: {}", credentials);
		String userId = credentials.split(":")[0];
		String password = credentials.split(":")[1];
		
		User user = userDao.findByUserId(userId);
		if (user == null) {
			log.debug("user not found");
			return true;
		}
		if (user.matchPassword(password)) {
			request.getSession().setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
		}
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long startTime = (long)request.getAttribute(START_TIME);
		long endTime = System.currentTimeMillis();
		
		log.debug("request URI: {}", request.getRequestURI());
		log.debug("execution time: {}", endTime - startTime);
	}
}
