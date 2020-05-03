package com.locals.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.locals.model.User;
import com.locals.service.UserService;

@Controller
@RequestMapping()
public class CoreController {

	@Autowired
	UserService userService;
	
	Logger logger = LogManager.getLogger();
	
	@Autowired
	AuthenticationManager authManager;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);	
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
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
	private boolean isAuthenticated(Authentication authentication) {
	    return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestParam(name =  "username") String username,
										@RequestParam(name = "password") String password){
		
		if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
			return ResponseEntity.badRequest().body(null);
		}
		UsernamePasswordAuthenticationToken authReq = 
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authManager.authenticate(authReq);
		
		boolean isAuthenticated = isAuthenticated(authentication);
		if (!isAuthenticated) {
			logger.error("Not authenticated");
			return ResponseEntity.badRequest().body(null);
		}
		
		User user = userService.findByUsername(username);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return ResponseEntity.accepted().body(user);
		
		
	}
	
}
