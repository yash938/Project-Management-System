package com.Project_Management.Service;

import com.Project_Management.Entity.User;
import com.Project_Management.Exception.UserNotFound;
import com.Project_Management.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return userRepo.findByEmail(email).orElseThrow(() -> new UserNotFound("user name is not found...!!"+email));

    }
}
