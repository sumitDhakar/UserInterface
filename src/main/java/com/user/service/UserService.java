package com.user.service;

import org.springframework.data.domain.Page;

import com.user.payload.UserRequest;
import com.user.payload.api.response.UserResponse;

public interface UserService {
	public UserResponse updateUser(UserRequest users);

	public UserResponse getUsersById(Long id);
	
	public Boolean deleteUser(Long id);
	
	public Page<UserResponse> searchUser(Integer pageNo,Integer pageSize,UserRequest designationRequest);

	

}
