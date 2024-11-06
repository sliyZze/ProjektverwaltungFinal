package de.szut.lf8_starter.mitarbeiter.dto;

import de.szut.lf8_starter.projekt.dto.ProjektDetailsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MitarbeiterProjektResponseDto {
    private long mitarbeiterId;
    private List<ProjektDetailsDto> projekte;
}
