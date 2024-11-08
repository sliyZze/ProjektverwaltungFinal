package de.szut.lf8_starter.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//Author: Tobias
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmployeeAlreadyInProject extends RuntimeException {
    public EmployeeAlreadyInProject(String message) {
        super(message);
    }
}
