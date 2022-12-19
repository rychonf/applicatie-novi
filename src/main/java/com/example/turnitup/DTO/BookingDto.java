package com.example.turnitup.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BookingDto {
    private LocalDate bookingDate;

    private double hoursBooked;
    private double totalPrice;

}
