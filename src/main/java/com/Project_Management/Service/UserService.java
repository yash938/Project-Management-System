package com.Project_Management.Service;


import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Entity.User;

public interface UserService {
    public UserDto createUser(UserDto user);
    User findByEmail(String email);
    User findByUserId(Long userId);
    User updateUserProjectSize(User user,int number);

}
