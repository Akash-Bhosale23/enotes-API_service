package com.codenza.enotes.service.impl;

import org.springframework.stereotype.Service;

import com.codenza.enotes.entity.AccountStatus;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.exceptions.SuccessException;
import com.codenza.enotes.repository.UserRepository;
import com.codenza.enotes.service.HomeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class HomeServiceImpl implements HomeService{
	
	private final UserRepository userRepository;

	@Override
	public Boolean verifyAccount(Integer uid, String verificationCode) throws Exception {
		
		log.info("HomeServiceImpl : verifyAccount() : start");
		User user = userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("Invalid user"));
		
		if(user.getStatus().getVerificationCode()==null) {
			log.info("Message : Account is already verified");
			throw new SuccessException("Account is already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepository.save(user);
			log.info("Message : Account verification success");

			return true;
		}
		log.info("HomeServiceImpl : verifyAccount() : end");

		return false;
	}

}
