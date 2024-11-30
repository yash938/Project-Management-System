package com.Project_Management.Controller;

import com.Project_Management.Dto.MessageResponse;
import com.Project_Management.Dto.UserDto;
import com.Project_Management.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user){
        UserDto createUser = userService.createUser(user);
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){
        UserDto updateUser = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> userById(@PathVariable Long userId){
        UserDto byUserId = userService.findByUserId(userId);
        return new ResponseEntity<>(byUserId, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

   @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        MessageResponse userDeleted = MessageResponse.builder().status(HttpStatus.OK).message("User deletes successfully").date(LocalDate.now()).build();
        return new ResponseEntity<>(userDeleted, HttpStatus.OK);
    }
}
