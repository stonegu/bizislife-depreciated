package com.bizislife.core.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;

public interface FakeService {
	
	@PreAuthorize("hasPermission(#storyId, 'isDirector')")
	public User getFakeUser(String userName);
	
	public boolean delFakeUser(String userName);

}
