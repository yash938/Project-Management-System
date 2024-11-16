package com.Project_Management.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(Jwt_Constant.JWT_HEADER);
        logger.info("Authorization {}",jwt);

        if(jwt != null && jwt.startsWith(Jwt_Constant.JWT_BEARER)){

           jwt = jwt.substring(7);

           try{
               SecretKey key = Keys.hmacShaKeyFor(Jwt_Constant.JWT_KEY.getBytes());
               Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                       .parseClaimsJws(jwt).getBody();

               String email = String.valueOf(claims.get("email"));
               String authorities = String.valueOf(claims.get("authorities"));
               List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

               Authentication authentication = new UsernamePasswordAuthenticationToken(email,null,auths);
               SecurityContextHolder.getContext().setAuthentication(authentication);
           }catch (IllegalArgumentException e){
               e.printStackTrace();
               logger.info("illegal argument while fetching the argument " + e.getMessage());
           }
           catch (ExpiredJwtException ex){
               logger.info("Given JWT is Expired " + ex.getMessage());
           }
           catch (MalformedJwtException ex){
               logger.info("Some changed has done in token "+ex.getMessage());
           }
           catch (Exception ex){
               ex.printStackTrace();
           }


        }else{
            if (jwt == null || !jwt.startsWith(Jwt_Constant.JWT_BEARER)) {
                logger.info("Missing or Invalid Authorization Header");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing or Invalid Authorization Header");
                return;
            }

        }
        filterChain.doFilter(request,response);
        }

    }

