package com.example.turnitup.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

@Table(name = "organisation")
@DynamicUpdate
public class Organisation {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int organizedEvents;

    @ManyToOne
    @JoinColumn(name = "fk_message")
    private Message message;

    @OneToMany
    private List<Booking> booking;


}
