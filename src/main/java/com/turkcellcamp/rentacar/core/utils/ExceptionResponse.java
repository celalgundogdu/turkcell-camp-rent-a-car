package com.turkcellcamp.rentacar.core.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {

    private String path;
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
