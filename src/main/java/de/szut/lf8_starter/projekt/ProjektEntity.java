package de.szut.lf8_starter.projekt;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "project")
public class ProjektEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String bezeichnung;

    private long verantwortlicherMitarbeiter;

    private long kundenId;

    private String kundenName;

    private String kommentar;

    private LocalDate startDatum;
    private LocalDate gepEndDatum;
    private LocalDate tatEndDatum;

    @ElementCollection
    @CollectionTable(name = "projekt_mitarbeiter", joinColumns = @JoinColumn(name = "projekt_id"))
    @Column(name = "mitarbeiter_id")
    private Set<Long> mitarbeiterIds;

    @ElementCollection
    @CollectionTable(name = "projekt_qualifikationen", joinColumns = @JoinColumn(name = "projekt_id"))
    @MapKeyColumn(name = "qualifikation")
    private Map<String, QualifikationDetail> qualifikationen = new HashMap<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "projekt_id")
    private List<MitarbeiterQualifikation> usedEmployeeQualifikationen = new ArrayList<>();

}
