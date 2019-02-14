package com.mspandrade.imageloader.model;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.mspandrade.imageloader.type.RoleType;

import lombok.Data;

@Document
@Data
public class User {

	@Id
	@JsonProperty(access=Access.READ_ONLY)
	private String id;
	
	@NotBlank
	private String username;

	@NotBlank
	@JsonProperty(access=Access.WRITE_ONLY)
	private String password;
	
	private Boolean enable = true;
	
	private RoleType role;
	
	public User() {}
	
	public User(
			String username, 
			String password,
			RoleType role
			) {
		setUsername(username);
		setPassword(password);
		setRole(role);
	}
	
}
