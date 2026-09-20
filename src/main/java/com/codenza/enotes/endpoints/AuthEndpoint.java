package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codenza.enotes.dto.LoginRequest;
import com.codenza.enotes.dto.LoginResponse;
import com.codenza.enotes.dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="Authentication", description = "Public APIs for user authentication")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	@Operation(summary = "User registration" , tags = {"Home"}, description = "Register new user here with proper JSON data")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "201", description = "User registered successfully"),
		    @ApiResponse(responseCode = "400", description = "Invalid input data (e.g. missing fields, invalid email format)"),
		    @ApiResponse(responseCode = "409", description = "Email already registered"),
		    @ApiResponse(responseCode = "500", description = "Registration failed due to a server error")
		})
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRequest userDTO, HttpServletRequest request) throws Exception;
	
	
	@Operation(summary = "User login", description = "Login with already registered user's email and password")
	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Login successful, returns JWT token and user details",
		        content = @Content(schema = @Schema(implementation = LoginResponse.class))),
		    @ApiResponse(responseCode = "400", description = "Invalid email or password"),
		    @ApiResponse(responseCode = "401", description = "Authentication failed")
		})
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) throws Exception;
}
