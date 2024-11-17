package com.Project_Management.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private Jwt_Helper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(Jwt_Constant.JWT_HEADER);
        logger.info("Authorization {}",jwt);

        String username = null;
        String token = null;

        if(jwt != null && jwt.startsWith(Jwt_Constant.JWT_BEARER)){

           token = jwt.substring(7);

           try{
               username=   jwtHelper.getUserNameFromToken(token);
                logger.info("Username from token {}",username);

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
            logger.error("Invalid token");
            }


        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if(username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token)){

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }



    }

        filterChain.doFilter(request,response);
        }

    }

