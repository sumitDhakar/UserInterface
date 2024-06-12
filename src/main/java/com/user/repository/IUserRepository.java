package com.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.entity.User;

public interface IUserRepository extends JpaRepository<User,Long> {
	
	public boolean existsByEmailAndDeleted(String email, boolean b);

	public Optional<User> findByEmailAndDeleted(String email, boolean b);

	public Optional<User> findByEmail(String email);
	
	public boolean existsByEmailAndDeletedAndIdNot(String email, boolean b,Long id);

	public Optional<User> findByIdAndDeleted(Long id, boolean b);

	public Optional<User> findByid(Long id);




}
