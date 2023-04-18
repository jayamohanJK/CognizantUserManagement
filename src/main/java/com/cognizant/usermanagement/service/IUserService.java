package com.cognizant.usermanagement.service;

import java.util.List;
import java.util.Map;

import com.cognizant.usermanagement.model.User;

public interface IUserService {

	public List<User> getAllUsers();
	public User addUser(User user);
	public User  validateUser(String username,String password);
	public boolean validateUserName(Map<String,String> user);
}
