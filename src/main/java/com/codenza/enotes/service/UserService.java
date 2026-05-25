package com.codenza.enotes.service;

import com.codenza.enotes.dto.LoginRequest;
import com.codenza.enotes.dto.LoginResponse;
import com.codenza.enotes.dto.UserDTO;

public interface UserService {

	public Boolean register(UserDTO userDTO, String url) throws Exception;

	public LoginResponse login(LoginRequest loginRequest);
	
}
