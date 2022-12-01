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
public class MixtapeDto {

    private String fileName;
    private LocalDate dateUploaded;
    private int timesPlayed;
}
