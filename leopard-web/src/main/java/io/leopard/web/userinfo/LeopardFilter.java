package io.leopard.web.userinfo;

import io.leopard.util.MonitorContext;
import io.leopard.web.userinfo.service.ConfigHandler;
import io.leopard.web.userinfo.service.SkipFilterService;
import io.leopard.web.userinfo.service.UserinfoService;
import io.leopard.web4j.session.SessionService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LeopardFilter implements Filter {
	private final Log logger = LogFactory.getLog(this.getClass());

	@Resource
	private ConfigHandler loginHandler;

	// @Autowired(required = false)
	// protected AdminService adminService;

	@Autowired(required = false)
	protected SessionService sessionService;

	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private SkipFilterService skipFilterService;

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		MonitorContext.setRequest(request);// 按入口进行性能监控
		// String uri = RequestUtil.getRequestContextUri(request);//
		// request.getRequestURI();

		HttpServletResponse response = (HttpServletResponse) res;
		LeopardRequestWrapper httpRequestWraper = new LeopardRequestWrapper(request, response, sessionService);
		chain.doFilter(httpRequestWraper, response);
	}

	//
	// protected boolean checkLogin(HttpServletRequest request,
	// HttpServletResponse response) {
	// boolean isExcludeUri = userinfoService.isExcludeUri(request);
	// CsrfUtil.setExcludeUri(request, isExcludeUri);
	// // System.err.println("uri:" + RequestUtil.getRequestContextUri(request)
	// // + " isExcludeUri:" + isExcludeUri);
	// if (isExcludeUri) {
	// return true;
	// }
	// Object account = userinfoService.login(request, response);
	// if (account == null) {
	// this.userinfoService.showLoginBox(request, response);
	// // System.err.println("forwardLoginUrl:");
	// return false;
	// }
	//
	// ConnectionLimitInterceptor.setAccount(request, account);
	//
	// return true;
	// }

	@Override
	public void destroy() {
		logger.info("destroy");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("init");
	}

}
