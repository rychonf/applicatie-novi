package com.example.turnitup.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private int rating;

    @OneToMany
    private List<DJ> dj;


}
