package com.locals.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	private boolean isAuthenticated(Authentication authentication) {
	    return authentication != null && !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> register(@RequestBody User user, BindingResult br){
		
		if(br.hasErrors()) {
			return ResponseEntity.badRequest().body(null);
		}
		if(StringUtils.isEmpty(user.getUsername().trim()) || StringUtils.isEmpty(user.getPassword().trim())) {
			return ResponseEntity.badRequest().body(null);
		}
		user.setUsername(user.getUsername().trim());
		user.setPassword(user.getPassword().trim());
		if(!StringUtils.isEmpty(userService.findByUsername(user.getUsername()))) {
			logger.error("User with username : "+user.getUsername().trim()+" already exists");
			return ResponseEntity.badRequest().body(null);
		}
		
		User retrievedUser =  userService.save(user);
		return ResponseEntity.accepted().body(retrievedUser);
		
	}
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll(){
		
		return ResponseEntity.accepted().body(userService.getAll());
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
	
	@PostMapping("/dummy")
	public ResponseEntity<String> dummy(HttpServletRequest request){
		request.getSession().setMaxInactiveInterval(65);
		return ResponseEntity.accepted().body("Session extended to : "+65+" seconds");
	}

    @GetMapping("/loggedOut")
	public ResponseEntity<String> loggedOut(){
    	return ResponseEntity.accepted().body("Logged out.");
	}
	
}
