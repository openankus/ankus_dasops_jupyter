package org.ankus.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.*;

public class JwtTokenProvider {
    private static String SALT = "djslzja";
    //private long tokenValidTime = 1000L * 60 * 60;

    // JWT 토큰 생성
    public static String createToken(String username, String userPk) {
        //make jwt header
        Map<String, Object> jwtHeader = new HashMap<>();
        jwtHeader.put("typ", "JWT");
        jwtHeader.put("alg", "HS256");
        jwtHeader.put("regDate", System.currentTimeMillis());

        //make claim
        Map<String, Object> claim = new HashMap<>();
        claim.put("id", userPk);

        String token = Jwts.builder()
                .setSubject(username)
                .setHeader(jwtHeader)
                .setClaims(claim)
                .signWith(SignatureAlgorithm.HS256, SALT)
                .compact();

       return token;
    }

    // 토큰의 유효성 + 만료일자 확인
    public static String validateToken(String jwtToken) {
        try {
            Claims verified = Jwts.parser().setSigningKey(SALT).parseClaimsJws(jwtToken).getBody(); //throw
            return (String) verified.get("id");   //throw
        } catch (Exception e) {
            return null;
        }
        //claims.getBody().getExpiration().before(new Date());
    }}