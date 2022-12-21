package com.example.turnitup.Exception;

public class DJNotFoundException extends RuntimeException {
    public String DJNotFoundException(){
        return "DJ with this Name doesn't exists";
    }
}