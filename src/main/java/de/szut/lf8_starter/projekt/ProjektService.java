package de.szut.lf8_starter.projekt;

import de.szut.lf8_starter.RequestEmployeeService;
import de.szut.lf8_starter.exceptionHandling.EmployeeNoMatchedQualification;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFreeException;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterGetDto;
import de.szut.lf8_starter.mitarbeiter.dto.QualifikationGetDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjektService {

    private final ProjektRepository repository;

    public ProjektService(ProjektRepository repository) {
        this.repository = repository;
    }

    public ProjektEntity create(ProjektEntity entity ) {
        return repository.save(entity);
    }

    public void delete(long id) {
        checkExistence(id);
        repository.deleteById(id);
    }

    public List<ProjektEntity> findAll() {
        return repository.findAll();
    }

    public ProjektEntity find(long id) {
        checkExistence(id);
        return repository.findById(id).get();
    }

    public void update(ProjektEntity entity, long id) {
        checkExistence(id);
        Optional<ProjektEntity> projektEntity = repository.findById(id);
        projektEntity.get().setBezeichnung(entity.getBezeichnung());
        projektEntity.get().setVerantwortlicherMitarbeiter(entity.getVerantwortlicherMitarbeiter());
        projektEntity.get().setKundenName(entity.getKundenName());
        projektEntity.get().setKommentar(entity.getKommentar());
        projektEntity.get().setGepEndDatum(entity.getGepEndDatum());

        repository.save(projektEntity.get());
    }

    public List<QualifikationGetDto> getMatchedQualification(ProjektEntity projektEntity, MitarbeiterGetDto employee) {
        Set<String> qualifikationen = projektEntity.getQualifikationen();
        List<QualifikationGetDto> qualifikationGetDtos = employee.getSkillSet();
        List<QualifikationGetDto> matchedQualification = new ArrayList<>();

        for (QualifikationGetDto qualificationGetDto : qualifikationGetDtos) {
            if (qualifikationen.stream().anyMatch(qualificationGetDto.getSkill()::equals)) {
                matchedQualification.add(qualificationGetDto);
            }
        }
        return matchedQualification;
    }

    public void addEmployeeToProject(long projectId, MitarbeiterGetDto employee) {
        checkExistence(projectId);
        ProjektEntity projektEntity = this.find(projectId);

        Set<String> projektQualifikationen = projektEntity.getQualifikationen();
        List<QualifikationGetDto> mitarbeiterQualifikationen = employee.getSkillSet();

        boolean qualifikationGefunden = mitarbeiterQualifikationen.stream()
                .anyMatch(qualifikationGetDto -> projektQualifikationen.stream()
                        .anyMatch(projektQualifikation ->
                                projektQualifikation.equalsIgnoreCase(qualifikationGetDto.getSkill())));

        LocalDate startDatum = projektEntity.getStartDatum();  // Startdatum des neuen Projekts
        LocalDate endDatum = projektEntity.getTatEndDatum();   // Tatsächliches Enddatum des neuen Projekts

        List<ProjektEntity> allEmployeeProjects = repository.findAllByEmployeeId(employee.getId());
        boolean frei = true;

        for (ProjektEntity employeeProject : allEmployeeProjects) {
            if (!(employeeProject.getTatEndDatum().isBefore(startDatum) || employeeProject.getStartDatum().isAfter(endDatum))) {
                frei = false;
                break;
            }
        }

        if (qualifikationGefunden && frei) {
            Set<Long> mitarbeiterIds = projektEntity.getMitarbeiterIds();
            mitarbeiterIds.add(employee.getId());

            projektEntity.setMitarbeiterIds(mitarbeiterIds);
//            this.update(projektEntity, projectId);
            repository.save(projektEntity);
        }
        if (!qualifikationGefunden) {
            throw new EmployeeNoMatchedQualification("employee id " + employee.getId());
        } else if (!frei) {
            throw new EmployeeNotFreeException("employee id " + employee.getId());
        }
    }

    public void removeEmployeeFromProject(long projectId, long employeeId) {
        checkExistence(projectId);
        ProjektEntity projektEntity = this.find(projectId);
        Set<Long> mitarbeiterIds = projektEntity.getMitarbeiterIds();

        if (!mitarbeiterIds.contains(employeeId)) {
            throw new EmployeeNotFoundException("employee with " + employeeId);
        }
        mitarbeiterIds.remove(employeeId);
        projektEntity.setMitarbeiterIds(mitarbeiterIds);
//        this.update(projektEntity, projectId);
        repository.save(projektEntity);
    }

    public List<ProjektEntity> getAllEmployeeProjects(long employeeId) {
        return repository.findAllByEmployeeId(employeeId);//responseDto;
    }

    //TODO auslagern in eine exception file damit mitarbeiter und so es auch benutzen können
    public void checkExistence(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Projekt with id " + id + " not found"); // hier maybe aich eigene exception machen?
        }
    }
}
