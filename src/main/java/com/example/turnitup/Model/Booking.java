package com.example.turnitup.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Table(name = "booking")
@DynamicUpdate
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;

    private double hoursBooked;
    private double totalPrice;

    @ManyToOne
////    @JoinColumn(name = "booking_dj")
    private DJ dj;

    @ManyToOne
    @JoinColumn(name = "booking_organisation")
    private Organisation organisation;
}
