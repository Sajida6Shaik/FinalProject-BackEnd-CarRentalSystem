
package com.springboot.main.security.dto;

import com.springboot.main.dto.AdminDto;
import com.springboot.main.dto.HostDto;

public class JWTAuthResponse {
	private String accessToken;
	private String tokenType = "Bearer";
	private UserDto userDto;// appending user details and JWT Token in response
 
	public JWTAuthResponse() {
	}

	public JWTAuthResponse(String accessToken, UserDto userDto) {
		super();
		this.accessToken = accessToken;
		this.userDto = userDto;
	}
	
 

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

//	public AdminDto getAdminDto() {
//		return adminDto;
//	}
//
//	public void setAdminDto(AdminDto adminDto) {
//		this.adminDto = adminDto;
//	}
//
//	public HostDto getHostDto() {
//		return hostDto;
//	}
//
//	public void setHostDto(HostDto hostDto) {
//		this.hostDto = hostDto;
//	}
//	
	 
}
