package com.mycompany.miniproject.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.mycompany.miniproject.dto.UserDto;

public class UsersDetails extends User {
	private UserDto user;
	
	public UsersDetails(UserDto user, List<GrantedAuthority> authorities) {
		super(user.getUserId(), user.getUserPwd(), user.isUserStatus(), true, true, true, authorities);
		this.user = user;
	}
	
	public UserDto getUser() {
		return user;
	}
}