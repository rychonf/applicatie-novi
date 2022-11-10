package com.example.turnitup.DTO;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MessageDto {

    @NotNull
    private Long id;

    private String message;
    private LocalDate finalDate;
}
