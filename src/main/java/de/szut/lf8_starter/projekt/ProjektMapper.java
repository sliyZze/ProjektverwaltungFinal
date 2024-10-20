package de.szut.lf8_starter.projekt;

import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterGetDto;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterProjektResponseDto;
import de.szut.lf8_starter.mitarbeiter.dto.QualifikationGetDto;
import de.szut.lf8_starter.projekt.dto.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjektMapper {

    public ProjektGetDto mapToGetDto(ProjektEntity entity) {
        return new ProjektGetDto(entity.getId(),entity.getBezeichnung(),entity.getVerantwortlicherMitarbeiter(),entity.getKundenId(),
                                entity.getKundenName(),entity.getKommentar(),entity.getStartDatum(),
                                entity.getGepEndDatum(),entity.getTatEndDatum(), entity.getQualifikationen(), entity.getMitarbeiterIds());
    }

    public ProjektEntity mapCreateDtoToEntity(ProjektCreateDto dto, long responsibleEmployee) {
        var entity = new ProjektEntity();
        entity.setBezeichnung(dto.getBezeichnung());
        entity.setVerantwortlicherMitarbeiter(responsibleEmployee);
        entity.setKundenId(dto.getKundenId());
        entity.setKundenName(dto.getKundenName());
        entity.setKommentar(dto.getKommentar());
        entity.setStartDatum(dto.getStartDatum());
        entity.setGepEndDatum(dto.getGepEndDatum());
        entity.setTatEndDatum(dto.getTatEndDatum());
        entity.setQualifikationen(dto.getQualifikationen());
        entity.setMitarbeiterIds(new HashSet<>());
        return entity;
    }

    public ProjektEntity mapUpdateDtoToEntity(ProjektUpdateDto dto, long responsibleEmployee) {
        var entity = new ProjektEntity();
        entity.setBezeichnung(dto.getBezeichnung());
        entity.setVerantwortlicherMitarbeiter(responsibleEmployee);
        entity.setKundenId(dto.getKundenId());
        entity.setKundenName(dto.getKundenName());
        entity.setKommentar(dto.getKommentar());
        entity.setGepEndDatum(dto.getGepEndDatum());
        return entity;
    }

    public MitarbeiterProjektResponseDto mapMitarbeiterProjekteDto(List<ProjektEntity> projektEntities, MitarbeiterGetDto mitarbeiterCreateDto) {
        List<ProjektDetailsDto> projectList = new ArrayList<>();
        List<QualifikationGetDto> mitarbeiterQualifikationen = mitarbeiterCreateDto.getSkillSet();

        for (ProjektEntity projektEntity : projektEntities) {
            ProjektDetailsDto projektDetails = new ProjektDetailsDto();
            projektDetails.setId(projektEntity.getId());
            projektDetails.setBezeichnung(projektEntity.getBezeichnung());
            projektDetails.setStartDatum(projektEntity.getStartDatum());
            projektDetails.setEndDatum(projektEntity.getTatEndDatum());

            // Finde die gemeinsamen Qualifikationen
            Set<String> projektQualifikationen = projektEntity.getQualifikationen();
            List<String> gemeinsameQualifikationen = mitarbeiterQualifikationen.stream()
                    .map(QualifikationGetDto::getSkill)
                    .filter(projektQualifikationen::contains)
                    .toList();
            projektDetails.setRollen(gemeinsameQualifikationen);

            projectList.add(projektDetails);
        }

        MitarbeiterProjektResponseDto responseDto = new MitarbeiterProjektResponseDto();
        responseDto.setMitarbeiterId(mitarbeiterCreateDto.getId());
        responseDto.setProjekte(projectList);

        return responseDto;
    }

    public ProjektCreateDtoAllEmployees mapGetAllEmployeesFromProjectDto(ProjektEntity projektEntity, List<MitarbeiterGetDto> mitarbeiterList){

        ProjektCreateDtoAllEmployees responseMap = new ProjektCreateDtoAllEmployees();
        responseMap.setProjektId(projektEntity.getId());
        responseMap.setBezeichnung(projektEntity.getBezeichnung());
        responseMap.setMitarbeiter(mitarbeiterList);
        return responseMap;
    }
}
