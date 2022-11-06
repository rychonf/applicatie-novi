package com.example.turnitup.DTO;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DJDto {

    @NotNull
    private long id;

    private String name;
    private String musicSpecialty;
    private double pricePerHour;

//    private char typeOfSubscription;

}
