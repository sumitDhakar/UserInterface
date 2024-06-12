package com.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.entity.AuthResponse;
import com.user.entity.User;
import com.user.payload.UserLogin;
import com.user.payload.UserRequest;
import com.user.service.AuthService;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin("*")

public class AuthController {

	
	@Autowired
	private AuthService userServices;
	
	
	@PostMapping("{role}")
	public ResponseEntity<?> createUser( UserRequest user,@PathVariable String role) {
		System.out.println("user");
		
		return new ResponseEntity<User>(this.userServices.createUser(user,role), HttpStatus.CREATED);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<?> login( UserLogin authRequest) {
		return new ResponseEntity<AuthResponse>(this.userServices.login(authRequest), HttpStatus.ACCEPTED);
	}

}
