package de.szut.lf8_starter.projekt;

import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterCreateSkillSet;
import de.szut.lf8_starter.projekt.dto.ProjektCreateDto;
import de.szut.lf8_starter.projekt.dto.ProjektUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "project")
public interface ProjektControllerInterface {


    @Operation(summary = "Create a project", description = "creates a new project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjektCreateDto.class)) }),
            @ApiResponse(responseCode = "404", description = "project creation failed", content = @Content)
    })
    @PostMapping()
    ResponseEntity<?> create(@RequestBody @Valid ProjektCreateDto projektCreateDto);

    @Operation(summary = "delete a project", description = "deletes a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted project",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "project deletion failed", content = @Content)
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable long id);


    @Operation(summary = "update a project", description = "updates a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjektCreateDto.class)) }),
            @ApiResponse(responseCode = "404", description = "project update failed", content = @Content)
    })
    @PostMapping("update/{id}")
    ResponseEntity<?> update(@RequestBody @Valid ProjektUpdateDto projektUpdateDto, @PathVariable long id);

    @Operation(summary = "get a single project", description = "get a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched project",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProjektCreateDto.class)) }),
            @ApiResponse(responseCode = "404", description = "project fetch failed", content = @Content)
    })
    @GetMapping("/{id}")
    ResponseEntity<?> getProjectById(@PathVariable long id);

    @Operation(summary = "get all projects in the service", description = "get all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all project",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProjektCreateDto.class)) }),
            @ApiResponse(responseCode = "404", description = "projects fetch failed", content = @Content)
    })
    @GetMapping("/all")
    ResponseEntity<?> getAllProjects();

    @Operation(summary = "add employee to project", description = "add a employee to a project, the employee needs the required qualifications and has to be available, you can choose which qualifications you want to add from the employee to the project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added to project",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "employee addition failed", content = @Content)
    })
    @PostMapping("/{id}/add/employee/{eid}")
    ResponseEntity<?> addEmployee(@PathVariable long id, @PathVariable long eid, @RequestBody @Valid MitarbeiterCreateSkillSet mitarbeiterCreateSkillSet);

    @Operation(summary = "remove a employee from project", description = "remove employee from project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added to project",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "employee addition failed", content = @Content)
    })
    @DeleteMapping("/{pid}/remove/employee/{eid}")
    ResponseEntity<?> removeEmployee(@PathVariable long pid, @PathVariable long eid);

    @Operation(summary = "get all employees from a project", description = "get all employees from a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "fetching failed", content = @Content)
    })
    @GetMapping("/{id}/employees")
    ResponseEntity<?> getAllEmployees(@PathVariable long id);

    @Operation(summary = "get all projects from an employee", description = "get all employee projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully fetched all employee projects",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "fetching failed", content = @Content)
    })
    @GetMapping("/employee/{id}")
    ResponseEntity<?> getAllEmployeeProjects(@PathVariable long id);
}
