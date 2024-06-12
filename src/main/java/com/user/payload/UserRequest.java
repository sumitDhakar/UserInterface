package com.user.payload;


import java.util.Set;

import com.user.entity.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRequest {

	private Long id;
	private String email;
	private String userName;


	private String password;
//	private Set<UserRoles> userRoles;
	
}
