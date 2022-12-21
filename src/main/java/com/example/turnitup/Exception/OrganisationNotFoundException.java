package com.example.turnitup.Exception;

public class OrganisationNotFoundException extends RuntimeException{

    public String organisationNotfoundException(){
        return "Organisation with this name doesn't exist";
    }
}
