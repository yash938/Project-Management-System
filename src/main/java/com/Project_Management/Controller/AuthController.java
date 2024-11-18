package com.Project_Management.Controller;

import com.Project_Management.Dtos.JwtRequest;
import com.Project_Management.Dtos.JwtResponse;
import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private Jwt_Helper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    // Sign-up method
    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> createUser(@RequestBody JwtRequest jwtRequest) {
        logger.info("Username {},Password {}",jwtRequest.getEmail(),jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtHelper.generateToken(userDetails);

        JwtResponse build = JwtResponse.builder().token(token).user(modelMapper.map(userDetails, UserDto.class)).build();
        return new ResponseEntity<>(build,HttpStatus.CREATED);

    }

    // Authenticate method to validate user credentials
    private void doAuthenticate(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
}
