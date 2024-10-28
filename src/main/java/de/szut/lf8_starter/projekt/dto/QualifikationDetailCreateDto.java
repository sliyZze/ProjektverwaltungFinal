package de.szut.lf8_starter.projekt.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QualifikationDetailCreateDto {
    @NotBlank
    private Long soll;
}
