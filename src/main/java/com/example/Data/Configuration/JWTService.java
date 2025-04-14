package com.example.Data.Configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Component
public class JWTService {

	private String secretKey="";
	
	public JWTService() {
		try {
			KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
			SecretKey sK=keyGen.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sK.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
	}
	
	public String generateToken(String username) {
	Map<String,Object> claim=new HashMap<>();
	return Jwts.builder().claims().add(claim).subject(username)
		    .issuedAt(new Date(System.currentTimeMillis()))
		    .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30)) // 30 days
		    .and()
		    .signWith(getKey())
		    .compact();


	}

	private SecretKey getKey() {
	   byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUserName(String token) {
		
		return extractClaim(token,Claims::getSubject);
	}
	private<T> T extractClaim(String token,Function<Claims,T> claimResolver) {
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build().parseSignedClaims(token).getPayload();
	}

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        System.out.println("Token: " + token);
        System.out.println("Secret key: " + secretKey);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
