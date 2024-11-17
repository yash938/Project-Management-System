package com.Project_Management.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class Jwt_Helper {

        Logger logger = LoggerFactory.getLogger(Jwt_Helper.class);
        public String getUserNameFromToken(String token){
                logger.info("Token {}",token);
                return getClaimsFromToken(token,Claims::getSubject);
        }

        public <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
            final Claims claims = getAllClaimsFromToken(token);
            return claimsResolver.apply(claims);
        }

        public Claims getAllClaimsFromToken(String token){
                try{
                        return Jwts.parserBuilder().setSigningKey(Jwt_Constant.JWT_KEY).build().parseClaimsJws(token).getBody();
                }catch (Exception e){
                        logger.error("Failed to parse token {}",e.getMessage());
                        throw new RuntimeException("token not parsing or INVALID TOKEN");
                }
        }
        public Boolean isTokenExpired(String token) {
                final Date expiration = getExpirationFromToken(token);
                return expiration.before(new Date());
        }

        public Date getExpirationFromToken(String token){
                return getClaimsFromToken(token,Claims::getExpiration);
        }

        public String generateToken(UserDetails userDetails){
                Map<String, Object> claims = new HashMap<>();
                claims.put("email",userDetails.getUsername());

                return Jwts.builder()
                        .setExpiration(new Date(System.currentTimeMillis() + Jwt_Constant.TOKEN_VALID))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setSubject(userDetails.getUsername())
                        .setClaims(claims)
                        .signWith(SignatureAlgorithm.ES256,Jwt_Constant.JWT_KEY)
                        .compact();
        }
}
