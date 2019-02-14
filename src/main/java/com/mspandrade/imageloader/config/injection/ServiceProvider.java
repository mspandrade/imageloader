package com.mspandrade.imageloader.config.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mspandrade.imageloader.config.DefaultUser;
import com.mspandrade.imageloader.service.ClientUserDetail;
import com.mspandrade.imageloader.service.UserService;

@Configuration
public class ServiceProvider {
	
	private UserService userService;
	private DefaultUser defaultUser;
	
	@Autowired
	public ServiceProvider(UserService userService, DefaultUser defaultUser) {
		this.userService = userService;
		this.defaultUser = defaultUser;
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new ClientUserDetail(userService, defaultUser);
	}
	
}