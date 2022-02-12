package com.naresh.petstore.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naresh.petstore.model.Users;
import com.naresh.petstore.security.CredentialsContext;
import com.naresh.petstore.security.CredentialsContextHolder;
import com.naresh.petstore.service.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	
	@Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Users> getUserById(@PathVariable Long userId) {
        Users userObj = userService.getUserById(userId);
        return ResponseEntity.ok(userObj);
    }
    
    @GetMapping("/currentUser")
    public CredentialsContext getCurrentUser(){
    	return CredentialsContextHolder.getContext();
    }

    @PostMapping
    public Users addUser(@RequestBody(required = true) @Valid Users userObj){
    	return userService.addUser(userObj);
    }

}
