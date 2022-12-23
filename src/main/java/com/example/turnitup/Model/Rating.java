package com.example.turnitup.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Table(name = "rating")

public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate dateRated;

    @Min(1)
    @Max(10)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "dj_rated", referencedColumnName = "djName")
    private DJ dj;




}
