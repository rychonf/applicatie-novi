package com.example.turnitup.Exception;

public class BadRequestException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public BadRequestException(String message){
        super(message);
    }
}
