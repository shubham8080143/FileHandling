package com.FileHandling.JwtSecurity;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private final String SECRET_KEY = "d9e6cdbbcccd7692f670a803e76a0102e06a73089152374350aa064a1b35b599c499883e508acc42d693c3e4064418ab119468c1b29922d6c3e4721ae6c86cf91fe3e6a1f683f6a6b99602c5d32d58c3a6b08ac3ac637f06aa3125d3eb66a25eaf2c0b58d35a5b09a8a98f08c9fde8e2fb2f2fff9cd02a803c6e1acc61f446dcb3009d805e8829d820a8593755b39f171694f1c78fbc8e92afb8d142d4722be62aa612434f90b809991612cf997b08c37c5231fa30a9b832e0f501392d7646a7eb69e9458340f8a25414e24fb286f831ccd936dd10107792ffaf440a612df62a96d643153494852b29844bde90a3e0489088685dad33ff8fc0a8039991cf8ee9";

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token, String username) {
		return username.equals(extractUsername(token)) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody()
				.getExpiration().before(new Date());
	}

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
}
