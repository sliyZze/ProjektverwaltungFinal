package de.szut.lf8_starter.exceptionHandling;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
//Author: Tobias
@ControllerAdvice
@ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "invalid JSON posted",
                content = @Content)
})
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleHelloEntityNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage() + " entity not found");
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEntityRequestNotFoundException(EmployeeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage() + " does not exists");
    }

    @ExceptionHandler(EmployeeNoMatchedQualification.class)
    public ResponseEntity<String> handleEmployeeNoMatchedQualification(EmployeeNoMatchedQualification ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage() + " employee has no matched qualification");
    }

    @ExceptionHandler(EmployeeNotFreeException.class)
    public ResponseEntity<String> handleEmployeeNotFree(EmployeeNotFreeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage() + " employee is not available");
    }

    @ExceptionHandler(EmployeeAlreadyInProject.class)
    public ResponseEntity<String> handleEmployeeAlreadyInProject(EmployeeAlreadyInProject ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage() + " is already in project");
    }
}
