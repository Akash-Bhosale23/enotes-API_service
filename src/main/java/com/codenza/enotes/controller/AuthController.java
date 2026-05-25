package com.codenza.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.LoginRequest;
import com.codenza.enotes.dto.LoginResponse;
import com.codenza.enotes.dto.UserDTO;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDTO userDTO, HttpServletRequest request) throws Exception{
		
		String url=CommonUtil.getUrl(request);
		
		Boolean register = userService.register(userDTO,url);
		
		if(register) {
			return CommonUtil.createBuildResponseMessage("User Registered Success", HttpStatus.CREATED);
		}
		else {
			return CommonUtil.createErrorResponseMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception{
		
		LoginResponse loginResponse = userService.login(loginRequest);
		
		if(ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtil.createErrorResponseMessage("Invalid Credentials", HttpStatus.BAD_REQUEST);
		}
		
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
		
	}
	
}
