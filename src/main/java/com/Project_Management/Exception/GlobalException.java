package com.Project_Management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ExceptionManage> userNameNotFound(UsernameNotFoundException ex){
        ExceptionManage exceptionManage = new ExceptionManage(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDate.now(), true);
        return new ResponseEntity<>(exceptionManage,HttpStatus.NOT_FOUND);
    }
}
