package com.codenza.enotes.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.codenza.enotes.dto.EmailRequest;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MailSenderService {

	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String mailFrom;
	
	public void sendEmail(EmailRequest emailRequest) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom(mailFrom, emailRequest.getTitle());
		helper.setTo(emailRequest.getTo());
		helper.setSubject(emailRequest.getSubject());
		helper.setText(emailRequest.getMessage(),true);
		mailSender.send(message);

	}
	
	
}
