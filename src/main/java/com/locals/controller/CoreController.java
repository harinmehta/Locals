package com.locals.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locals.model.User;
import com.locals.service.UserService;

@Controller
@RequestMapping()
public class CoreController {

	@Autowired
	UserService userService;
	
	Logger logger = LogManager.getLogger();
	
	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody User user, BindingResult br){
		
		if(br.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		}
		if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
			return ResponseEntity.badRequest().body(null);
		}
		if(!StringUtils.isEmpty(userService.findByUsername(user.getUsername()))) {
			logger.error("User with username : "+user.getUsername()+" already exists");
			return ResponseEntity.badRequest().body(null);
		}
		
		User retrievedUser =  userService.save(user);
		return ResponseEntity.accepted().body(retrievedUser);
		
	}
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll(){
		
		return ResponseEntity.accepted().body(userService.getAll());
	}
	
	
	
}
