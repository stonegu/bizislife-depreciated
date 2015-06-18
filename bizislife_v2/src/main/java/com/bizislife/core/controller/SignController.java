package com.bizislife.core.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bizislife.core.controller.component.ApiResponse;
import com.bizislife.core.controller.component.SignupForm;
import com.bizislife.util.annotation.PublicPage;

@PublicPage
@Controller
@RequestMapping(value = "/sign")
public class SignController {
	
    @RequestMapping(value="/signup", method=RequestMethod.POST, consumes = "application/json")
    public @ResponseBody ApiResponse signup(HttpSession session,
            @RequestBody final SignupForm signupForm) {
    	
    	ApiResponse apires = new ApiResponse();
    	
    	return apires;
    	
    }


}
