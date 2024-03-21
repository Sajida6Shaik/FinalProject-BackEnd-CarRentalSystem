
package com.springboot.main.securityconfig;

import org.springframework.stereotype.Service;

import com.springboot.main.repository.UserRepository;

@Service

public class CustomUserDetailsService {

	private UserRepository userRepository;

	// Constructor DI

	public CustomUserDetailsService(UserRepository userRepository) {

		super();

		this.userRepository = userRepository;

	}

}
