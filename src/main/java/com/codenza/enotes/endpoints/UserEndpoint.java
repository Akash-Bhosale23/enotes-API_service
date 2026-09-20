package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codenza.enotes.dto.PasswordChangeRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "APIs for viewing profile and managing account password")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {
	
	@Operation(summary = "Get profile", description = "Retrieves the profile details of the logged-in user")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile();
	
	@Operation(summary = "Change password", description = "Changes the password of the logged-in user; requires the current password for verification")
	@PostMapping("/change-pswd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequest changeRequest);

}
