package de.szut.lf8_starter.projekt;

import de.szut.lf8_starter.exceptionHandling.EmployeeAlreadyInProject;
import de.szut.lf8_starter.exceptionHandling.EmployeeNoMatchedQualification;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_starter.exceptionHandling.EmployeeNotFreeException;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterCreateSkillSet;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterGetDto;
import de.szut.lf8_starter.mitarbeiter.dto.QualifikationGetDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjektService {

    private final ProjektRepository repository;

    public ProjektService(ProjektRepository repository) {
        this.repository = repository;
    }

    public ProjektEntity create(ProjektEntity entity, MitarbeiterGetDto responsibleEmployee) {
        for (QualifikationGetDto QualiDto : responsibleEmployee.getSkillSet()) {
            if (QualiDto.getSkill().equals("ProductOwner")) {
                return repository.save(entity);
            }
        }
        throw new RuntimeException("Mitarbeiter nicht als Projektleiter geeignet");
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
        Map<String, QualifikationDetail> qualifikationen = projektEntity.getQualifikationen();
        List<QualifikationGetDto> qualifikationGetDtos = employee.getSkillSet();
        List<QualifikationGetDto> matchedQualification = new ArrayList<>();

        for (QualifikationGetDto qualificationGetDto : qualifikationGetDtos) {
            if (qualifikationen.containsKey(qualificationGetDto.getSkill())) {
                matchedQualification.add(qualificationGetDto);
            }
        }
        return matchedQualification;
    }

    public void addEmployeeToProject(long projectId, MitarbeiterGetDto employee, MitarbeiterCreateSkillSet neededEmployeeSkills) {
        checkExistence(projectId);
        ProjektEntity projektEntity = find(projectId);

        if (isEmployeeInProject(projektEntity, employee.getId())) {
            throw new EmployeeAlreadyInProject("employee with id " + employee.getId());
        }

        Set<String> vorhandeneSkills = getMatchingSkills(neededEmployeeSkills, employee);
        if (vorhandeneSkills.isEmpty()) {
            throw new EmployeeNoMatchedQualification("employee id " + employee.getId());
        }

        boolean skillVorhanden = checkProjectSkills(vorhandeneSkills, projektEntity.getQualifikationen());
        boolean frei = checkEmployeeAvailability(employee.getId(), projektEntity.getStartDatum(), projektEntity.getTatEndDatum());

        if (skillVorhanden && frei) {
            addEmployeeToProject(projektEntity, employee.getId(), vorhandeneSkills);
            repository.save(projektEntity);
        } else {
            handleUnmatchedConditions(skillVorhanden, frei, employee.getId());
        }
    }

    // gibt die schnittmenge von dem employee und der benötigten skills wieder
    private Set<String> getMatchingSkills(MitarbeiterCreateSkillSet neededSkills, MitarbeiterGetDto employee) {
        Set<String> employeeSkills = employee.getSkillSet().stream()
                .map(QualifikationGetDto::getSkill)
                .collect(Collectors.toSet());

        return neededSkills.getSkills().stream()
                .filter(employeeSkills::contains)
                .collect(Collectors.toSet());
    }

    // checkt ob die skills vom mitarbeiter in dem projekt vorhanden sind
    private boolean checkProjectSkills(Set<String> vorhandeneSkills, Map<String, QualifikationDetail> projectSkills) {
        return vorhandeneSkills.stream().allMatch(projectSkills::containsKey);
    }

    private boolean checkEmployeeAvailability(long employeeId, LocalDate projectStart, LocalDate projectEnd) {
        List<ProjektEntity> allEmployeeProjects = repository.findAllByEmployeeId(employeeId);

        return allEmployeeProjects.stream().allMatch(employeeProject ->
                employeeProject.getTatEndDatum().isBefore(projectStart) || employeeProject.getStartDatum().isAfter(projectEnd));
    }

    private void addEmployeeToProject(ProjektEntity project, long employeeId, Set<String> matchedSkills) {
        project.getMitarbeiterIds().add(employeeId);

        for (String skill : matchedSkills) {
            project.getQualifikationen().get(skill).istIn();
            project.getUsedEmployeeQualifikationen().add(createEmployeeQualification(project.getId(), employeeId, skill));
        }
    }

    private MitarbeiterQualifikation createEmployeeQualification(long projectId, long employeeId, String skill) {
        MitarbeiterQualifikation qualification = new MitarbeiterQualifikation();
        qualification.setProjektId(projectId);
        qualification.setMitarbeiterId(employeeId);
        qualification.setQualifikation(skill);
        return qualification;
    }

    private void handleUnmatchedConditions(boolean skillMatched, boolean isFree, long employeeId) {
        if (!skillMatched) {
            throw new EmployeeNoMatchedQualification("employee id " + employeeId);
        }
        if (!isFree) {
            throw new EmployeeNotFreeException("employee id " + employeeId);
        }
    }

    public void removeEmployeeFromProject(long projectId, long employeeId) {
        checkExistence(projectId);
        ProjektEntity projektEntity = find(projectId);

        if (!isEmployeeInProject(projektEntity, employeeId)) {
            throw new EmployeeNotFoundException("employee with id " + employeeId);
        }

        removeEmployeeQualifications(projektEntity, employeeId);
        projektEntity.getMitarbeiterIds().remove(employeeId);
        repository.save(projektEntity);
    }

    private boolean isEmployeeInProject(ProjektEntity projektEntity, long employeeId) {
        return projektEntity.getMitarbeiterIds().contains(employeeId);
    }

    private void removeEmployeeQualifications(ProjektEntity projektEntity, long employeeId) {
        Iterator<MitarbeiterQualifikation> iterator = projektEntity.getUsedEmployeeQualifikationen().iterator();

        while (iterator.hasNext()) {
            MitarbeiterQualifikation mitarbeiterQualifikation = iterator.next();
            if (mitarbeiterQualifikation.getMitarbeiterId() == employeeId) {
                projektEntity.getQualifikationen().get(mitarbeiterQualifikation.getQualifikation()).istDe();
                iterator.remove();
            }
        }
    }

    public List<ProjektEntity> getAllEmployeeProjects(long employeeId) {
        return repository.findAllByEmployeeId(employeeId);
    }

    public void checkExistence(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Projekt with id " + id + " not found");
        }
    }
}
