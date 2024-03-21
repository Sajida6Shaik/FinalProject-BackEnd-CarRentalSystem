package com.springboot.main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponseDto {
	
	public AuthResponseDto(String token2) {
		 this.token=token2;
	}

	public String token;
	

}
