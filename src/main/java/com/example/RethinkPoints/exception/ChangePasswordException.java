package com.example.RethinkPoints.exception;
public class ChangePasswordException extends RuntimeException{
    public ChangePasswordException(String message){
        super(message);
    }
}