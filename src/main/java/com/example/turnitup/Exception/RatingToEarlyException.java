package com.example.turnitup.Exception;

public class RatingToEarlyException extends RuntimeException{

    public RatingToEarlyException(String message){
        super(message);
    }
}
