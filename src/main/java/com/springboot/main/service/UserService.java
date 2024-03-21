package com.springboot.main.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.User;
import com.springboot.main.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
private PasswordEncoder passwordEncoder;

	// TO SAVE A USER IN DB

	public User addNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	// GET ALL USERS
	public List<User> getAllUsers(Pageable pageable) {

		return userRepository.findAll(pageable).getContent();
	}

	// GET USER BY ID
	public User getById(int uid) throws InvalidIdException {
		Optional<User> optional = userRepository.findById(uid);
		if (!optional.isPresent())
			throw new InvalidIdException("UserID Invalid");
		return optional.get();
	}

	// TO DELETE A USER

	public void deleteUser(int uid) {
		userRepository.deleteById(uid);

	}

}
