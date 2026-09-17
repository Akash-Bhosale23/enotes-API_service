package com.codenza.enotes.service;

import com.codenza.enotes.dto.PasswordChangeRequest;

public interface UserService {

	public void changePassword(PasswordChangeRequest passwordChangeRequest);
	
}
