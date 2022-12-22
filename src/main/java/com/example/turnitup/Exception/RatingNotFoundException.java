package com.example.turnitup.Exception;

public class RatingNotFoundException extends RuntimeException{
     public String ratingNotFoundException(){
         return "Rating with this id doesn't exist";
     }
}
