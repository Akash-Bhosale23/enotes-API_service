package com.codenza.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.PswdResetRequest;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.service.HomeService;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uId, @RequestParam String code) throws Exception{
		
		Boolean verifyAccount = homeService.verifyAccount(uId, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		
		return CommonUtil.createErrorResponseMessage("Incorrect credencials", HttpStatus.BAD_REQUEST);
	}
	
	
	@PostMapping("/send-reset-email")
	public ResponseEntity<?> sendPasswordResetEmail (@RequestParam String email, HttpServletRequest request) throws Exception{
		
		userService.sendPasswordResetEmail(email, request);
		
		return CommonUtil.createBuildResponseMessage("Link is sent to your email...", HttpStatus.OK);
	}
	
	@GetMapping("/verify-pswd-link")
	public ResponseEntity<?> verifyPasswordResetLink (@RequestParam Integer uId, @RequestParam String code) throws Exception{
		
		userService.verifyPswdResetLink(uId, code);
		
		return CommonUtil.createBuildResponseMessage("Link verified...", HttpStatus.OK);
	}
	
	@PostMapping("/reset-pswd")
	public ResponseEntity<?> resetPassword (@RequestBody  PswdResetRequest pswdResetRequest) throws ResourceNotFoundException{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset successfully...", HttpStatus.OK);
		
	}
}
