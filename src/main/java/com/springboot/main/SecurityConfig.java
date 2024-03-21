package com.springboot.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.main.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private JwtAuthFilter authFilter;

	@Bean
	// authentication
	public UserDetailsService userDetailsService() {
//	         UserDetails admin = User.withUsername("Basant")
//	                 .password(encoder.encode("Pwd1"))
//	                 .roles("ADMIN")
//	                 .build();
//	         UserDetails user = User.withUsername("John")
//	                 .password(encoder.encode("Pwd2"))
//	                 .roles("USER","ADMIN","HR")
//	                 .build();
//	         return new InMemoryUserDetailsManager(admin, user);
		return new UserInfoUserDetailsService();
	}

//	     @Bean
//	     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	         return http.csrf().disable()
//	                 .authorizeHttpRequests()
//	                 .requestMatchers("/products/new","/products/authenticate").permitAll()
//	                 .and()
//	                 .authorizeHttpRequests().requestMatchers("/products/**")
//	                 .authenticated().and()
//	                 .sessionManagement()
//	                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	                 .and()
//	                 .authenticationProvider(authenticationProvider())
//	                 .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
//	                 .build();
//	     }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(
						requests -> requests.requestMatchers("/users/authenticate","/users/new",  "/car/cardetails",//"/users/getallusers","/users/update/{uid}","/users/delete/{uid}","/users/getone/{uid}",
								"/host/add",//"/host/getallHosts","/host/getone/{hid}","/host/update/{hid}","/host/delete/{hid}",
     							"/admin/add",//"/admin/getallAdmins","/admin/getone/{aid}","/admin/update/{aid}","/admin/delete/{aid}",
								"/customer/add",//"/customer/getallcustomers", "/customer/delete/{cid}","/customer/{cid}/bookings","/customer/{cid}/cars","/customer/getone/{cid}","/customer/update/{cid}",
								//"/car/add/{hid}","/car/getallcars","/car/delete/{carid}","/car/getone/{carid}","/car/update/{carid}",
								//"/customercar/add/{cid}/{carid}","/customercar/getallcustomercar","/,customercar/getone/{custid}","/customercar/update/{custid}","/customercar/delete/{custid}",
					            "/booking/add/{cid}/{carid}"//"/booking/getallBookings","/booking/getone/{bid}","/booking/update/{bid}","/booking/delete/{bid}", 
//						        "/routes/add/{bid}"//"/routes/getallRoutes","/routes/getone/{rid}","/routes/update/{rid}","/routes/delete/{rid}",
								//"/payment/add/{cid}","/payment/getallpayments","/payment/getone/{pid}","/payment/update/{pid}","/payment/delete/{pid}"
								).permitAll())
				.authorizeHttpRequests(requests -> requests.requestMatchers("/admin/**","/car/**","/routes/**","/booking/**","/host/**","/customercar/**","/customer/**","/payment/**","/users/**").authenticated())
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
