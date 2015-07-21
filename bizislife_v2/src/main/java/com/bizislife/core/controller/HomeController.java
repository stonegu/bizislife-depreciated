package com.bizislife.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bizislife.util.annotation.PublicPage;

@PublicPage
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String test(
            ModelMap model) {
    	logger.debug("enter /test now...");
    	
        return "test";
    }
	
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String root(
            ModelMap model) {
        return "home";
    }

}
