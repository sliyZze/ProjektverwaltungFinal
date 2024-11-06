package de.szut.lf8_starter.projekt.dto;

import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterGetDto;
import lombok.Data;

import java.util.List;

@Data
public class ProjektCreateDtoAllEmployees {

    long projektId;
    String bezeichnung;

    List<MitarbeiterGetDto> mitarbeiter;

}
