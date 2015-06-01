package com.bizislife.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.bizislife.core.service.FakeService;
import com.bizislife.util.annotation.PublicPage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@PublicPage
@Controller
public class EndpointDocController {
    private final RequestMappingHandlerMapping handlerMapping;
    @Autowired
    public EndpointDocController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

	@Autowired
	private FakeService fakeService;
	
    @RequestMapping(value="/endpointdoc", method=RequestMethod.GET)
    public @ResponseBody Map<String, Map<String, Set>> show() {
    	Map<?, HandlerMethod> all = this.handlerMapping.getHandlerMethods();
    	Map<String, Map<String, Set>> results = new HashMap<String, Map<String, Set>>();
    	if (all!=null) {
    		
    		int i = 1;
    		for (Map.Entry<?, HandlerMethod> entry : all.entrySet()) {
    			System.out.println(entry.toString());
    			RequestMappingInfo mappingInfo = (RequestMappingInfo)entry.getKey();
    			
    			Map<String, Set> resultValues = new HashMap<String, Set>();
    			resultValues.put("pattern", mappingInfo.getPatternsCondition().getPatterns());
    			resultValues.put("method", mappingInfo.getMethodsCondition().getMethods());
//    			resultValues.put("params", mappingInfo.getParamsCondition().getExpressions());
    			
    			
    			results.put(entry.getValue().getBean().toString() + "_" + i++, resultValues);
    		}
    	}
        return results;
    }
    
    
	@RequestMapping(method = RequestMethod.GET, value="/ffake2get2")
	public @ResponseBody User fake22() {
		return fakeService.getFakeUser("bbb");
	}

}