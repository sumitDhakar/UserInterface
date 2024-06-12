package com.user.serviceimpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.constant.AppConstants;
import com.user.entity.AuthResponse;
import com.user.entity.Role;
import com.user.entity.User;
import com.user.entity.UserRoles;
import com.user.exception.ResourcesAlreadyExists;
import com.user.exception.UserNotFoundException;
import com.user.payload.UserLogin;
import com.user.payload.UserRequest;
import com.user.repository.IUserRepository;
import com.user.service.AuthService;
import com.user.utils.JWTUtils;
@Service
public class AuthServiceimpl implements AuthService , UserDetailsService{

	@Autowired
	private IUserRepository usersRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtils jwtUtils;


	
	@Override
	public User createUser(UserRequest userRequest, String roleId) {
	    boolean isPresent = this.usersRepository.existsByEmailAndDeleted(userRequest.getEmail(), false);
	    if (isPresent) {
	        throw new ResourcesAlreadyExists("User with this email already exists: " + userRequest.getEmail());
	    }

	    // Create a new user entity from the user request
	    User user = new User();
	    user.setEmail(userRequest.getEmail());
	    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
	    // Set other properties from userRequest to user as needed

	    Set<UserRoles> userRoles = new HashSet<>();
	    UserRoles userRole = new UserRoles();

	    Role role = new Role();
	    switch (roleId) {
	        case "ADMIN":
	            role.setId(1L);
	            break;
	        case "USER":
	            role.setId(2L);
	            break;
	        case "EMPLOYEE":
	            role.setId(3L);
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid role ID: " + roleId);
	    }

	    userRole.setRoles(role);
	    userRole.setRoles(role); // Set the relationship from UserRoles to User
	    userRoles.add(userRole);

	    user.setUserRoles(userRoles);
	//  user.setCreatedAt(Date.valueOf(LocalDate.now())); // Uncomment if needed

	    User savedUser = this.usersRepository.save(user);
	    return savedUser;
	}
	
	@Override
	public AuthResponse login(UserLogin authRequest) {
	    // Authenticate the user
	    this.authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

	    // Retrieve the user from the repository
	    User u = this.usersRepository.findByEmailAndDeleted(authRequest.getEmail(), false)
	            .orElseThrow(() -> new UserNotFoundException("No such user found with this email: " + authRequest.getEmail()));

	    // Generate the token
	    String token = jwtUtils.generateToken(authRequest.getEmail());

	    // Create the response
	    AuthResponse response = new AuthResponse();
	    response.setToken(token);

	    return response;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.usersRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND_EMAIL + email));
		Set<UserRoles> userRoles = user.getUserRoles();
		List<SimpleGrantedAuthority> authorities = userRoles.stream()
				.map(ur -> new SimpleGrantedAuthority(ur.getRoles().getTitle())).collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(email, user.getPassword(), authorities);
	}


	
}
