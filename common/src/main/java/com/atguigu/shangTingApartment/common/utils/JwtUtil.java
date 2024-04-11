package com.atguigu.shangTingApartment.common.utils;

import com.atguigu.shangTingApartment.common.exceptions.ApartmentCustomException;
import com.atguigu.shangTingApartment.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    private static long tokenExpiration = 60 * 60 * 1000L;
    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());

    public static String createToken(Long userId, String username,String avatarUrl) {
        String token = Jwts.builder().
                setSubject("USER_INFO").
                setExpiration(new Date(System.currentTimeMillis() + tokenExpiration)).
                claim("userId", userId).
                claim("username", username).
                claim("avatarUrl", avatarUrl).
                signWith(tokenSignKey).
                compressWith(CompressionCodecs.GZIP).
                compact();
        return token;
    }

    public static Claims parseToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().
                    setSigningKey(tokenSignKey).
                    build().parseClaimsJws(token);
            return claimsJws.getBody();

        } catch (ExpiredJwtException e) {
            throw new ApartmentCustomException(ResultCodeEnum.TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new ApartmentCustomException(ResultCodeEnum.TOKEN_INVALID);
        }
    }
}