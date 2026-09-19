package com.codenza.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.codenza.enotes.endpoints.HomeEndpoint;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.service.HomeService;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController implements HomeEndpoint{
	
	Logger log= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uId, @RequestParam String code) throws Exception{
		
		log.info("HomeController : verifyUserAccount() : Execution start");
		
		Boolean verifyAccount = homeService.verifyAccount(uId, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		
		log.info("HomeController : verifyUserAccount : Execution end");
		return CommonUtil.createErrorResponseMessage("Incorrect credencials", HttpStatus.BAD_REQUEST);
	}
	
	@Override
	public ResponseEntity<?> sendPasswordResetEmail (@RequestParam String email, HttpServletRequest request) throws Exception{
		
		userService.sendPasswordResetEmail(email, request);
		
		return CommonUtil.createBuildResponseMessage("Link is sent to your email...", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLink (@RequestParam Integer uId, @RequestParam String code) throws Exception{
		
		userService.verifyPswdResetLink(uId, code);
		
		return CommonUtil.createBuildResponseMessage("Link verified...", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> resetPassword (@RequestBody  PswdResetRequest pswdResetRequest) throws Exception{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createBuildResponseMessage("Password reset successfully...", HttpStatus.OK);
		
	}
}
