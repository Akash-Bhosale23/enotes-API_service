package com.codenza.enotes.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.codenza.enotes.dto.EmailRequest;
import com.codenza.enotes.dto.PasswordChangeRequest;
import com.codenza.enotes.dto.PswdResetRequest;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.exceptions.ResourceNotFoundException;
import com.codenza.enotes.repository.UserRepository;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;

import ch.qos.logback.core.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailSenderService mailSenderService;
	
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

	@Override
	public void sendPasswordResetEmail(String email, HttpServletRequest request) throws Exception {

		User user = userRepository.findByEmail(email);
		
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("Invalid email");
		}
		
		// Generate unique password reset token
		String passwordResetToken = UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User updatedUser = userRepository.save(user);
		
		String url = CommonUtil.getUrl(request);
		sendEmailRequest(updatedUser,url);
		
		
	}

	private void sendEmailRequest(User user, String url) throws Exception {
		
		String message = "Hi, <b>[[username]]</b> <br>" 
				+ "<p> You have requested to reset your password </p> <br>"
				+ "<p> Click the link below to change your password.. </p> <br>"
				+ "<p> <a href =[[url]]> Change password </p>"
				+ "<p>Ignore this email if you do remember your password, or you have not made the request.</P><br><br>"
				+ "<br>Thanks,<br> ENotes";
		
		message = message.replace("[[username]]", user.getFirstName());
		
		message = message.replace("[[url]]", url+"/api/v1/home/verify-pswd-link?uId=" + user.getId() + "&code="+ user.getStatus().getPasswordResetToken());

		
		EmailRequest emailRequest = EmailRequest.builder().to(user.getEmail())
				.title("Password Reset").subject("Password reset link").message(message).build();
		
		//send password reset email to user
		mailSenderService.sendEmail(emailRequest);
	}

	@Override
	public void verifyPswdResetLink(Integer uId, String code) throws Exception {
		User user= userRepository.findById(uId).orElseThrow(()->new ResourceNotFoundException("Invalid User"));	
		verifyPasswordResetToken(user.getStatus().getPasswordResetToken(), code);
	}

	private void verifyPasswordResetToken(String existedToken, String reqToken) {

		if(StringUtils.hasText(reqToken)) {
			
			if(!StringUtils.hasText(existedToken)) {
				throw new IllegalArgumentException("Password is already reset");
			}
			
			if(!existedToken.equals(reqToken)) {
				throw new IllegalArgumentException("Invalid url");
			}
			
		}else {
			throw new IllegalArgumentException("Invalid token");
		}
		
	}

	@Override
	public void resetPassword(PswdResetRequest pswdResetRequest) throws ResourceNotFoundException {

		User user= userRepository.findById(pswdResetRequest.getUId()).orElseThrow(()-> new ResourceNotFoundException("Invalid User"));
		String encodedPassword= passwordEncoder.encode(pswdResetRequest.getNewPassword());
		user.setPassword(encodedPassword);
		user.getStatus().setPasswordResetToken(null);
		userRepository.save(user);
	}
	


}
