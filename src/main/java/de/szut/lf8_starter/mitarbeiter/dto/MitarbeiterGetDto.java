package de.szut.lf8_starter.mitarbeiter.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MitarbeiterGetDto {

    @NotBlank
    private long id;

    @NotBlank
    private List<QualifikationGetDto> skillSet;
}
