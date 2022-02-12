package com.naresh.petstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naresh.petstore.model.Users;
import com.naresh.petstore.repository.UserRepository;
import com.naresh.petstore.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Users getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public Users addUser(Users userObj) {
		return userRepository.save(userObj);
	}

	@Override
	public Users getUserByUserId(String userId) {
 		return userRepository.findByUserId(userId).orElse(null);
	}

}
