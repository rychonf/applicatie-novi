package com.example.turnitup.DTO;

import com.example.turnitup.Model.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserDto {

    private String username;
    private String password;
    private String email;
    private Boolean enabled;
    private char typeOfSubscription;

    @JsonSerialize
    private Set<Authority> authorities;
}
