package com.example.turnitup.Exception;

public class OrganisationNotFoundException extends RuntimeException{

    public OrganisationNotFoundException(String message){
        super(message);
    }
}
