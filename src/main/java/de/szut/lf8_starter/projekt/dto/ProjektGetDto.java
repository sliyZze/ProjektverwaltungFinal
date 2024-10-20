package de.szut.lf8_starter.projekt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
public class ProjektGetDto {

    private long id;

    private String bezeichnung;

    private Long verantwortlicherMitarbeiter;

    private long kundenId;

    private String kundenName;

    private String kommentar;

    private LocalDate startDatum;

    private LocalDate gepEndDatum;

    private LocalDate tatEndDatum;

    private Set<String> qualifikationen;

    private Set<Long> mitarbeiterIds;

    @JsonCreator
    public ProjektGetDto(long id,String bezeichnung,long verantwortlicherMitarbeiter, long kundenId, String kundenName, String kommentar, LocalDate startDatum, LocalDate gepEndDatum, LocalDate tatEndDatum, Set<String> qualifikationen, Set<Long>  mitarbeiter) {
        this.id = id;
        this.bezeichnung = bezeichnung;
        this.verantwortlicherMitarbeiter = verantwortlicherMitarbeiter;
        this.kundenId = kundenId;
        this.kundenName = kundenName;
        this.kommentar = kommentar;
        this.startDatum = startDatum;
        this.gepEndDatum = gepEndDatum;
        this.tatEndDatum = tatEndDatum;
        this.qualifikationen = qualifikationen;
        this.mitarbeiterIds = mitarbeiter;
    }
}
