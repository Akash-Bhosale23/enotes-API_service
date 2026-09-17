package com.codenza.enotes.service;

import com.codenza.enotes.dto.PasswordChangeRequest;
import com.codenza.enotes.dto.PswdResetRequest;
import com.codenza.enotes.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public void changePassword(PasswordChangeRequest passwordChangeRequest);

	public void sendPasswordResetEmail(String email, HttpServletRequest request) throws Exception;

	public void verifyPswdResetLink(Integer uId, String code) throws Exception;

	public void resetPassword(PswdResetRequest pswdResetRequest) throws ResourceNotFoundException;
	
}
