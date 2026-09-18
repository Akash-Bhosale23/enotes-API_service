package com.codenza.enotes.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.codenza.enotes.entity.User;

public class CustomUserDetails implements UserDetails{
	
	private User user;

	public CustomUserDetails(User user) {

		super();
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
//		List<SimpleGrantedAuthority> authority= new ArrayList<>();
//		
//		user.getRoles().forEach(r->{
//			authority.add(new SimpleGrantedAuthority(r.getName()));
//		});
//		
//		return authority;
		
		 return user.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
	                .collect(Collectors.toList());
	}
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
