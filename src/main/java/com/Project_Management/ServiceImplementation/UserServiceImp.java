package com.Project_Management.ServiceImplementation;

import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Entity.User;
import com.Project_Management.Exception.UserNotFound;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<User> existingUser = userRepo.findByEmail(userdto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserNotFound("User with email " + userdto.getEmail() + " already exists.");
        }

        // Mapping and saving new user logic
        User user = modelMapper.map(userdto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

}
