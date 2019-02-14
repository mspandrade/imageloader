package com.mspandrade.imageloader.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mspandrade.imageloader.model.User;
import com.mspandrade.imageloader.type.RoleType;

import lombok.Data;

@Data
@Component
public class DefaultUser {
	
	@Value("${defaultuser.enabled}")
	private Boolean enabled;
	
	@Value("${defaultuser.username}")
	private String username;
	
	@Value("${defaultuser.password}")
	private String password;
	
	private PasswordEncoder passwordEncoder;	
	
	@Autowired
	public DefaultUser(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	public Boolean isEnabled() {
		return enabled;
	}
	
	public User instanceDefaultUser() {
		return new User(
				username, 
				passwordEncoder.encode(password),
				RoleType.ADMIN
				);
	}
	
}
