package com.Project_Management.ServiceImpl;

import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Security.JwtHelper;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtHelper jwtHelper;
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
    public User userByEmail(String email) {
        User userEmailIsNotFound = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User email is not found"));
        return userEmailIsNotFound;
    }

    @Override
    public User getById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user id is not found"));
        return user;
    }


    @Override
    public UserDto updateUserProjectSize(UserDto userDto, int number) {
        User users = modelMapper.map(userDto, User.class);
        users.setProjectSize(userDto.getProjectSize());
        userRepo.save(users);
        return modelMapper.map(users, UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }



    @Override
    public List<UserDto> getAllUsers() {
        // Fetch all users from the repository
        List<User> allUsers = userRepo.findAll();

        // Map List<User> to List<UserDto>
        List<UserDto> userDtos = allUsers.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public User findUserProfileByJwt(String jwt) {
        String emailFromToken = jwtHelper.getEmailFromToken(jwt);
        return userByEmail(emailFromToken);
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
