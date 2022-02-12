package com.naresh.petstore.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.naresh.petstore.model.Users;
import com.naresh.petstore.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	CustomUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users usersObj = userService.getUserByUserId(userName);
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		return new User(usersObj.getUserId(), "", authorities);
	}

}
