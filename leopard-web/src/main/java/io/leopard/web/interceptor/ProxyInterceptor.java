package io.leopard.web.interceptor;

import io.leopard.web.userinfo.SkipFilterService;
import io.leopard.web4j.proxy.ResinProxy;
import io.leopard.web4j.servlet.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 页面代理，实现访问指定resin机器.
 * 
 * @author 阿海
 * 
 */
@Component
public class ProxyInterceptor implements HandlerInterceptor {

	@Autowired
	private SkipFilterService skipFilterService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String requestUri = RequestUtil.getRequestContextUri(request);
		// System.err.println("ProxyInterceptor preHandle:" + requestUri);
		boolean proxy = ResinProxy.proxy(requestUri, request, response);
		if (proxy) {
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}

}
