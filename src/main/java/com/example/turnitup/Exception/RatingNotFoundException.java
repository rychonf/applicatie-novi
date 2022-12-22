package com.example.turnitup.Exception;

public class RatingNotFoundException extends RuntimeException{
     public RatingNotFoundException(String message){
         super(message);
     }
}
