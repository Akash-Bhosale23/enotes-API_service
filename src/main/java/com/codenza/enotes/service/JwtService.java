package com.codenza.enotes.service;

import com.codenza.enotes.entity.User;

public interface JwtService {

	String generateToken(User user);
	
}
