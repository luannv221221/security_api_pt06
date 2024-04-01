package com.ra.security.jwt;

import com.ra.security.user_principal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    @Value("${expired}")
    private Long EXPIRED;
    @Value("${secret_key}")
    private String SECRET_KEY;
    private Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    public String generateToken(UserPrinciple userPrinciple){
        String token = Jwts.builder().
                setSubject(userPrinciple.getUsername()).
                setIssuedAt(new Date()).
                setExpiration(new Date(new Date().getTime()+EXPIRED)).
                signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
        return token;
    }

    public Boolean validateToken(String token){

        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException expiredJwtException){
            logger.error("expired token {}",expiredJwtException.getMessage());
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
