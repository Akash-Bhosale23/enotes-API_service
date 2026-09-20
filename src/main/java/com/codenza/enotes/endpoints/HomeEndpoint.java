package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codenza.enotes.dto.PswdResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name="Home", description = "Public APIs for email verification and password reset operations")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@Operation(summary = "Verify user account", description = "Verifies registered user's account using the id and verification code sent via email")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uId, @RequestParam String code) throws Exception;
	
	@Operation(summary = "Send password reset email", description = "Sends a password reset link to the user's registered email address")
	@PostMapping("/send-reset-email")
	public ResponseEntity<?> sendPasswordResetEmail (@RequestParam String email, HttpServletRequest request) throws Exception;
	
	@Operation(summary = "Verify password reset link", description = "Validates the id and verification code from the password reset link before allowing a new password to be set")
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink (@RequestParam Integer uId, @RequestParam String code) throws Exception;
	
	@Operation(summary = "Reset password", description = "Sets a new password for the user after the reset link has been verified")
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword (@RequestBody  PswdResetRequest pswdResetRequest) throws Exception;
	
}
