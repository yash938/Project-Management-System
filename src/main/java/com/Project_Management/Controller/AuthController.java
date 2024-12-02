package com.Project_Management.Controller;


import com.Project_Management.Dto.*;
import com.Project_Management.Entity.RefreshToken;
import com.Project_Management.Entity.User;
import com.Project_Management.Security.JwtHelper;
import com.Project_Management.Service.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private JwtHelper helper;

    @GetMapping("/generateRefreshToken")
    public ResponseEntity<JwtResponse> generateRefreshToken(@RequestBody RefreshTokenReq refreshToken){
        RefreshTokenDto byToken = refreshTokenService.findByToken(refreshToken.getRefreshToken());
        RefreshTokenDto refreshTokenDto = refreshTokenService.verifyToken(byToken);


        UserDto user = refreshTokenService.getUser(refreshTokenDto);
        String s = helper.generateToken(modelMapper.map(user,User.class));

        JwtResponse build = JwtResponse.builder().token(s).refreshTokenDto(byToken).user(user ).build();
        return ResponseEntity.ok(build);

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        log.info("Username {} , Password {} ",jwtRequest.getEmail(),jwtRequest.getPassword());

        this.doAuthenticate(jwtRequest.getEmail(),jwtRequest.getPassword());

        User user = (User)userDetailsService.loadUserByUsername(jwtRequest.getEmail());


        String token = helper.generateToken(user);
        RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());



        JwtResponse build = JwtResponse.builder().token(token).refreshTokenDto(refreshToken).user(modelMapper.map(user, UserDto.class)).build();
        return ResponseEntity.ok(build);
    }

    private void doAuthenticate(String email, String password) {
        try{
            Authentication authentication = new UsernamePasswordAuthenticationToken(email,password);
        }catch (BadCredentialsException ex){
            ex.getMessage();
        }
    }
}
