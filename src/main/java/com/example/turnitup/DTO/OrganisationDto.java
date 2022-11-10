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
public class OrganisationDto {

    @NotNull
    private Long id;

    private String name;
    private int organizedEvents;

}
