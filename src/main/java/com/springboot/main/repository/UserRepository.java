package com.springboot.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.main.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	 
////	   Optional<User> findByUserName(String username);
 
	public Optional<User> findByUsername(String username);

}

