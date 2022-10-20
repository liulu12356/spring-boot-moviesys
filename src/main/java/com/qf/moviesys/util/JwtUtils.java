package com.qf.moviesys.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtUtils {

    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String data) {
        return Jwts.builder().signWith(key).setSubject(data).compact();
    }

    public static Jws<Claims> getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }


    public static void main(String[] args) {
        String username = "root";

        // 生成token
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // String token = Jwts.builder().signWith(key).setSubject(username).compact();
        // System.out.println(token);

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb290In0.Jtvm3IBsHEXoVFQp7xG6jTgfQXTUGSjldyOotRs9F_U";

        // 解析、校验token
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
        System.out.println(subject);
    }

}
