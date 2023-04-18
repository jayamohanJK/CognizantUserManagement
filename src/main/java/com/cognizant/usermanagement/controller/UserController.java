package com.cognizant.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.usermanagement.model.User;
import com.cognizant.usermanagement.service.UserServiceImpl;



@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "*",allowedHeaders ="*" )
public class UserController 
{
	@Autowired
	UserServiceImpl userService;
	
	@GetMapping("/user/all")
	public ResponseEntity<?> getAllUsers()
	{
		List<User> userList = userService.getAllUsers();
		if(userList.isEmpty())
		{
			return new ResponseEntity<String>("User list is empty", HttpStatus.NOT_FOUND);
		}
		else
			return new ResponseEntity<>(userList, HttpStatus.OK);
	}
	
		
	
	
	

}
