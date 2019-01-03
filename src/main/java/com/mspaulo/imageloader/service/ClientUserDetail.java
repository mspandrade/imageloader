package com.mspaulo.imageloader.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.mspaulo.imageloader.type.RoleType;


@Service
public class ClientUserDetail implements UserDetailsService{

	private UserService userService;
	
	@Autowired
	public ClientUserDetail(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		com.mspaulo.imageloader.model.User user = userService.findByUsername(username);
		
		return new User(
				user.getUsername(), 
				user.getPassword(), 
				user.getEnable(),//enabled 
				true,//accountNonExpired 
				true,//credentialsNonExpired 
				true,//accountNonLocked 
				getAuthories(user) //authorities
				);
	}
	
	public List<GrantedAuthority> getAuthories(com.mspaulo.imageloader.model.User user) {
		
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
