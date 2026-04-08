package com.codenza.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codenza.enotes.entity.AccountStatus;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.exceptions.SuccessException;
import com.codenza.enotes.repository.UserRespository;
import com.codenza.enotes.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private UserRespository userRepository;

	@Override
	public Boolean verifyAccount(Integer uid, String verificationCode) throws Exception {
		
		User user = userRepository.findById(uid).orElseThrow(()->new ResourceNotFoundException("Invalid user"));
		
		if(user.getStatus().getVerificationCode()==null) {
			throw new SuccessException("Account is already verified");
		}
		
		if(user.getStatus().getVerificationCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerificationCode(null);
			
			userRepository.save(user);
			
			return true;
		}
		
		return false;
	}

}
