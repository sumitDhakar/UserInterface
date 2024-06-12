package com.user.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.payload.UserRequest;
import com.user.payload.api.response.UserResponse;
import com.user.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService Userservice;

	@PutMapping("/update")
	public ResponseEntity<UserResponse> updateEmployee(UserRequest usersRequest) {
		System.err.println(usersRequest);

		return new ResponseEntity<UserResponse>(this.Userservice.updateUser(usersRequest), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(this.Userservice.getUsersById(id));

	}

	@DeleteMapping("delete/{depId}")
	public ResponseEntity<Boolean> deletedUser(@PathVariable Long depId) {

		return ResponseEntity.ok(Userservice.deleteUser(depId));
	}

	@PostMapping("search/{pageNo}/{pageSize}")
	public ResponseEntity<Page<UserResponse>> searchDepartment(@PathVariable Integer pageNo,
			@PathVariable Integer pageSize,  UserRequest designation) {
		Page<UserResponse> departments = this.Userservice.searchUser(pageNo, pageSize, designation);
		System.err.println(designation);
		return new ResponseEntity<Page<UserResponse>>(departments, HttpStatus.OK);
	}

}
