package com.springboot.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.dto.AuthRequest;
import com.springboot.main.dto.AuthResponseDto;
import com.springboot.main.exception.InvalidIdException;
import com.springboot.main.model.User;
import com.springboot.main.security.dto.JWTAuthResponse;
import com.springboot.main.security.dto.UserDto;
import com.springboot.main.service.JwtService;
import com.springboot.main.service.UserService;
@CrossOrigin("*")
@RestController
@RequestMapping("/users")

public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	// To Add A User(REGISTRATION)

	@PostMapping("/new")
	public User addNewUser(@RequestBody User user) {
		return userService.addNewUser(user);
	}

	// TO AUTHENTICATE USER(LOGIN)

	@PostMapping("/authenticate")
	public ResponseEntity<JWTAuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		if (authentication.isAuthenticated()) {
 
//			return jwtService.generateToken(authRequest.getUsername());
			JWTAuthResponse  jwtAuthResponse=jwtService.generateToken(authRequest.getUsername());
			
			return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
			
			
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}
	}

	// GET ALL USERS
	@PreAuthorize(" hasAuthority('ADMIN')")
	@GetMapping("/getallusers")

	public List<User> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "1000000") Integer size) {

		Pageable pageable = PageRequest.of(page, size);
		return userService.getAllUsers(pageable);

	}

	// GET USER BY ID
	@PreAuthorize(" hasAuthority('ADMIN')")
	@GetMapping("/getone/{uid}")
	public ResponseEntity<?> getByUserId(@PathVariable("uid") int uid) {
		try {
			User user = userService.getById(uid);

			return ResponseEntity.ok().body(user);
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// UPDATE USER

	@PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('ADMIN')")
	@PutMapping("/update/{uid}")
	public ResponseEntity<?> updateUser(@PathVariable("uid") int uid, @RequestBody User newUser) {
		try {
			User user = userService.getById(uid);

			if (newUser.getId() != 0)
				user.setId(newUser.getId());

			if (newUser.getUsername() != null)
				user.setUsername(newUser.getUsername());

			if (newUser.getRole() != null)
				user.setRole(newUser.getRole());

			if (newUser.getPassword() != null)
				user.setPassword(newUser.getPassword());

			user = userService.addNewUser(user);
			return ResponseEntity.ok().body(user);

		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	// DELETE USER
	@PreAuthorize("hasAuthority('CUSTOMER') OR hasAuthority('ADMIN')")
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<?> deleteUser(@PathVariable("uid") int uid) {
		try {
			User user = userService.getById(uid);
			userService.deleteUser(uid);
			return ResponseEntity.ok().body("User is Deleted");
		} catch (InvalidIdException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

}

 