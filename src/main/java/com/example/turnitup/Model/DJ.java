package com.example.turnitup.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter


@Table(name = "DJ")
@DynamicUpdate
public class DJ {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String musicSpecialty;
    private double pricePerHour;
    private char typeOfSubscription;

    @ManyToOne
    @JoinColumn(name = "fk_rating_id")
    private Rating rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_mixtape_id")
    private Mixtape mixtape;


}
