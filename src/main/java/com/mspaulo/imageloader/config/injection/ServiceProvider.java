package com.mspaulo.imageloader.config.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mspaulo.imageloader.service.ClientUserDetail;
import com.mspaulo.imageloader.service.UserService;

@Configuration
public class ServiceProvider {
	
	private UserService userService;
	
	@Autowired
	public ServiceProvider(UserService userService) {
		this.userService = userService;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ClientUserDetail(userService);
	}
	
}
