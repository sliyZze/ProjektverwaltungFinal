package de.szut.lf8_starter.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmployeeNoMatchedQualification extends RuntimeException {
    public EmployeeNoMatchedQualification(String message) {
        super(message);
    }
}
