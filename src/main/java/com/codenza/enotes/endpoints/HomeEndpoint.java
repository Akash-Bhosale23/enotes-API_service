package com.codenza.enotes.endpoints;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codenza.enotes.dto.PswdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uId, @RequestParam String code) throws Exception;
	
	@PostMapping("/send-reset-email")
	public ResponseEntity<?> sendPasswordResetEmail (@RequestParam String email, HttpServletRequest request) throws Exception;
	
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink (@RequestParam Integer uId, @RequestParam String code) throws Exception;
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword (@RequestBody  PswdResetRequest pswdResetRequest) throws Exception;
	
}
