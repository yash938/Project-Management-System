package com.Project_Management.Controller;

import com.Project_Management.Dtos.MessageResponse;
import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class projectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;



}
