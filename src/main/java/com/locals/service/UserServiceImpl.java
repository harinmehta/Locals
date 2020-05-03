package com.locals.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locals.model.User;
import com.locals.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	Logger logger = LogManager.getLogger();
	
	@Override
	public User findByUsername(String username) {
		User user = null;
		try {
			user = userRepository.findByUsername(username);
		}
		catch(Exception e) {
			logger.catching(e);
		}
		return user;
	}

	@Override
	public User save(User user) {
		User user1 = null;
		try {
			user1 = userRepository.save(user);
		}catch(Exception e){
			logger.catching(e);
		}
		return user1;
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
