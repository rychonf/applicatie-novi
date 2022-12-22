package com.example.turnitup.Exception;

public class BookingNotFoundException extends RuntimeException{

    public BookingNotFoundException(String message){
        super(message);
    }
}
