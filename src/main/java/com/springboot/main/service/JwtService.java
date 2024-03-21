
package com.springboot.main.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.springboot.main.model.User;
import com.springboot.main.repository.UserRepository;
import com.springboot.main.security.dto.JWTAuthResponse;
import com.springboot.main.security.dto.UserDto;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class JwtService {
	@Autowired
	private UserRepository userRepository;
//
//	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	public static final String SECRET = "fcbf7c525eb7948de05f4bb856043b17415749e8587180cb49984b3aaa99d50f";
 

	 
	
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public 	JWTAuthResponse  generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		Optional<User> user =userRepository.findByUsername( userName);
		UserDto userDto =new UserDto();
		userDto.setId(user.get().getId());
		userDto.setRole(user.get().getRole());
		userDto.setUsername(user.get().getUsername());
		userDto.setPassword(user.get().getPassword());
		String Token=  createToken(claims, userName);
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse(Token,userDto);
		
		return jwtAuthResponse;
		 
		 
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				 
				.setExpiration(new Date(System.currentTimeMillis() +  909600000))

				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
