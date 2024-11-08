package de.szut.lf8_starter.projekt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
//Author: Martin
@Getter
@Setter
public class ProjektUpdateDto {

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
    private LocalDate gepEndDatum;

}
