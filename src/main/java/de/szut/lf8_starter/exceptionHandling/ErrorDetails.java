package de.szut.lf8_starter.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
//Author: Tobias
@Data
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
