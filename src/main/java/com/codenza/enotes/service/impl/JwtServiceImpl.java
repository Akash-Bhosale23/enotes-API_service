package com.codenza.enotes.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.codenza.enotes.entity.User;
import com.codenza.enotes.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Override
	public String generateToken(User user) {

		Map<String, Object> claims = new HashMap<>();

		claims.put("id", user.getId());
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());

		String token = Jwts.builder()
				.claims(claims)
				.subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(getKey())
				.compact();

		return token;
	}

	private Key getKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);

	}

}
