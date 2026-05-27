package com.codenza.enotes.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.codenza.enotes.dto.EmailRequest;
import com.codenza.enotes.dto.LoginRequest;
import com.codenza.enotes.dto.LoginResponse;
import com.codenza.enotes.dto.UserDTO;
import com.codenza.enotes.entity.AccountStatus;
import com.codenza.enotes.entity.Role;
import com.codenza.enotes.entity.User;
import com.codenza.enotes.repository.RoleRepository;
import com.codenza.enotes.repository.UserRespository;
import com.codenza.enotes.security.CustomUserDetails;
import com.codenza.enotes.service.JwtService;
import com.codenza.enotes.service.UserService;
import com.codenza.enotes.util.CommonUtil;
import com.codenza.enotes.util.Validation;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRespository userRespository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private Validation validation;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private MailSenderService mailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;

	@Override
	public Boolean register(UserDTO userDTO, String url) throws Exception {

		validation.userValidation(userDTO);

		User user = mapper.map(userDTO, User.class);

		setRole(userDTO, user);
		
		AccountStatus status=AccountStatus.builder()
				.isActive(false)
				.verificationCode(UUID.randomUUID().toString())
				.build();

		user.setStatus(status);
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRespository.save(user);

		if (!ObjectUtils.isEmpty(savedUser)) {

			// send email
			emailSend(savedUser);

			return true;
		} else {

			return false;
		}

	}

	private void emailSend(User savedUser) throws Exception {

		  // This automatically builds the URL based on the current request
        String verificationUrl = CommonUtil.buildVerificationUrl(savedUser.getId(), savedUser.getStatus().getVerificationCode());
		
		String message = "Hi, <b>[[username]]</b> <br> Your account is registered successfully on Enotes <br>"
				+ "<br> click the link below to verify your account <br>" + "<a href='[[url]]'>Click Here</a><br>"
				+"<br>Thanks,<br> ENotes";
		
		message = message.replace("[[username]]", savedUser.getFirstName());
		
		message = message.replace("[[url]]", verificationUrl);

		
		EmailRequest emailRequest = EmailRequest.builder().to(savedUser.getEmail())
				.title("Account creation conformation").subject("Enotes account creation").message(message).build();

		mailService.sendEmail(emailRequest);
	}

	private void setRole(UserDTO userDTO, User user) {

		List<Integer> reqRoleId = userDTO.getRoles().stream().map(r -> r.getId()).toList();

		List<Role> roles = roleRepository.findAllById(reqRoleId);
		user.setRoles(roles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {

		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		
		if(authenticate.isAuthenticated()) {
			CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
			String token=jwtService.generateToken(customUserDetails.getUser());
			
			LoginResponse loginResponse =LoginResponse.builder()
					.user(mapper.map(customUserDetails.getUser(), UserDTO.class))
					.token(token)
					.build();
			return loginResponse;
		}
		

		return null;
	}

}
