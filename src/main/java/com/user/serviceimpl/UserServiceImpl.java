package com.user.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.user.constant.AppConstants;
import com.user.entity.User;
import com.user.exception.ResourceNotFoundException;
import com.user.exception.ResourcesAlreadyExists;
import com.user.exception.UserNotFoundException;
import com.user.payload.UserRequest;
import com.user.payload.api.response.UserResponse;
import com.user.repository.IUserRepository;
import com.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private IUserRepository iUserRepository;

	public UserResponse userToUserResponse(User user) {
		return this.modelMapper.map(user, UserResponse.class);
	}

	public User userRequestToUser(UserRequest usersRequest) {
		return this.modelMapper.map(usersRequest, User.class);
	}

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserResponse updateUser(UserRequest users) {
		User user = this.iUserRepository.findByIdAndDeleted(users.getId(), false)
				.orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND + users.getId()));
		boolean emailExists = this.iUserRepository.existsByEmailAndDeletedAndIdNot(users.getEmail(), false,
				users.getId());
		if (emailExists) {
			throw new ResourcesAlreadyExists("this Email is Already Exists");
		}
		user.setUserName(users.getUserName());

		return this.userToUserResponse(this.iUserRepository.save(user));
	}

	@Override
	public UserResponse getUsersById(Long id) {
		User user = this.iUserRepository.findByIdAndDeleted(id,false).orElseThrow(() -> new UserNotFoundException(AppConstants.USER_NOT_FOUND + id));
		
				return userToUserResponse(user);
	}

	@Override
	public Boolean deleteUser(Long id) {
		User user = this.iUserRepository.findByid(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND+id));
		user.setDeleted(true);
		this.iUserRepository.save(user);
		return true;
	}
	
	
	@Override
	public Page<UserResponse> searchUser(Integer pageNo, Integer pageSize,
			UserRequest designationRequest) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues() // ignoring null values of variable
				.withIgnoreCase() // ignoring case of letters
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) // contains for string
				.withMatcher("id", match -> match.transform(value -> value.map(id -> ((Long) id == 0) ? null : id))); // for

		// for

		Example<User> example = Example.of(this.userRequestToUser(designationRequest), matcher);
		List<User> list = this.iUserRepository.findAll(example);

		return new PageImpl<UserResponse>(
				list.stream().map(d -> this.userToUserResponse(d)).collect(Collectors.toList()));
	}

	
}
