package com.Project_Management.ServiceImpl;

import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        User users = modelMapper.map(userDto, User.class);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        User createUser = userRepo.save(users);
        return modelMapper.map(createUser, UserDto.class);
    }
    @Override
    public UserDto findByUserId(Long userId) {
        User user_id = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User id is not found"));
        return modelMapper.map(user_id, UserDto.class);
    }



    @Override
    public UserDto updateUserProjectSize(UserDto userDto, int number) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user_id = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User id is not found"));

        user_id.setFullName(userDto.getFullName());
        user_id.setEmail(userDto.getEmail());
        user_id.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user_id.setProjectSize(userDto.getProjectSize());
        user_id.setAssignIssue(userDto.getAssignIssue());

        User updateUser = userRepo.save(user_id);
        return modelMapper.map(updateUser, UserDto.class);
    }
}
