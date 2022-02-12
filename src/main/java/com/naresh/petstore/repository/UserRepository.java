package com.naresh.petstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naresh.petstore.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUserId(String userId);
	
	Users addUser(Users userobj);

}
