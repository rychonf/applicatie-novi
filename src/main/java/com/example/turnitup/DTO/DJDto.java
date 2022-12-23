package com.example.turnitup.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DJDto {

    @NotEmpty
    private String djName;

    @NotEmpty
    private String musicSpecialty;


    private double pricePerHour;

}
