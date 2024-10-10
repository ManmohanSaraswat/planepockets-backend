package com.planepockets.proton.controller;

import com.planepockets.proton.configuration.JwtRequestFilter;
import com.planepockets.proton.exception.CustomException;
import com.planepockets.proton.mailservice.MailService;
import com.planepockets.proton.model.JwtRequest;
import com.planepockets.proton.model.User;
import com.planepockets.proton.service.JwtService;
import com.planepockets.proton.service.UserAuthenticationService;
import com.planepockets.proton.service.UserService;
import com.planepockets.proton.wrapperclasses.ForgotPasswordPojo;
import com.planepockets.proton.wrapperclasses.SimpleResponse;
import com.planepockets.proton.wrapperclasses.UserPojo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/user")
@CrossOrigin
public class UserController {

	static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	@Qualifier("UserService")
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private MailService mailService;

	@Autowired
	private JwtRequestFilter requestFilter;

	@Autowired
	private UserAuthenticationService userAuthenticationService;

	@PostMapping("/register")
	public SimpleResponse registerUser(@RequestBody User user) throws CustomException {
		userService.registerNewUser(user);
		mailService.sendUserRegisteredMail(user.getFullName(), user.getLoginId());
		log.info("New user registered successfully {}", user);
		return new SimpleResponse("User registered Successfully");
	}

	@PostConstruct
	public void initRoleAndUser() {
		log.info("Initializing role and user");
		userService.initRoleAndUser();
	}

	@PostMapping("/login")
	public UserPojo loginUser(@RequestBody JwtRequest jwtRequest) throws Exception {
		UserPojo pojo = jwtService.createJwtToken(jwtRequest);
		log.info("Token created successfully for the request : {}", jwtRequest);
		return pojo;
	}

	@PostMapping("/forgot")
	public SimpleResponse forgotPassword(@RequestBody ForgotPasswordPojo pojo) throws Exception {
		return userService.forgotPassword(pojo);
	}

	@PostMapping("/reset")
	public SimpleResponse resetPassword(@RequestBody ForgotPasswordPojo pojo) throws Exception {
		return userService.resetPassword(pojo);
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('Admin')")
	@CrossOrigin
	public SimpleResponse forAdmin() {
		return new SimpleResponse("You are authenticated as admin");
	}
		
	@GetMapping("/user")
	@PreAuthorize("hasRole('User')")
	@CrossOrigin
	public SimpleResponse forUser() {
		return new SimpleResponse("You are authenticated as user");
	}

	@GetMapping("/logout/{userName}")
	@CrossOrigin
	public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token, @PathVariable("userName") String userName) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String authenticatedEmail = authentication.getName();
		if(!userName.equals(authenticatedEmail)) return new ResponseEntity<>(new SimpleResponse("Unauthorized Access"), HttpStatus.FORBIDDEN);
		userAuthenticationService.logoutSession(userName, token);
		requestFilter.invalidateSession(userName, token);
		return new ResponseEntity<>(new SimpleResponse("User logged out successfully"), HttpStatus.OK);
	}
}