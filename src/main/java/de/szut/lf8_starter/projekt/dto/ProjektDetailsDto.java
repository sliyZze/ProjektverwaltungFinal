package de.szut.lf8_starter.projekt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProjektDetailsDto {

    @NotBlank
    private long id;

    @NotBlank
    private String bezeichnung;

    @NotBlank
    private LocalDate startDatum;

    @NotBlank
    private LocalDate endDatum;

    @NotBlank
    private List<String> rollen;


}
