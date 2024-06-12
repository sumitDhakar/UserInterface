package com.user.service;

import com.user.entity.AuthResponse;
import com.user.entity.User;
import com.user.payload.UserLogin;
import com.user.payload.UserRequest;

public interface AuthService {

	public User createUser(UserRequest u,String roleId);
	

	public AuthResponse login(UserLogin authRequest);


	
}
