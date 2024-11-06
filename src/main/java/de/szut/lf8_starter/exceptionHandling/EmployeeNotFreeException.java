package de.szut.lf8_starter.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//Author: Tobias
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmployeeNotFreeException extends RuntimeException {
    public EmployeeNotFreeException(String message) {
        super(message);
    }
}
