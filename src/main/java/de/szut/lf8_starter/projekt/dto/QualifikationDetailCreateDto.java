package de.szut.lf8_starter.projekt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
//Author: Martin
@Setter
@Getter
public class QualifikationDetailCreateDto {
    @NotBlank
    private Long soll;
}
