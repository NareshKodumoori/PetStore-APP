package com.naresh.petstore.service;

import com.naresh.petstore.model.Users;

public interface UserService {

	public Users getUserById(Long userId);
	
	public Users addUser(Users userObj);
	
	public Users getUserByUserId(String userId);


}
