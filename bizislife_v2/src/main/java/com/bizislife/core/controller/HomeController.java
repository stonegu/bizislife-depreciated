package com.bizislife.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bizislife.util.annotation.PublicPage;

@PublicPage
@Controller
public class HomeController {
	
	
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String test(
            ModelMap model) {
        return "test";
    }
	
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String root(
            ModelMap model) {
        return "home";
    }

}
