package com.codenza.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codenza.enotes.dto.PasswordChangeRequest;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.repository.UserRepository;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void changePassword(PasswordChangeRequest passwordChangeRequest) {
		
		User loggedInUser= CommonUtil.getLoggedInUser();
		
		if(!passwordEncoder.matches(passwordChangeRequest.getOldPassword(),loggedInUser.getPassword())) {
			 throw new IllegalArgumentException("Entered old password is not correct...");
		}
		
		String encodePassword = passwordEncoder.encode(passwordChangeRequest.getNewPassword());
		
		loggedInUser.setPassword(encodePassword);
		
		userRepository.save(loggedInUser);
		
	}

}
