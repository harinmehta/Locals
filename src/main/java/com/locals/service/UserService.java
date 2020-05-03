package com.locals.service;

import java.util.List;

import com.locals.model.User;

public interface UserService {

	User findByUsername(String username);
	
	User save(User user);
	
	List<User> getAll();
}
