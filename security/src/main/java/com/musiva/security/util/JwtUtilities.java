package com.musiva.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtilities {

    private final String SECRET_KEY;
    private final long EXPIRATION_TIME;
    private final SignatureAlgorithm SIGNATURE_ALGORITHM;

    public JwtUtilities(String key, long expirationTime, SignatureAlgorithm signatureAlgorithm) {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(key.getBytes());
        this.EXPIRATION_TIME = expirationTime;
        this.SIGNATURE_ALGORITHM = signatureAlgorithm;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public boolean validateToken(String token, String username) {
        String usernameToken = extractUsername(token);
        return usernameToken.equals(username) && !isExpired(token);
    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String createToken(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        return generateToken(claims, username);
    }

    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().signWith(SIGNATURE_ALGORITHM, SECRET_KEY).setHeaderParam("typ", "JWT").addClaims(claims)
                .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

}
