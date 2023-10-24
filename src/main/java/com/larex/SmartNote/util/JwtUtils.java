package com.larex.SmartNote.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    private String secret ="How I Failed My Netflix Interview | Prime Reacts";

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claimsMap = new HashMap<String, Object>();

        String generatedToken = Jwts.builder()
                .addClaims(claimsMap)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+Util.JWT_TWO_MONTHS_EXP_TIME))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();

        return generatedToken;
    }

    public String extractEmail(String jwt) {

        return "";
    }

    private <T> T getClaimsFromToken(String token, Function<Claims,T> claimsTFunction){

    }

}
