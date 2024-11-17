package com.Project_Management.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ExceptionManage> userNameNotFoundException(UserNotFound ex){
        ExceptionManage exceptionManage = new ExceptionManage(ex.getMessage(), HttpStatus.NOT_FOUND, LocalDate.now(), true);
        return new ResponseEntity<>(exceptionManage,HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleApiResponse(MethodArgumentNotValidException ex){
            Map<String,String> errors = new HashMap<>();

            ex.getBindingResult().getAllErrors().forEach((error)->{
                String field = ((FieldError) error).getField();
                String defaultMessage = error.getDefaultMessage();
                errors.put(field,defaultMessage);
            });
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
