package com.cognizant.usermanagement.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTfilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpReq= (HttpServletRequest)request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		String authHeader  = httpReq.getHeader("authorization");
		if(authHeader ==null || authHeader.split(" ").length!=2)
		{
			throw new ServletException("Missing or invalid Authentication Header");
		}
		httpRes.setHeader("Access-Control-Allow-Origin", httpReq.getHeader("Origin"));
		httpRes.setHeader("Access-Control-Allow-Credentials", "true");
		httpRes.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE,PUT");
		httpRes.setHeader("Access-Control-Max-Age", "3600");
		httpRes.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
		
		String jwtToken =authHeader.substring(7);
		Claims claims =Jwts.parser().setSigningKey("super key").parseClaimsJws(jwtToken).getBody();
		
		httpReq.setAttribute(authHeader, jwtToken);
		
		chain.doFilter(request, response);
	
	
	}

	
}
