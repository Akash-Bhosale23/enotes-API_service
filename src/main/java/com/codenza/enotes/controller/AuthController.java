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
import com.codenza.enotes.dto.UserRequest;
import com.codenza.enotes.endpoints.AuthEndpoint;
import com.codenza.enotes.service.AuthService;
import com.codenza.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController implements AuthEndpoint {
	
	@Autowired
	private AuthService authService;

	@Override
	public ResponseEntity<?> register(UserRequest userDTO, HttpServletRequest request) throws Exception{
		
		log.info("AuthController : register() : Execution start");
		String url=CommonUtil.getUrl(request);
		
		Boolean register = authService.register(userDTO,url);
		
		if(!register) {
			log.info("Error message : {} ", "Registration failed");
			return CommonUtil.createErrorResponseMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		log.info("AuthController : register() : Execution end");
		return CommonUtil.createBuildResponseMessage("User Registered Success", HttpStatus.CREATED);
			

	}
	
	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) throws Exception{
		
		LoginResponse loginResponse = authService.login(loginRequest);
		
		if(ObjectUtils.isEmpty(loginResponse)) {
			return CommonUtil.createErrorResponseMessage("Invalid Credentials", HttpStatus.BAD_REQUEST);
		}
		
		return CommonUtil.createBuildResponse(loginResponse, HttpStatus.OK);
		
	}
	
}
