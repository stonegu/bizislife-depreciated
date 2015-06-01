package com.bizislife.core.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

public class AuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter{

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		
		Object loginUser = request.getAttribute("loginUser");
		
		if (loginUser==null) {
			loginUser = request.getParameter("loginUser");
		}
		
		if (loginUser==null) {
			return null;
//            throw new PreAuthenticatedCredentialsNotFoundException("loginUser not found in request.");
		}
		
		return loginUser;
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		// TODO Auto-generated method stub
        return "N/A";
	}

}
