package com.qf.moviesys.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
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
        // Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        //
        // final String encode = Encoders.BASE64.encode(key.getEncoded());
        // System.out.println(encode);
        //
        // String token = Jwts.builder().signWith(key).setSubject(username).compact();
        // System.out.println(token);

        String key1="b9VkIFV3YCKgCOGx7UBgOdjtenNqqq0brRkllPLfRGw=";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyb290In0.jyYi-Dswxh-ihb_PMPMk-md0IG205BD7qdYb9GA-a04";
        final byte[] decode = Decoders.BASE64.decode(key1);
        final SecretKey secretKey = Keys.hmacShaKeyFor(decode);

        // 解析、校验token
        String subject = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
        System.out.println(subject);
    }

}
