package com.example.turnitup.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookingDto {

    @NotNull
    private LocalDate bookingDate;

    @NotNull
    private double hoursBooked;

    private double totalPrice;

}
