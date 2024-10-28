package de.szut.lf8_starter.projekt;

import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterCreateSkillSet;
import de.szut.lf8_starter.request.RequestEmployeeService;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterGetDto;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterProjektResponseDto;
import de.szut.lf8_starter.mitarbeiter.dto.QualifikationGetDto;
import de.szut.lf8_starter.projekt.dto.ProjektCreateDto;
import de.szut.lf8_starter.projekt.dto.ProjektCreateDtoAllEmployees;
import de.szut.lf8_starter.projekt.dto.ProjektGetDto;
import de.szut.lf8_starter.projekt.dto.ProjektUpdateDto;
import de.szut.lf8_starter.request.RequestKundenService;
import de.szut.lf8_starter.request.RequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
//@RequestMapping(value = "project")
@PreAuthorize("hasAnyAuthority('user')")
public class ProjektController implements ProjektControllerInterface {

    private final ProjektService service;
    private final ProjektMapper projektMapper;
    RequestEmployeeService employeeServiceRequest;
    RequestKundenService kundenServiceRequest;

    public ProjektController(ProjektService service, ProjektMapper projektMapper, RequestEmployeeService request) {
        this.service = service;
        this.projektMapper = projektMapper;
        this.employeeServiceRequest = request;
    }

    @Override
    public ResponseEntity<?> create(@RequestBody @Valid ProjektCreateDto projektCreateDto) {

        MitarbeiterGetDto responsibleEmployee = employeeServiceRequest.getEmployee(projektCreateDto.getVerantwortlicherMitarbeiter());
//        kundenServiceRequest.checkKunde(projektCreateDto.getKundenId());
        ProjektEntity projektEntity = this.projektMapper.mapCreateDtoToEntity(projektCreateDto,responsibleEmployee.getId());
        projektEntity = this.service.create(projektEntity);
        ProjektGetDto projektDto = projektMapper.mapToGetDto(projektEntity);
        return ResponseEntity.ok(projektDto); // wirft exception?
    }

    @Override
    public ResponseEntity<?> delete(@PathVariable long id) {
        this.service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("successfully deleted project with id " + id);
    }

    @Override
    public ResponseEntity<?> update(@RequestBody @Valid ProjektUpdateDto projektUpdateDto, @PathVariable long id) {

        MitarbeiterGetDto responsibleEmployee = employeeServiceRequest.getEmployee(projektUpdateDto.getVerantwortlicherMitarbeiter());
        kundenServiceRequest.checkKunde(projektUpdateDto.getKundenId());
        ProjektEntity projektEntity = this.projektMapper.mapUpdateDtoToEntity(projektUpdateDto, responsibleEmployee.getId());
        service.update(projektEntity,id);
        ProjektGetDto projektDto = projektMapper.mapToGetDto(projektEntity);
        return ResponseEntity.status(HttpStatus.OK).body("successfully updated " + projektDto);
    }

    @Override
    public ResponseEntity<?> getProjectById(@PathVariable long id) {

        ProjektEntity entity = service.find(id);
        ProjektGetDto projektDto = projektMapper.mapToGetDto(entity);

        return ResponseEntity.ok(projektDto);
    }

    @Override
    public ResponseEntity<?> getAllProjects() {

        List<ProjektGetDto> projektGetDtoList = new ArrayList<>();
        for (ProjektEntity projektEntity : service.findAll()) {
            ProjektGetDto projektDto = projektMapper.mapToGetDto(projektEntity);
            projektGetDtoList.add(projektDto);
        }
        return ResponseEntity.ok(projektGetDtoList);
    }

    @Override
    public ResponseEntity<?> addEmployee(@PathVariable long id, @PathVariable long eid, @RequestBody @Valid MitarbeiterCreateSkillSet neededEmployeeSkills) {

        MitarbeiterGetDto employee = employeeServiceRequest.getEmployee(eid);
        service.addEmployeeToProject(id, employee, neededEmployeeSkills);
        return ResponseEntity.ok("added employee "+ eid + " successfully to project " + id);
    }

    @Override
    public ResponseEntity<?> removeEmployee(@PathVariable long pid, @PathVariable long eid){

        service.removeEmployeeFromProject(pid, eid);
        return ResponseEntity.ok("mitarbeiter " + eid + " deleted successfully");
    }

    // gibt alle mitarbeiter eines projektes wieder und das projekt mit id und bezeichnung
    @Override
    public ResponseEntity<?> getAllEmployees(@PathVariable long id) {
        ProjektEntity projektEntity = service.find(id);
        List<MitarbeiterGetDto> mitarbeiterList = new ArrayList<>();

        for (Long mitarbeiterId : projektEntity.getMitarbeiterIds()) {
            MitarbeiterGetDto employee = employeeServiceRequest.getEmployee(mitarbeiterId);
            List<QualifikationGetDto> matchedQualification = service.getMatchedQualification(projektEntity,employee);
            employee.setSkillSet(matchedQualification);
            mitarbeiterList.add(employee);
        }

        ProjektCreateDtoAllEmployees responseMap = projektMapper.mapGetAllEmployeesFromProjectDto(projektEntity,mitarbeiterList);
        return ResponseEntity.ok(responseMap);
    }

    // gibt alle projekte eines mitarbeiters wieder
    @Override
    public ResponseEntity<?> getAllEmployeeProjects(@PathVariable long id) {

        MitarbeiterGetDto mitarbeiterCreateDto = employeeServiceRequest.getEmployee(id);
        List<ProjektEntity> allEmployeeProjects = service.getAllEmployeeProjects(id);
        MitarbeiterProjektResponseDto responseDto = projektMapper.mapMitarbeiterProjekteDto(allEmployeeProjects,mitarbeiterCreateDto);

        return ResponseEntity.ok(responseDto);
    }

}
