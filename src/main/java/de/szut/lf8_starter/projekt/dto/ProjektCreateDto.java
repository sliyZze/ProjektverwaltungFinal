package de.szut.lf8_starter.projekt.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import de.szut.lf8_starter.projekt.QualifikationDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class ProjektCreateDto {

    @NotBlank
    @Size(min = 3, message = "at least length of 3")
    private String bezeichnung;

    @NotBlank
    private long verantwortlicherMitarbeiter;

    @NotBlank
    private long kundenId;

    @NotBlank
    @Size(max = 20, message = "at least length of 3")
    private String kundenName;

    @NotBlank
    @Size(max = 255)
    private String kommentar;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDatum;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate gepEndDatum;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate tatEndDatum;

    @NotBlank
    private Map<String, QualifikationDetailCreateDto> qualifikationen = new HashMap<>();

//    private HashMap<String,Long> qualifis;

    @JsonCreator
    public ProjektCreateDto() {

    }
}
