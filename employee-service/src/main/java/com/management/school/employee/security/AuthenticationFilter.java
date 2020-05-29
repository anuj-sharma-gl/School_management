package com.management.school.employee.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.library.core.bean.ExceptionResponseBean;
import com.library.core.bean.SuccessResponseBean;
import com.management.school.employee.bean.LoginReqBean;
import com.management.school.employee.bean.UserDto;
import com.management.school.employee.repository.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private UserService userService;
	private Environment environment;

	public AuthenticationFilter(UserService userService, Environment environment,
			AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.environment = environment;
		super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginReqBean creds = new ObjectMapper().readValue(request.getInputStream(), LoginReqBean.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>()));

		} catch (Exception e) {
			System.out.println("Error occured while Login " + e.getLocalizedMessage());
//			throw new RuntimeException(e);
			try {
				unsuccessfulAuthentication(request,response,new AuthenticationException(e.getLocalizedMessage()) {
				});
			} catch (IOException | ServletException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("Error Occured unsuccessfulAuthentication");
		ExceptionResponseBean errorResponse = new ExceptionResponseBean(HttpStatus.SC_UNAUTHORIZED,
				failed.getLocalizedMessage(), "Email/Password not valid", new Date());
		String json = new Gson().toJson(errorResponse);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String userName = ((User) authResult.getPrincipal()).getUsername();
		UserDto userDto = userService.getUserDetailByEmail(userName);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (userDto.getSuperAdmin() != 1) {
			// Set the error status in case of login failure
			response.setStatus(HttpStatus.SC_UNAUTHORIZED);
			// Return Error message
			ExceptionResponseBean errorResponse = new ExceptionResponseBean(HttpStatus.SC_UNAUTHORIZED,
					"Login failed due to access rights", "Login failed due to access rights", new Date());
			String json = new Gson().toJson(errorResponse);
			response.getWriter().write(json);
		} else {
			String token = Jwts.builder().setSubject(userDto.getUserId())
					.setExpiration(new Date(
							System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration"))))
					.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret")).compact();
			SuccessResponseBean bean = new SuccessResponseBean(HttpStatus.SC_OK, "Login Successful", userDto);
			String json = new Gson().toJson(bean);
			response.addHeader("token", token);
			response.addHeader("userId", userDto.getUserId());
			response.getWriter().write(json);
		}
	}

}
