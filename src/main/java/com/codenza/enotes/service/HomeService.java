package com.codenza.enotes.service;

public interface HomeService {

	Boolean verifyAccount(Integer uid, String verificationCode) throws Exception;
	
}
