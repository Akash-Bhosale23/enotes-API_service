package com.codenza.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.service.HomeService;
import com.codenza.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;

	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uId, @RequestParam String code) throws Exception{
		
		Boolean verifyAccount = homeService.verifyAccount(uId, code);
		
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		
		return CommonUtil.createErrorResponseMessage("Incorrect credencials", HttpStatus.BAD_REQUEST);
	}
	
}
