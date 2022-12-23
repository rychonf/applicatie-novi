package com.example.turnitup.Model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity

@Table(name = "mixtape")
@DynamicUpdate
public class Mixtape {

    @Id
    @GeneratedValue
    private Long id;
    private String fileName;
    private LocalDate dateUploaded;

    private int timesPlayed;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] docFile;

    @OneToOne(mappedBy = "mixtape")
    private DJ dj;

}
