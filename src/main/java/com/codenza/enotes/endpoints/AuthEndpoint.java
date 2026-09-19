package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codenza.enotes.dto.LoginRequest;
import com.codenza.enotes.dto.UserRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRequest userDTO, HttpServletRequest request) throws Exception;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
