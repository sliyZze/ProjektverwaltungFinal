package de.szut.lf8_starter.mitarbeiter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
//Author: Tobias
@Getter
@Setter
public class MitarbeiterCreateDto {

    @NotBlank
    private long id;
}
