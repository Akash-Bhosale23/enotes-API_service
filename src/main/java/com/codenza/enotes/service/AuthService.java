package com.codenza.enotes.service;

import com.codenza.enotes.dto.LoginRequest;
import com.codenza.enotes.dto.LoginResponse;
import com.codenza.enotes.dto.UserRequest;

public interface AuthService {

	public Boolean register(UserRequest userDTO, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);
	
}
