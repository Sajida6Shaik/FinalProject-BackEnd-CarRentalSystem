
package com.springboot.main.service;

import com.springboot.main.security.dto.JWTAuthResponse;
import com.springboot.main.security.dto.LoginDto;
import com.springboot.main.security.dto.RegisterDto;

public interface AuthService {
	JWTAuthResponse login(LoginDto dto);

	String register(RegisterDto dto);
}
