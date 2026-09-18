package com.codenza.enotes.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.codenza.enotes.entity.User;
import com.codenza.enotes.exceptions.JwtAuthenticationException;
import com.codenza.enotes.exceptions.JwtExpiredException;
import com.codenza.enotes.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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

		String token = Jwts.builder().claims(claims).subject(user.getEmail())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)).signWith(getKey()).compact();

		return token;
	}

	private Key getKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);

	}

	@Override
	public String extractUsername(String token) {

		Claims claims = extractAllClaims(token);

		return claims.getSubject();

	}

	public String role(String token) {

		Claims claims = extractAllClaims(token);
		String role = (String) claims.get("role");
		return role;
	}

	private Claims extractAllClaims(String token) {

		try {

			return Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(token).getPayload();

		} catch (ExpiredJwtException e) {
			throw new JwtExpiredException("Your JWT token is expired");
		} catch (JwtException e) {
			throw new JwtAuthenticationException("Invalid JWT");
		} catch (Exception e) {
			throw e;
		}

	}

	private SecretKey decryptKey(String secretKey) {

		byte[] keyBytes = Decoders.BASE64.decode(secretKey);

		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public Boolean validateToken(String token, UserDetails userDetails) {

		String username = extractUsername(token);

		Boolean isExpired = isTokenExpired(token);

		if (username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired) {
			return true;
		}

		return false;
	}

	private Boolean isTokenExpired(String token) {

		Claims claims = extractAllClaims(token);

		Date expiryDate = claims.getExpiration();

		return expiryDate.before(new Date());
	}

}
