package com.cognizant.usermanagement.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

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

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("auth/v1")
@CrossOrigin(origins = "*",allowedHeaders ="*" )
public class AutheticationController {

	private Map<String , String> map = new HashMap<>();
	
	private UserServiceImpl userServiceImpl;

	@Autowired
	public AutheticationController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;
	}
	
	@GetMapping("/")
	public String  serviceStarted()
	{
		return "Authentication service started";
		
	}
	
	public String generateToken(String username, String passwrd) throws ServletException,Exception
	{
		String jwtToken = "";
		if(username == null || passwrd==null)
		{
			throw new ServletException("Missing Credential");
		}
		User user =userServiceImpl.validateUser(username, passwrd);

		if(user== null)
		{
			throw new ServletException("invalid credential");
		}else
		{
			jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis()+300000))
						.signWith(SignatureAlgorithm.HS256, "super key")
						.compact();
		}
		return jwtToken;
				
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> doLogin(@RequestBody User user)
	{
		try {
			String jwtToken = generateToken(user.getUsername(), user.getPassword());
			map.put("message", "user successfully logged in");
			map.put("token", jwtToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("message", e.getMessage());
			map.put("token", null);
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
			
		}
		return new ResponseEntity<>(map, HttpStatus.OK);

	}
	@PostMapping("/validateUser")
	public ResponseEntity<?> doLogin(@RequestBody Map<String,String> user)
	{
		Map<String,String> hasvalid = new HashMap<String,String>();
		try {
		
			if(userServiceImpl.validateUserName(user))
				hasvalid.put("hasusername","true");
			else
				hasvalid.put("hasusername", null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			return new ResponseEntity<>(hasvalid, HttpStatus.UNAUTHORIZED);
			
		}
		return new ResponseEntity<>(hasvalid, HttpStatus.OK);

	}
	@PostMapping("/user/addUser")
	public ResponseEntity<?> addUser(@RequestBody User user)
	{
		User user1 = userServiceImpl.addUser(user);
		if(user1!=null)
		{
			return new ResponseEntity<String>("User is added", HttpStatus.CREATED);
		}
		else
			return new ResponseEntity<String>("User cannot be added", HttpStatus.CONFLICT);
	}
	
 }
