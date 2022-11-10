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

    @Column(unique = true)
    private String djName;

    private String musicSpecialty;
    private double pricePerHour;


    @ManyToOne
    @JoinColumn(name = "fk_rating_id")
    private Rating rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mixtape_id", referencedColumnName = "id")
    private Mixtape mixtape;


}
