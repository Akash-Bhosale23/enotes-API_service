package com.codenza.enotes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {

	private UserDTO user;
	
	private String token;
	
}
