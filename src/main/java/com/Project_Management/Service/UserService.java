package com.Project_Management.Service;


import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.User;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    UserDto findByUserId(Long userId);
    User userByEmail(String email);
    UserDto updateUserProjectSize(UserDto userDto,int number);

    void deleteUser(Long userId);
    List<UserDto> getAllUsers();
    public User findUserProfileByJwt(String jwt);
    UserDto updateUser(Long userId, UserDto userDto);

}
