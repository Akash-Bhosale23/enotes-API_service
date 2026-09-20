package com.codenza.enotes.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.codenza.enotes.dto.PasswordChangeRequest;
import com.codenza.enotes.dto.UserResponse;
import com.codenza.enotes.endpoints.UserEndpoint;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController implements UserEndpoint{

	private final ModelMapper mapper;

	private final UserService userService;

	@Override
	public ResponseEntity<?> getProfile() {

		User loggedInUser = CommonUtil.getLoggedInUser();
		UserResponse userResponse = mapper.map(loggedInUser, UserResponse.class);

		return CommonUtil.createBuildResponse(userResponse, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> changePassword(PasswordChangeRequest changeRequest) {

		userService.changePassword(changeRequest);
		return CommonUtil.createBuildResponse("Password changed successfully", HttpStatus.OK);
	}

}
