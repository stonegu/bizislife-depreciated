package com.bizislife.core.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizislife.core.controller.component.ApiResponse;
import com.bizislife.core.service.FakeService;
import com.bizislife.util.annotation.PublicPage;

@PublicPage
@Controller
public class FakeController1 {
	
	@Autowired
	private FakeService fakeService;
	
	@RequestMapping(method = RequestMethod.GET, value="/fakeget")
	public @ResponseBody User fake1() {
		return fakeService.getFakeUser("aaa");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/ffake2get")
	public @ResponseBody User fake2() {
		AntPathMatcher antPathMatcher;
		return fakeService.getFakeUser("bbb");
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/fakedel/{username}")
	public @ResponseBody ApiResponse fakeDel(HttpSession httpSession, @PathVariable String username) {
		boolean passed = fakeService.delFakeUser(username);
		
		if (passed) {
			ApiResponse apires = new ApiResponse();
			apires.setSuccess(true);
			return apires;
		} else {
			ApiResponse apires = new ApiResponse();
			apires.setSuccess(false);
			return apires;
		}
	}

}
