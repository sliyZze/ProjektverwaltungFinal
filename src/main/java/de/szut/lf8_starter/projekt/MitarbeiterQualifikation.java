package de.szut.lf8_starter.projekt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
//Author: Eren
@Getter
@Setter
@Entity
@Table(name = "projekt_mitarbeiter_qualifikationen")
public class MitarbeiterQualifikation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "projekt_id")
    private long projektId;

    @Column(name = "mitarbeiter_id")
    private long mitarbeiterId;

    @Column(name = "qualifikation")
    private String qualifikation;

    // Getter und Setter
}

