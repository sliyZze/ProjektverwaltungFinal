package de.szut.lf8_starter.hello;


import de.szut.lf8_starter.exceptionHandling.ResourceNotFoundException;
import de.szut.lf8_starter.hello.dto.HelloCreateDto;
import de.szut.lf8_starter.hello.dto.HelloGetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "hello")
@PreAuthorize("hasAnyAuthority('user')")
public class HelloController {
    private final HelloService service;
    private final HelloMapper helloMapper;

    public HelloController(HelloService service, HelloMapper mappingService) {
        this.service = service;
        this.helloMapper = mappingService;
    }

    @Operation(summary = "creates a new hello with its id and message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "created hello",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HelloGetDto.class))}),
            @ApiResponse(responseCode = "400", description = "invalid JSON posted",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @PostMapping
    public HelloGetDto create(@RequestBody @Valid HelloCreateDto helloCreateDto) {
        HelloEntity helloEntity = this.helloMapper.mapCreateDtoToEntity(helloCreateDto);
        helloEntity = this.service.create(helloEntity);
        return this.helloMapper.mapToGetDto(helloEntity);
    }

    @Operation(summary = "delivers a list of hellos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of hellos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HelloGetDto.class))}),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping
    public List<HelloGetDto> findAll() {
        return this.service
                .readAll()
                .stream()
                .map(e -> this.helloMapper.mapToGetDto(e))
                .collect(Collectors.toList());
    }

    @Operation(summary = "deletes a Hello by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "delete successful"),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteHelloById(@PathVariable long id) {
        var entity = this.service.readById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("HelloEntity not found on id = " + id);
        } else {
            this.service.delete(entity);
        }
    }

    @Operation(summary = "find hellos by message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of hellos who have the given message",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = HelloGetDto.class))}),
            @ApiResponse(responseCode = "404", description = "qualification description does not exist",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "not authorized",
                    content = @Content)})
    @GetMapping("/findByMessage")
    public List<HelloGetDto> findAllEmployeesByQualification(@RequestParam String message) {
        return this.service
                .findByMessage(message)
                .stream()
                .map(e -> this.helloMapper.mapToGetDto(e))
                .collect(Collectors.toList());
    }
}
