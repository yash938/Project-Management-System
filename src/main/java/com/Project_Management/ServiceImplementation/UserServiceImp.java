package com.Project_Management.ServiceImplementation;

import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Entity.User;
import com.Project_Management.Exception.UserNotFound;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;



    @Override
    public UserDto createUser(UserDto userdto) {
        // Check if the password is not null
        if (userdto.getPassword() == null || userdto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        // Mapping and saving new user logic
        User user = modelMapper.map(userdto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }


    @Override
    public UserDto findByEmail(String email) {
        User userEmailIsNotFound = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User email is not found"));
        UserDto userEmail = modelMapper.map(userEmailIsNotFound, UserDto.class);
        return userEmail;
    }

    @Override
    public User findByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User id is not found"));
        return user;
    }

    @Override
    public User updateUserProjectSize(User user, int number) {
        user.setProjectSize(user.getProjectSize() + number);

        return userRepo.save(user);
    }

}
