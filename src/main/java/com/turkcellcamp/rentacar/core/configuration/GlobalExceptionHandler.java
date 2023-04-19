package com.turkcellcamp.rentacar.core.configuration;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.core.exceptions.EntityAlreadyExistsException;
import com.turkcellcamp.rentacar.core.exceptions.EntityNotFoundException;
import com.turkcellcamp.rentacar.core.utils.ExceptionResponse;
import com.turkcellcamp.rentacar.core.utils.ValidationExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException exception,
                                                           HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return response;
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException exception,
                                                                HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
        return response;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleBusinessException(BusinessException exception, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                LocalDateTime.now()
        );
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                   HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ExceptionResponse response = new ValidationExceptionResponse(
                request.getServletPath(),
                Messages.Exception.VALIDATION,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                validationErrors
        );
        return response;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                       HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                   HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(RuntimeException exception, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
        return response;
    }
}
