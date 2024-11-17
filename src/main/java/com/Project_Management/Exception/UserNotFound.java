package com.Project_Management.Exception;

public class UserNotFound extends RuntimeException{
    public UserNotFound(){
        super("User is not found");
    }

    public UserNotFound(String message){
        super(message);
    }
}
