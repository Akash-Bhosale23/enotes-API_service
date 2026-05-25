package com.codenza.enotes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codenza.enotes.entity.User;
import com.codenza.enotes.repository.UserRespository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRespository userRespository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRespository.findByEmail(username);
		
		if(user==null) {
			
			throw new UsernameNotFoundException("Invalid Email");
			
			
		}
		
		return new CustomUserDetails(user);
	}

}
