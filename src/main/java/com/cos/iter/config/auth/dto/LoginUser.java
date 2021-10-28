package com.cos.iter.config.auth.dto;

import com.cos.iter.domain.user.User;

import lombok.Data;

@Data	
public class LoginUser {
	private int id;
	private String username;
	private String email;
	private String name;
	private String role;
	
	public LoginUser(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.name = user.getName();
		this.role = user.getRole().getKey();
	}
	
	public User getUser() {
		return User.builder()
				.id(id)
				.build();
	}
}
