package com.cognizant.usermanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.cognizant.usermanagement.filter.JWTfilter;

@SpringBootApplication
public class UserManagementApplication {

	@Bean
	public FilterRegistrationBean jwtFilter()
	{
		FilterRegistrationBean frb = new FilterRegistrationBean<>();
		frb.setFilter(new JWTfilter());
		frb.addUrlPatterns("/api/v1/*");
		return frb;
	}
	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
