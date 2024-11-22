package com.Project_Management.Controller;

import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> findByEmail(@PathVariable String email){
        UserDto byEmail = userService.findByEmail(email);
        return new ResponseEntity<>(byEmail,HttpStatus.CREATED);
    }

    @GetMapping("/hello")
    public String user(){
        return "hello";
    }




}
