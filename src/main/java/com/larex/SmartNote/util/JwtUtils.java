package com.larex.SmartNote.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private String secret = "How I Failed My Netflix Interview | Prime ReactsHow I Failed My Netflix Interview | Prime ReactsHow I Failed My Netflix Interview | Prime ReactsHow I Failed My Netflix Interview | Prime ReactsHow I Failed My Netflix Interview | Prime Reacts";

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claimsMap = new HashMap<String, Object>();

        return Jwts.builder()
                .addClaims(claimsMap)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Util.JWT_TWO_MONTHS_EXP_TIME))
                .signWith(signInKey(secret), SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractEmail(String jwt) {

        return getClaimsFromToken(jwt,Claims::getSubject);
    }

    private Key signInKey(String secret) {
        byte[] keyByte = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyByte);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = Jwts.parserBuilder().setSigningKey(signInKey(secret)).build().parseClaimsJws(token).getBody();
        return claimsTFunction.apply(claims);
    }

    public String getUserNameFromToken(String token){
        return getClaimsFromToken(token,Claims::getSubject);
    }
    public Date getExpirationDateFromToken(String token){
        return getClaimsFromToken(token,Claims::getExpiration);
    }

    public boolean validateToken(String token,UserDetails userDetails){
        final String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        final Date exp_date = getExpirationDateFromToken(token);
        return exp_date.before(new Date());
    }

}
