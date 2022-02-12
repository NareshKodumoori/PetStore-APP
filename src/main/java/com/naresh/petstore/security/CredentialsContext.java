package com.naresh.petstore.security;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.naresh.petstore.model.Users;

public class CredentialsContext {

	private Users userDetails;
	private List<String> roles = new ArrayList<>();
	private Boolean isAdmin = Boolean.FALSE;

	@JsonIgnore
	private Users user;

	public Users getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(Users userDetails) {
		this.userDetails = userDetails;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
