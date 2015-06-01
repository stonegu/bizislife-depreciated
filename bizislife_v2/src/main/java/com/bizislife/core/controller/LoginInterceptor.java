package com.bizislife.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bizislife.core.configuration.ApplicationConfiguration;
import com.bizislife.core.service.AccountService;
import com.bizislife.util.SpringAnnotationUtil;
import com.bizislife.util.annotation.PublicPage;


public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ApplicationConfiguration applicationConfig;
	
	protected String loginURI;

	public void setLoginURI (String loginURI) {
		this.loginURI = loginURI;
	}

	protected List<String> outsidePages;

	public void setOutsidePages (List<String> outsidePages) {
		this.outsidePages = outsidePages;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// if its a resource, skip the login handler
		if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) return true;

		// set the cache control for the content that handlers return
		response.addHeader("Cache-Control", "no-cache, no-store");
		response.addHeader("Pragma", "no-cache");
		response.addHeader("Expires", "-1");

		// check for controllers marked with PublicPage annotation
		if (hasPublicPageAnnotation(request, handler)) return true;

		String uri = request.getRequestURI();
		if (request.getQueryString()!=null && !request.getQueryString().isEmpty())
			uri+="?"+request.getQueryString();

		// check if its one the pages defined in the xml file
		if (outsidePages!=null) {
			for (String outside : outsidePages) {
				if (uri.startsWith(outside)) return true;
			}
		}

		if (accountService.getLoginAccount()==null) {
			if (request.getRequestURI().startsWith(loginURI)) return true; // we're already on login

			// save the back uri
			request.getSession().setAttribute("back_url", uri);
			response.sendRedirect(loginURI);
			return false;
		}
		return true;
	}
	
	static boolean hasPublicPageAnnotation (HttpServletRequest request, Object handler) {
		if (handler instanceof org.springframework.web.method.HandlerMethod) {
			handler = ((org.springframework.web.method.HandlerMethod)handler).getBean();
		}

		Class<?> handlerClass = handler.getClass();

		// see if the annotation has been declared on the handler (controller) class
		boolean controllerIsPublicAnnotation = SpringAnnotationUtil.isAnnotationInControllerClass(handlerClass, PublicPage.class);
		return controllerIsPublicAnnotation;

	}
}
