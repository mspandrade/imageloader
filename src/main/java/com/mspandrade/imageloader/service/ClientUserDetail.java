package com.mspandrade.imageloader.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mspandrade.imageloader.config.DefaultUser;
import com.mspandrade.imageloader.type.RoleType;


@Service
public class ClientUserDetail implements UserDetailsService{

	private UserService userService;
	private DefaultUser defaultUser;
	
	@Autowired
	public ClientUserDetail(UserService userService, DefaultUser defaultUser) {
		this.userService = userService;
		this.defaultUser = defaultUser;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		UserDetails detail = null;
		com.mspandrade.imageloader.model.User user = null;
		
		if (defaultUser.isEnabled()) {
			
			user = defaultUser.instanceDefaultUser();
		}
		
		if (user == null || !user.getUsername().equals(username) ) {	
			
			user = userService.findByUsername(username);	
		}
		
		if (user != null) {
			
			detail = new User(
						user.getUsername(), 
						user.getPassword(), 
						true,//enabled 
						true,//accountNonExpired 
						true,//credentialsNonExpired 
						true,//accountNonLocked 
						getAuthories(user) //authorities
					 );
		}
		
		return detail;
	}
	
	private List<GrantedAuthority> getAuthories(com.mspandrade.imageloader.model.User user) {
		
		List<GrantedAuthority> authories = new ArrayList<>();
		
		authories.add(new GrantedAuthority() {			
			private static final long serialVersionUID = 1L;
			@Override
			public String getAuthority() {
				return user.getRole().name();
			}
		});		
		
		if (user.getRole().equals(RoleType.ADMIN)) {
			
			authories.add(new GrantedAuthority() {			
				private static final long serialVersionUID = 1L;
				@Override
				public String getAuthority() {
					return RoleType.CLIENT.name();
				}
			});
		}
		
		return authories;
	}

}
