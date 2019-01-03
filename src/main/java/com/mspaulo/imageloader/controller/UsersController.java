package com.mspaulo.imageloader.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mspaulo.imageloader.model.User;
import com.mspaulo.imageloader.service.UserService;

@RestController
@RequestMapping("api/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {

	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public User store(@RequestBody User user) {
		return userService.save(user);
	}
	
	@GetMapping("me")
	public User me(Principal principal) {
		
		return userService.findByUsername(principal.getName());
	}
	
}
